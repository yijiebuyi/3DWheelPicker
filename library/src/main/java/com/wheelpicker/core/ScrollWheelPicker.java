package com.wheelpicker.core;


import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.core.view.ViewCompat;

import com.wheelpicker.R;
import com.wheelpicker.anim.Animation;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public abstract class ScrollWheelPicker<T extends WheelPickerAdapter> extends AbstractWheelPicker<T> {
    /**
     * Idle
     */
    public static final int SCROLL_STATE_IDLE = 0;
    /**
     * Down
     */
    public static final int SCROLL_STATE_DOWN = 1;

    /**
     * Dragging
     */
    public static final int SCROLL_STATE_DRAGGING = 2;

    /**
     * Scrolling
     */
    public static final int SCROLL_STATE_SCROLLING = 3;

    public static final int VETTAICL = 1 << 1;
    public static final int HORIZENTAL = 1 << 2;

    private static final int CORRECT_ANIMATION_DURATION = 250;
    /**
     * 手指滑动，滚轮跟随滚动因子
     */
    private static final float MOVE_FACTOR = 0.2F;

    private final Interpolator DEFAULT_FLING_ANIM_INTERPOLATOR  = new DecelerateInterpolator(4);
    private final Interpolator DEFAULT_ROLLBACK_ANIM_INTERPOLATOR  = new DecelerateInterpolator(4);

    protected static int mOrientation = VETTAICL;
    /**
     * 是否支持循环滚动
     */
    protected boolean mLoop = false;

    protected WheelPickerImpl mWheelPickerImpl;

    private CorrectAnimRunnable mCorrectAnimRunnable;
    private CorrectTransLateAnim mCorrectAnimController;

    private FlingRunnable mFlingRunnable;

    /**
     * overScroll偏移量
     */
    private int mOverScrollOffset;
    private int mMinScrollOffset;
    private int mMaxScrollOffset;

    private int mScrollState = SCROLL_STATE_IDLE;

    /**
     * 手指滑动，滚动跟随滚动因子
     */
    private float mFingerMoveFactor = MOVE_FACTOR * 1.0f;
    /**
     * fling滚动阻尼因子
     */
    private float mFlingAnimFactor = 0.8f;
    /**
     * 手指离开屏幕后，动画Anim的插值器
     */
    private Interpolator mFlingAnimInterpolator = DEFAULT_FLING_ANIM_INTERPOLATOR;
    /**
     * 回滚动画的插值器
     */
    private Interpolator mRollbackAnimInterpolator = DEFAULT_ROLLBACK_ANIM_INTERPOLATOR;

    public ScrollWheelPicker(Context context) {
        super(context);
    }

    public ScrollWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void instantiation() {
        super.instantiation();

        mWheelPickerImpl = new WheelPickerImpl(mOrientation);
        init();
    }

    private void init() {
        mCorrectAnimController = new CorrectTransLateAnim();
        mFlingRunnable = new FlingRunnable(getContext());
        mCorrectAnimRunnable = new CorrectAnimRunnable();

        mOverScrollOffset = getResources().getDimensionPixelOffset(R.dimen.px24);
    }

    protected void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public void setScrollRange(int minOffset, int maxOffset) {
        mMinScrollOffset = minOffset;
        mMaxScrollOffset = maxOffset;
    }

    @Override
    protected void resetScroll() {
        if (mFlingRunnable != null) {
            mFlingRunnable.resetScroller(getContext());
        }
    }

    @Override
    protected void onTouchDown(MotionEvent event) {
        mFlingRunnable.stop();
        mCorrectAnimRunnable.stop();
        mScrollState = SCROLL_STATE_DOWN;
    }

    @Override
    protected void onTouchMove(MotionEvent event) {
        mScrollState = SCROLL_STATE_DRAGGING;
        mCurrentX = (mCurrentX + mDeltaX);
        mCurrentY = (mCurrentY + mDeltaY * mFingerMoveFactor);

        onScrolling(mCurrentX, mCurrentY, false);
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
        mScrollState = SCROLL_STATE_SCROLLING;
        mFlingRunnable.fling();
    }

    @Override
    protected void onTouchCancel(MotionEvent event) {
        mFlingRunnable.stop();
        mScrollState = SCROLL_STATE_IDLE;
    }

    /**
     * Fling animation
     */
    private class FlingRunnable implements Runnable {
        protected WheelScroller mScroller;
        private float friction = ViewConfiguration.getScrollFriction();

        private FlingRunnable(Context context) {
            if (OSUtils.isEMUI()) {
                mScroller = new ScrollerCompat(context, mFlingAnimInterpolator);
                mScroller.setFriction(friction);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    mScroller = new OverScrollerCompat(context, mFlingAnimInterpolator);
                    mScroller.setFriction(friction);
                } else {
                    mScroller = new ScrollerCompat(context, mFlingAnimInterpolator);
                    mScroller.setFriction(friction);
                }
            }
        }

        public void run() {
            mScroller.computeScrollOffset();
            if (!mScroller.isFinished()) {
                ViewCompat.postOnAnimation(ScrollWheelPicker.this, this);
            } else {
                mCurrentX = mScroller.getFinalX();
                mCurrentY = mScroller.getFinalY();
            }

            mCurrentX = mScroller.getCurrX();
            mCurrentY = mScroller.getCurrY();
            onScrolling(mCurrentX, mCurrentY,
                    mScrollState == SCROLL_STATE_SCROLLING && mScroller.isFinished());
        }

        public void stop() {
            if (!mScroller.isFinished())
                mScroller.abortAnimation();
        }

        public void fling() {
            int minScrollOffset = mLoop ? Integer.MIN_VALUE : mMinScrollOffset;
            int maxScrollOffset = mLoop ? Integer.MAX_VALUE : mMaxScrollOffset;

            //Log.i("aaa", "  min=" + minScrollOffset + " max=" + maxScrollOffset);
            //minScrollOffset = 0;
            //maxScrollOffset = 200;

            if (mOrientation == HORIZENTAL) {
                mScroller.fling((int) (mCurrentX), 0,
                        (int) (mTracker.getXVelocity() * mFlingAnimFactor), 0,
                        minScrollOffset, maxScrollOffset,
                        0, 0,
                        mOverScrollOffset, 0);
            } else {
                mScroller.fling(0, (int) (mCurrentY),
                        0, (int) (mTracker.getYVelocity() * mFlingAnimFactor),
                        0, 0,
                        minScrollOffset, maxScrollOffset,
                        0, mOverScrollOffset);
            }
            //开启fling动画
            ViewCompat.postOnAnimation(ScrollWheelPicker.this, this);
        }

        public void resetScroller(Context context) {
            if (mCurrentX == 0 && mCurrentY == 0) {
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                mScroller.startScroll(x, y, -x, -y, 10); //scroll back in short time
            } else {
                mScroller.setFinalX(0);
                mScroller.setFinalY(0);
            }

            mCurrentX = 0;
            mCurrentY = 0;
        }
    }

    /**
     * start correction animation
     *
     * @param transLateX
     * @param transLateY
     */
    protected void startCorrectAnimation(float transLateX, float transLateY) {
        if (mCorrectAnimController == null) {
            mCorrectAnimController = new CorrectTransLateAnim();
        } else {
            mCorrectAnimController.forceStop();
        }
        if (mOrientation == HORIZENTAL) {
            if (transLateX == 0) {
                mScrollState = SCROLL_STATE_IDLE;
                return;
            }
        } else {
            if (transLateY == 0) {
                mScrollState = SCROLL_STATE_IDLE;
                return;
            }
        }

        mCorrectAnimController.setTransLate(transLateX, transLateY);

        ViewCompat.postOnAnimation(ScrollWheelPicker.this, mCorrectAnimRunnable);
        mCorrectAnimController.start();
    }

    /**
     * stop correction animation
     */
    protected void stopCorrectAnimation() {
        mCorrectAnimRunnable.stop();
    }

    /**
     * The animation runnable of correcting the location
     */
    private class CorrectAnimRunnable implements Runnable {

        public void run() {
            boolean running = mCorrectAnimController.calculate(SystemClock.uptimeMillis());
            if (running) {
                onScrolling(mCurrentX, mCurrentY, false);
                ViewCompat.postOnAnimation(ScrollWheelPicker.this, this);
            } else {
                mCurrentX = mCorrectAnimController.getFinalX();
                mCurrentY = mCorrectAnimController.getFinalY();
                onScrolling(mCurrentX, mCurrentY, true);

                mScrollState = SCROLL_STATE_IDLE;
            }
        }

        public void stop() {
            mCorrectAnimController.forceStop();
        }
    }

    /**
     * Translate animation
     */
    private class CorrectTransLateAnim extends Animation {
        private float mStartX;
        private float mStartY;
        private float mTransLateX;
        private float mTransLateY;

        public CorrectTransLateAnim() {
            setDuration(CORRECT_ANIMATION_DURATION);
            setInterpolator(new DecelerateInterpolator());
        }

        public void setTransLate(float transLateX, float transLateY) {
            mTransLateX = transLateX;
            mTransLateY = transLateY;
            mStartX = mCurrentX;
            mStartY = mCurrentY;
        }

        public float getFinalX() {
            return mStartX + mCurrentX;
        }

        public float getFinalY() {
            return mStartY + mTransLateY;
        }

        protected void onCalculate(float factor) {
            if (mOrientation == HORIZENTAL) {
                mCurrentX = (mStartX + (int) (factor * mTransLateX));
            } else {
                mCurrentY = (mStartY + factor * mTransLateY);
            }
        }
    }

    protected abstract void onScrolling(float offsetX, float offsetY, boolean isFinshed);

    public void setFingerMoveFactor(float fingerMoveFactor) {
        fingerMoveFactor = Animation.clamp(fingerMoveFactor, 0.001f, 1);
        fingerMoveFactor *= MOVE_FACTOR;
        mFingerMoveFactor = fingerMoveFactor;
    }

    public void setFlingAnimFactor(float flingAnimFactor) {
        flingAnimFactor = Animation.clamp(flingAnimFactor, 0.001f, 1);
        mFlingAnimFactor = flingAnimFactor;
    }

    public void setOverOffset(int overScrollOffset) {
        if (overScrollOffset < 0) {
            return;
        }

        mOverScrollOffset = overScrollOffset;
    }
}
