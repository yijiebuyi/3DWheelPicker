package com.wheelpicker.widget;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.wheelpicker.R;
import com.wheelpicker.core.OnWheelPickedListener;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：The wheel picker for text
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class TextWheelPicker extends AbstractTextWheelPicker {
    private final static String TAG = "TextWheelPicker";
    private final static float DEPTH_FACTOR = 0.6F; //0 ~ 1.0F
    private final Camera mCamera = new Camera();
    private final Matrix mRotateMatrix = new Matrix();
    private final Matrix mDepthMatrix = new Matrix();
    private final RectF mCurrentItemRect = new RectF();

    private int mRadius;

    private int mOldOffsetItemIndex = 0;
    private int mOffsetItemIndex = 0;
    private float mOffsetItemDelta = 0;

    private float mShadowOffset = 0;
    private float mLineOffset = 0;

    private float mRelRadius;

    private String mPickedData;
    private int mPickedIndex;

    private OnWheelPickedListener mOnWheelPickedListener;

    public TextWheelPicker(Context context) {
        super(context);
    }

    public TextWheelPicker(Context context, int id) {
        super(context);
        setId(id);
    }

    public TextWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void computeWheelSize() {
        mRadius = mWheelPickerImpl.computeRadius(mVisibleItemCount, mItemSpace, mItemMaxWidth, mItemMaxHeight);
        mUnitDegree = (int) (180 * 1.0F / mVisibleItemCount);

        mWheelContentWidth = mWheelPickerImpl.getWheelWidth(mRadius, mItemMaxWidth);
        mWheelContentHeight = mWheelPickerImpl.getWheelHeight(mRadius, mItemMaxHeight);

        mLineOffset = mItemMaxHeight / 2 + mItemSpace * 0.8f;

        mShadowOffset = SHADOW_MAX * mShadowFactor;
        mRelRadius = mRadius * DEPTH_FACTOR;

        if (mAdapter != null) {
            mOldOffsetItemIndex = 0;

            if (mCurrItemIndex < 0) {
                mCurrItemIndex = 0;
            }

            if (mCurrItemIndex >= mAdapter.getCount()) {
                mCurrItemIndex = mAdapter.getCount() - 1;
            }

            setScrollRange(-(mAdapter.getCount() - 1 - mCurrItemIndex) * mUnitDegree, mCurrItemIndex * mUnitDegree);
        }
    }

    @Override
    protected void drawBackground(Canvas canvas) {
        if (mAdapter != null && !mAdapter.isEmpty()) {
            canvas.drawLine(0, mWheelCenterY - mLineOffset, mViewWidth,
                    mWheelCenterY - mLineOffset, mLinePaint);
            canvas.drawLine(0, mWheelCenterY + mLineOffset, mViewWidth,
                    mWheelCenterY + mLineOffset, mLinePaint);

            mCurrentItemRect.set(0, mWheelCenterY - mLineOffset,
                    mViewWidth, mWheelCenterY + mLineOffset);
        }
    }

    @Override
    protected void drawItems(Canvas canvas) {
        if (mAdapter == null || mAdapter.isEmpty()) {
            return;
        }

        for (int i = mItemStartIndex; i <= mItemEndIndex; i++) {
            float rotateDegree = mUnitDegree * (i - mCurrItemIndex) + mOffsetItemDelta;
            if (rotateDegree > 90 || rotateDegree < -90) {
                continue;
            }

            //为了避免角度太小计算产生误差，所以进行一个校正
            if (Math.abs(rotateDegree) < 0.1f) {
                if (rotateDegree < 0) {
                    rotateDegree = -0.1f;
                } else {
                    rotateDegree = 0.1f;
                }
            }

    float space = computeSpace(rotateDegree, mRadius);
    float relDegree = Math.abs(rotateDegree) / 90;
    canvas.save();
    mCamera.save();
    mRotateMatrix.reset();

    //apply gravity
    if (mShadowGravity == SHADOW_RIGHT) {
        mCamera.translate(-mShadowOffset, 0, 0);
    } else if (mShadowGravity == SHADOW_LEFT) {
        mCamera.translate(mShadowOffset, 0, 0);
    }
    //rotate
    mWheelPickerImpl.rotateCamera(mCamera, rotateDegree);
    mCamera.getMatrix(mRotateMatrix);
    mCamera.restore();
    mWheelPickerImpl.matrixToCenter(mRotateMatrix, space, mWheelCenterX, mWheelCenterY);
    //apply gravity
    if (mShadowGravity == SHADOW_RIGHT) {
        mRotateMatrix.postTranslate(mShadowOffset, 0);
    } else if (mShadowGravity == SHADOW_LEFT) {
        mRotateMatrix.postTranslate(-mShadowOffset, 0);
    }

    float depth = computeDepth(rotateDegree, mRelRadius);
    mCamera.save();
    mDepthMatrix.reset();
    mCamera.translate(0, 0, depth);
    mCamera.getMatrix(mDepthMatrix);
    mCamera.restore();
    mWheelPickerImpl.matrixToCenter(mDepthMatrix, space, mWheelCenterX, mWheelCenterY);

    mRotateMatrix.postConcat(mDepthMatrix);
    canvas.concat(mRotateMatrix);

            /*if (i == getHighLightItem(mCurrItemIndex)) {
                mPaint.setAlpha(255);
            } else {
                mPaint.setAlpha(128 - (int) (128 * relDegree));
            }*/
            //先绘制文本渐变，离当前item越远，alpha值越小
            mPaint.setAlpha(128 - (int) (128 * relDegree));
            draw(canvas, mPaint, mAdapter.getItemText(i), space, mWheelCenterX, mWheelCenterTextY);

            //设置当前选中item为非透明
            mPaint.setAlpha(255);
            canvas.clipRect(mCurrentItemRect);
            draw(canvas, mPaint, mAdapter.getItemText(i), space, mWheelCenterX, mWheelCenterTextY);

            canvas.restore();
        }

    }

    @Override
    protected void drawForeground(Canvas canvas) {

    }

    public void setOnWheelPickedListener(OnWheelPickedListener listener) {
        mOnWheelPickedListener = listener;
    }

    @Override
    protected void onWheelSelected(boolean touch, int index) {
        if (mAdapter != null && index > -1 && index < mAdapter.getCount()) {
            setPickedItemIndex(index);
            mPickedData = mAdapter.getItemText(index);
            mPickedIndex = index;
            if (mOnWheelPickedListener != null) {
                mOnWheelPickedListener.onWheelSelected(this, index, mPickedData, touch);
            }
        } else {
            Log.i(TAG, "error index:" + index);
        }
    }

    @Override
    public void onScrolling(float offsetX, float offsetY, boolean isFinshed) {
        mOffsetItemIndex = (int) (offsetY / mUnitDegree);
        mOffsetItemDelta = offsetY % mUnitDegree;
        if (mOffsetItemIndex != mOldOffsetItemIndex) {
            mCurrItemIndex = mCurrItemIndex - (mOffsetItemIndex - mOldOffsetItemIndex);
        }

        mOldOffsetItemIndex = mOffsetItemIndex;
        updateItemIndexRange();
        postInvalidate();

        if (isFinshed) {
            correctLocation(mOffsetItemIndex, 0, mOffsetItemDelta);
            if (Math.abs(mOffsetItemDelta) < 0.01f) {
                onWheelSelected(true, mCurrItemIndex);
            }
        }
    }

    @Override
    protected void draw(Canvas canvas, Paint paint, String data, float space, float x, float y) {
        fitTextSize(data, paint);
        canvas.drawText(/*getDrawText(data)*/data, x, y + space, paint);
    }

    /**
     * 调整text的大小，让所有文字都可以全部显示到view中
     * @param text
     * @param paint
     */
    private void fitTextSize(String text, Paint paint) {
        float fitTextSize = mTextSize;
        if (mTextSize == 0) {
            return;
        }

        paint.setTextSize(mTextSize);
        float currWidth = paint.measureText(text);
        while (currWidth > mViewWidth) {
            fitTextSize = fitTextSize - 4;
            paint.setTextSize(fitTextSize);
            currWidth = paint.measureText(text);
        }
    }

    @Override
    public int getCurrentItem() {
        int currentItem = super.getCurrentItem();
        if (currentItem < 0) {
            return 0;
        }

        if (mAdapter == null) {
            return currentItem;
        }

        if (currentItem >= mAdapter.getCount()) {
            currentItem = mAdapter.getCount() - 1;
        }

        return currentItem;
    }

    @Override
    public void setCurrentItem(int index) {
        mOldOffsetItemIndex = 0;
        mOffsetItemIndex = 0;
        super.setCurrentItem(index);
    }

    /**
     * 获取高亮显示的item索引
     * 已弃用，转至
     * @see #fitTextSize(String, Paint) 
     * @param item
     * @return
     */
    @Deprecated
    private int getHighLightItem(int item) {
        if (mOffsetItemDelta > 0) {
            if (mOffsetItemDelta > mUnitDegree / 2) {
                return item - 1;
            } else {
                return item;
            }
        } else {
            if (Math.abs(mOffsetItemDelta) > mUnitDegree / 2)
                return item + 1;
            else
                return item;
        }
    }

    /**
     * 根据view的宽度截取字符串
     * @param data
     * @return
     */
    private String getDrawText(String data) {
        if (data == null) {
            return data;
        }

        float itemWidth = mItemMaxWidth;
        int viewWidth = getMeasuredWidth();
        int len = data.length();
        while (itemWidth > viewWidth && len > 0) {
            mPaint.getTextBounds(data, 0, --len, mItemBounds);
            itemWidth = mItemBounds.width();
        }

        return data.substring(0, len);
    }

    /**
     * 获取选择的数据
     *
     * @return
     */
    public String getPickedData() {
        return mPickedData;
    }

    /**
     * 获取选择的索引
     *
     * @return
     */
    public int getPickedIndex() {
        return mPickedIndex;
    }
}
