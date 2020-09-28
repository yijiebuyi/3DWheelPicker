package com.wheelpicker.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.wheelpicker.R;


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
public abstract class AbstractWheelPicker<T extends WheelPickerAdapter> extends View {
    private static final int VELOCITY_TRACKER_UNITS_DEFAULT = 600;

    private static final int DEFAULT_INDEX = 0;
    private static final int DEFAULT_COUNT = 5;
    private static final int DEFAULT_SPACE = 20;
    private static final int DEFAULT_COLOR = 0xFF000000;
    /**
     * 默认分割线颜色
     */
    private static final int DEFAULT_LINE_COLOR = 0XFFDDDDDD;

    protected VelocityTracker mTracker;

    public static final int SHADOW_LEFT = 0;
    public static final int SHADOW_MIDDLE = 1;
    public static final int SHADOW_RIGHT = 2;
    protected static final int SHADOW_MAX = 200;

    // 滚轮是偏右还是左
    protected int mShadowGravity = SHADOW_RIGHT;
    protected float mShadowFactor = 0.4f; //0 ~ 1.0f;

    protected Paint mPaint;
    protected Paint mLinePaint;

    protected int mWheelContentWidth;
    protected int mWheelContentHeight;

    protected int mViewWidth;
    protected int mViewHeight;

    protected Rect mBounds;
    protected float mWheelCenterX;
    protected float mWheelCenterY;
    protected float mWheelCenterTextY;

    /**
     * Default count of visible items
     */
    protected int mVisibleItemCount;
    protected int mItemSpace;
    /**
     * 当前变化的索引值，(当被初始化时，显示第几个item)
     */
    protected int mCurrItemIndex;
    /**
     *  滚动index时的索引值使用mPickedItemIndex表示
     */
    protected int mPickedItemIndex;
    protected int mColor;
    protected int mLineColor;
    protected int mLineStrokeWidth;

    protected int mItemStartIndex;
    protected int mItemEndIndex;

    protected int mItemMaxWidth;
    protected int mItemMaxHeight;

    protected boolean mIgnorePadding;
    protected T mAdapter;
    protected AdapterDataSetObserver mDataSetObserver;

    protected float mOldX;
    protected float mOldY;
    protected float mCurrentX;
    protected float mCurrentY;
    protected float mDeltaX;
    protected float mDeltaY;

    //是否可以响应滑动事件
    private boolean mTouchable = true;

    public AbstractWheelPicker(Context context) {
        super(context);
        init(null);
    }

    public AbstractWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbstractWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        obtainAttrs(attrs);
        instantiation();
        //computeWheelSize();
    }

    protected void obtainAttrs(AttributeSet attrs) {
        if (attrs == null) {
            mColor = DEFAULT_COLOR;
            mLineColor = DEFAULT_LINE_COLOR;
            mCurrItemIndex = DEFAULT_INDEX;
            mVisibleItemCount = DEFAULT_COUNT;
            mItemSpace = DEFAULT_SPACE;
            mLineStrokeWidth = 1;
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);

            mCurrItemIndex = a.getInt(R.styleable.WheelPicker_wheel_item_index, DEFAULT_INDEX);
            mVisibleItemCount = a.getInt(R.styleable.WheelPicker_wheel_visible_item_count, DEFAULT_COUNT);
            mItemSpace = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_item_space, DEFAULT_SPACE);
            mColor = a.getColor(R.styleable.WheelPicker_wheel_text_color, DEFAULT_COLOR);
            mLineColor = a.getColor(R.styleable.WheelPicker_wheel_line_color, DEFAULT_LINE_COLOR);
            mLineStrokeWidth = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_line_width, 1);
            a.recycle();
        }
    }

    protected void instantiation() {
        mCurrentX = 0;
        mCurrentY = 0;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mColor);
        mPaint.setTextSize(70);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Style.FILL);
        mLinePaint.setStrokeWidth(mLineStrokeWidth);

        mBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mWheelContentWidth;
        int resultHeight = mWheelContentHeight;

        resultWidth += (getPaddingLeft() + getPaddingRight());
        resultHeight += (getPaddingTop() + getPaddingBottom());
        resultWidth = measureSize(modeWidth, sizeWidth, resultWidth);
        resultHeight = measureSize(modeHeight, sizeHeight, resultHeight);

        mViewWidth = resultWidth;
        mViewHeight = resultHeight;

        setMeasuredDimension(resultWidth, resultHeight);
    }

    protected int measureSize(int mode, int sizeExpect, int sizeActual) {
        int realSize;
        if (mode == MeasureSpec.EXACTLY) {
            realSize = sizeExpect;
        } else {
            realSize = sizeActual;
            if (mode == MeasureSpec.AT_MOST) {
                realSize = Math.min(realSize, sizeExpect);
            }
        }
        return realSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mBounds.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        mWheelCenterX = mBounds.centerX();
        mWheelCenterY = mBounds.centerY();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);

        canvas.save();
        canvas.clipRect(mBounds);
        drawItems(canvas);
        canvas.restore();

        drawForeground(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mTouchable) {
            return true;
        }

        if (null == mTracker) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                mOldY = event.getY();
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mDeltaX = event.getX() - mOldX;
                mDeltaY = event.getY() - mOldY;
                mOldX = event.getX();
                mOldY = event.getY();

                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(VELOCITY_TRACKER_UNITS_DEFAULT);
                onTouchUp(event);

                mTracker.recycle();
                mTracker = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                mTracker.recycle();
                mTracker = null;
                onTouchCancel(event);

                break;
        }
        return true;

    }

    public void setCurrentItem(int index) {
        mCurrItemIndex = index;
        mPickedItemIndex = index;
        requestComputeLayout();
    }

    public void setCurrentItemWithoutReLayout(int index) {
        mCurrItemIndex = index;
        mPickedItemIndex = index;
    }

    public int getCurrentItem() {
        return mCurrItemIndex;
    }

    public void setPickedItemIndex(int index) {
        mPickedItemIndex = index;
    }

    public int getPickedItemIndex() {
        return mPickedItemIndex;
    }

    public void setItemSpace(int space) {
        mItemSpace = space;
        if (mAdapter != null && !mAdapter.isEmpty()) {
            requestComputeLayout();
        }
    }

    public void setVisibleItemCount(int count) {
        mVisibleItemCount = count;
        if (mAdapter != null && !mAdapter.isEmpty()) {
            requestComputeLayout();
        }
    }

    public void setShadowGravity(int gravity) {
        mShadowGravity = gravity;
    }

    public void setShadowFactor(float factor) {
        if (factor > 1.0) {
            factor = 1.0f;
        }

        if (factor < 0) {
            factor = 0;
        }

        mShadowFactor = factor;
    }

    public synchronized void setAdapter(T adapter) {
        if (adapter == null) {
            return;
        }

        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }

        mAdapter = adapter;
        if (mCurrItemIndex > mAdapter.getCount() || mCurrItemIndex < 0) {
            mCurrItemIndex = 0;
        }

        if (mDataSetObserver == null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
            mDataSetObserver.onChanged();
        }

        requestComputeLayout();
    }

    public T getAdapter() {
        return mAdapter;
    }

    public void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }

    protected void updateItemIndexRange() {
        if (mAdapter != null && !mAdapter.isEmpty()) {
            mItemStartIndex = Math.max(0, mCurrItemIndex - mVisibleItemCount / 2 - 1);
            mItemEndIndex = Math.min(mCurrItemIndex + mVisibleItemCount / 2 + 1, mAdapter.getCount() - 1);
        }
    }

    public void requestComputeLayout() {
        if ((mAdapter != null && mCurrItemIndex >= mAdapter.getCount())) {
            mCurrItemIndex = mAdapter.getCount() - 1;
        }

        resetScroll();
        initItemSize();
        computeWheelSize();
        updateItemIndexRange();
        postInvalidate();
    }

    /**
     * ==============abstract method==============
     */
    protected abstract void initItemSize();

    protected abstract void computeWheelSize();

    protected abstract void resetScroll();

    protected abstract void drawBackground(Canvas canvas);

    protected abstract void drawItems(Canvas canvas);

    protected abstract void drawForeground(Canvas canvas);

    protected abstract void onTouchDown(MotionEvent event);

    protected abstract void onTouchMove(MotionEvent event);

    protected abstract void onTouchUp(MotionEvent event);

    protected abstract void onTouchCancel(MotionEvent event);

    /**
     * ==============abstract method==============
     */

    protected void onWheelSelected(boolean touch, int index) {

    }

    class AdapterDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            int count = 0;
            if (mAdapter != null) {
                count = mAdapter.getCount();
            }
            if (mCurrItemIndex > count - 1) {
                mCurrItemIndex = count - 1;
            }
            if (mCurrItemIndex < 0) {
                mCurrItemIndex = 0;
            }
            onWheelSelected(false, mCurrItemIndex);
            requestComputeLayout();
        }

        @Override
        public void onInvalidated() {
            invalidate();
        }
    }

}
