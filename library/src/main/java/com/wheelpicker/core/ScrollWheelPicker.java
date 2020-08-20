package com.wheelpicker.core;


import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import androidx.core.view.ViewCompat;

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
    private static final float MOVE_FACTOR = 0.3F;

    protected static int mOrientation = VETTAICL;
    protected int mOverOffset;

    protected WheelPickerImpl mWheelPickerImpl;

    private CorrectAnimRunnable mCorrectRunnable;
    private FlingRunnable mFlingRunnable;
    private TransLateAnim mAnimController;

    private int mMinScrollOffset;
    private int mMaxScrollOffset;

    private int mScrollState = SCROLL_STATE_IDLE;

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
        mAnimController = new TransLateAnim();
        mFlingRunnable = new FlingRunnable(getContext());
        mCorrectRunnable = new CorrectAnimRunnable();
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
        mCorrectRunnable.stop();
        mScrollState = SCROLL_STATE_DOWN;
    }

    @Override
    protected void onTouchMove(MotionEvent event) {
        mScrollState = SCROLL_STATE_DRAGGING;
        mCurrentX = (mCurrentX + mDeltaX);
        mCurrentY = (mCurrentY + mDeltaY * MOVE_FACTOR);

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
        private Interpolator mInterpolator = new DecelerateInterpolator(4);
        private float friction = ViewConfiguration.getScrollFriction();

        private FlingRunnable(Context context) {
            if (OSUtils.isEMUI()) {
                mScroller = new ScrollerCompat(context, mInterpolator);
                mScroller.setFriction(friction);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    mScroller = new OverScrollerCompat(context, mInterpolator);
                    mScroller.setFriction(friction);
                } else {
                    mScroller = new ScrollerCompat(context, mInterpolator);
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

            onScrolling(mScroller.getCurrX(), mScroller.getCurrY(),
                    mScrollState == SCROLL_STATE_SCROLLING && mScroller.isFinished());
        }

        public void stop() {
            if (!mScroller.isFinished())
                mScroller.abortAnimation();
        }

        public void fling() {
            if (mOrientation == HORIZENTAL) {
                mScroller.fling((int) (mCurrentX), 0, (int) mTracker.getXVelocity(), 0, mMinScrollOffset, mMaxScrollOffset, 0, 0, mOverOffset, 0);
            } else {
                mScroller.fling(0, (int) (mCurrentY), 0, (int) mTracker.getYVelocity(), 0, 0, mMinScrollOffset, mMaxScrollOffset, 0, mOverOffset);
            }
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
        if (mAnimController == null) {
            mAnimController = new TransLateAnim();
        } else {
            mAnimController.forceStop();
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

        mAnimController.setTransLate(transLateX, transLateY);

        ViewCompat.postOnAnimation(ScrollWheelPicker.this, mCorrectRunnable);
        mAnimController.start();
    }

    /**
     * stop correction animation
     */
    protected void stopCorrectAnimation() {
        mCorrectRunnable.stop();
    }

    /**
     * The animation runnable of correcting the location
     */
    private class CorrectAnimRunnable implements Runnable {

        public void run() {
            boolean running = mAnimController.calculate(SystemClock.uptimeMillis());
            if (running) {
                onScrolling(mCurrentX, mCurrentY, false);
                ViewCompat.postOnAnimation(ScrollWheelPicker.this, this);
            } else {
                mCurrentX = mAnimController.getFinalX();
                mCurrentY = mAnimController.getFinalY();
                onScrolling(mCurrentX, mCurrentY, true);

                mScrollState = SCROLL_STATE_IDLE;
            }
        }

        public void stop() {
            mAnimController.forceStop();
        }
    }

    /**
     * Translate animation
     */
    private class TransLateAnim extends Animation {
        private float mStartX;
        private float mStartY;
        private float mTransLateX;
        private float mTransLateY;

        public TransLateAnim() {
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
}
