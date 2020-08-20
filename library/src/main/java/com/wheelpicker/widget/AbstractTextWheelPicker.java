package com.wheelpicker.widget;

import com.wheelpicker.R;
import com.wheelpicker.core.ScrollWheelPicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

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
public abstract class AbstractTextWheelPicker extends ScrollWheelPicker<TextBaseAdapter> {
    protected int mUnitDegree;

    protected int mTextColor;
    protected float mTextSize;

    protected Rect mItemBounds = new Rect();
    private Point mItemSize = new Point();

    //是否自动测量item宽高
    private boolean mAutoMeasureItemHeight = true;
    private boolean mAutoMeasureItemWidth = true;

    private boolean mIsDatePicker;


    public AbstractTextWheelPicker(Context context) {
        super(context);
    }

    public AbstractTextWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractTextWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void obtainAttrs(AttributeSet attrs) {
        super.obtainAttrs(attrs);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);
            mTextColor = a.getInt(R.styleable.WheelPicker_wheel_text_color, -1);
            mTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_text_size, 50);
            a.recycle();
        }
    }

    @Override
    protected void instantiation() {
        super.instantiation();
        mPaint.setTextAlign(Paint.Align.CENTER);
        if (mTextColor > 0) {
            mPaint.setColor(mTextColor);
        }

        if (mTextSize > 0) {
            mPaint.setTextSize(mTextSize);
        }
    }

    @Override
    public void setAdapter(TextBaseAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    protected void initItemSize() {
        if (mAdapter == null || mAdapter.isEmpty()) {
            return;
        }

        if (!mAutoMeasureItemWidth && !mAutoMeasureItemHeight) {
            return;
        }

        //TODO to be optimized
        if (mOrientation == HORIZENTAL && mAutoMeasureItemWidth) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                CharSequence cs = mAdapter.getItemText(i);
                String text = cs == null ? "" : cs.toString();
                Point p = computeItemSize(text);

                if (mAutoMeasureItemWidth) {
                    mItemMaxWidth = Math.max(mItemMaxWidth, p.x);
                }
            }
        }

        if (mOrientation == VETTAICL && mAutoMeasureItemHeight) {
            mPaint.setTextSize(mTextSize);
            FontMetrics ftms = mPaint.getFontMetrics();
            mItemMaxHeight = (int) Math.abs(ftms.bottom - ftms.top);
        }
    }

    public void setAutoMeasureSize(boolean autoWidth, boolean autoHeight) {
        mAutoMeasureItemWidth = autoWidth;
        mAutoMeasureItemHeight = autoHeight;
    }

    public void setItemWidth(int itemWidth) {
        mItemMaxWidth = itemWidth;
    }

    public void setItemHeight(int itemHeight) {
        mItemMaxHeight = itemHeight;
    }

    public void setIsDatePicker(boolean isDatePicker) {
        mIsDatePicker = isDatePicker;
    }

    public void setGravity() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        // h = descent + (-ascent); h / 2 + z = -ascent;
        // 求得z = (ascent + descent) / 2; 即中线到绘制文字基线的距离
        mWheelCenterTextY = mWheelCenterY - (mPaint.ascent() + mPaint.descent()) / 2.0f;

    }

    public void setTextColor(int textColor) {
        mColor = textColor;
        mPaint.setColor(textColor);
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mPaint.setTextSize(textSize);
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        mLinePaint.setColor(lineColor);
    }

    public void setLineStorkeWidth(float width) {
        mLinePaint.setStrokeWidth(width);
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        if (itemWidth > 0) {
            mItemMaxWidth = itemWidth;
        }

        if (itemHeight > 0) {
            mItemMaxHeight = itemHeight;
        }
    }

    public Point computeItemSize(String text) {
        if (mPaint == null || TextUtils.isEmpty(text)) {
            mItemSize.set(0, 0);
            return mItemSize;
        }
        mPaint.getTextBounds(text, 0, text.length(), mItemBounds);
        mItemSize.set(mItemBounds.width(), mItemBounds.height());
        return mItemSize;
    }

    protected float computeSpace(float degree, float radius) {
        return (float) Math.sin(Math.toRadians(degree)) * radius;
    }

    protected float computeDepth(float degree, float radius) {
        return (float) (radius - Math.cos(Math.toRadians(degree)) * radius);
    }

    protected void correctLocation(int itemIndex, float offsetItemX, float offsetItemY) {
        if (Math.abs(offsetItemY) < 0.01f) {
            return;
        }

        float distance;
        if (offsetItemY > 0) {
            if (offsetItemY > mUnitDegree / 2) {
                distance = mUnitDegree - offsetItemY;
            } else {
                distance = -offsetItemY;
            }
        } else {
            if (Math.abs(offsetItemY) > mUnitDegree / 2)
                distance = -mUnitDegree - offsetItemY;
            else
                distance = -offsetItemY;
        }

        startCorrectAnimation(0, distance);
    }

    protected abstract void draw(Canvas canvas, Paint paint, String data, float space, float x, float y);
}
