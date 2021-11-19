package com.wheelpicker;

import android.content.Context;
import android.graphics.Color;

import com.wheelpicker.widget.AbstractViewWheelPicker;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class PickOption {
    //===============bottomSheet style=============
    private int leftTitleColor;
    private int rightTitleColor;
    private int middleTitleColor;
    private String leftTitleText;
    private String rightTitleText;
    private String middleTitleText;
    private int titleHeight;
    private int titleBackground;

    //===============date pick attr=================
    /**
     * 年月日，时分秒，哪些需要显示的
     * @see com.wheelpicker.DateTimePicker
     */
    private int dateWitchVisible;
    /**
     * 距离当前时间往后显示的天数，（只涉及未来日期模式）
     */
    private int durationDays;
    /**
     * 距离当前时间，往前显示多少年
     */
    private int aheadYears;
    /**
     * 距离当前时间，往后显示多少年
     */
    private int afterYears;

    //=================pick style=====================
    /**
     * 显示的item数量，必须是单数
     */
    private int visibleItemCount;
    /**
     * pickerView item的文本颜色
     */
    private int itemTextColor;
    /**
     * pickerView item的文本字体大小
     */
    private int itemTextSize;
    /**
     * pickerView item的间距
     */
    private int itemSpace;
    /**
     * pickerView line颜色
     */
    private int itemLineColor;
    /**
     * pickerView Line宽度
     */
    private int itemLineWidth;
    /**
     * pickerView 背景颜色
     */
    private int backgroundColor;
    /**
     * pickerView verPadding
     */
    private int verPadding;
    /**
     * pickerView horPadding
     */
    private int horPadding;
    /**
     * pickerView 滚轮偏向
     */
    private int shadowGravity;
    /**
     * pickerView 滚轮偏向因子（0.0 ~ 1.0）
     */
    private float shadowFactor;
    /**
     * 手指滑动，滚轮跟随滚动因子
     */
    private float fingerMoveFactor;
    /**
     * fling滚动阻尼因子
     */
    private float flingAnimFactor;
    /**
     * 回弹的偏移量
     */
    private int overScrollOffset;

    public int getDateWitchVisible() {
        return dateWitchVisible;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public int getAheadYears() {
        return aheadYears;
    }

    public int getAfterYears() {
        return afterYears;
    }

    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

    public int getItemTextSize() {
        return itemTextSize;
    }

    public int getItemSpace() {
        return itemSpace;
    }

    public int getItemLineColor() {
        return itemLineColor;
    }

    public int getItemLineWidth() {
        return itemLineWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getVerPadding() {
        return verPadding;
    }

    public int getHorPadding() {
        return horPadding;
    }

    public int getShadowGravity() {
        return shadowGravity;
    }

    public float getShadowFactor() {
        return shadowFactor;
    }

    public float getFingerMoveFactor() {
        return fingerMoveFactor;
    }

    public float getFlingAnimFactor() {
        return flingAnimFactor;
    }

    public int getOverScrollOffset() {
        return overScrollOffset;
    }

    public int getLeftTitleColor() {
        return leftTitleColor;
    }

    public int getRightTitleColor() {
        return rightTitleColor;
    }

    public int getMiddleTitleColor() {
        return middleTitleColor;
    }

    public String getLeftTitleText() {
        return leftTitleText;
    }

    public String getRightTitleText() {
        return rightTitleText;
    }

    public String getMiddleTitleText() {
        return middleTitleText;
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public int getTitleBackground() {
        return titleBackground;
    }

    private PickOption(Builder builder) {
        leftTitleText = builder.leftTitleText;
        leftTitleColor = builder.leftTitleColor;
        rightTitleText = builder.rightTitleText;
        rightTitleColor = builder.rightTitleColor;
        middleTitleText = builder.middleTitleText;
        middleTitleColor = builder.middleTitleColor;
        titleHeight = builder.titleHeight;
        titleBackground = builder.titleBackground;

        dateWitchVisible = builder.dateWitchVisible;
        durationDays = builder.durationDays;
        aheadYears = builder.aheadYears;
        afterYears = builder.afterYears;

        visibleItemCount = builder.visibleItemCount;
        itemTextColor = builder.itemTextColor;
        itemTextSize = builder.itemTextSize;
        itemSpace = builder.itemSpace;
        itemLineColor = builder.itemLineColor;
        itemLineWidth = builder.itemLineWidth;
        backgroundColor = builder.backgroundColor;
        verPadding = builder.verPadding;
        horPadding = builder.horPadding;
        shadowGravity = builder.shadowGravity;
        shadowFactor = builder.shadowFactor;

        flingAnimFactor = builder.flingAnimFactor;
        fingerMoveFactor = builder.fingerMoveFactor;
        overScrollOffset = builder.overScrollOffset;
    }

    /**
     * Builder
     */
    public static final class Builder {
        //===============bottomSheet style=============
        private int leftTitleColor;
        private int rightTitleColor;
        private int middleTitleColor;
        private String leftTitleText;
        private String rightTitleText;
        private String middleTitleText;
        private int titleHeight;
        private int titleBackground;

        //===============date pick attr=================
        private int dateWitchVisible;
        private int durationDays;
        @Deprecated
        private int aheadYears;
        @Deprecated
        private int afterYears;

        //=================pick style=====================
        private int visibleItemCount;
        private int itemTextColor;
        private int itemTextSize;
        private int itemSpace;
        private int itemLineColor;
        private int itemLineWidth;
        private int backgroundColor;
        private int verPadding;
        private int horPadding;
        private int shadowGravity;
        private float shadowFactor;
        private float fingerMoveFactor;
        private float flingAnimFactor;
        private int overScrollOffset;

        //默认配置
        public Builder() {
            dateWitchVisible = DateTimePicker.TYPE_ALL;
            durationDays = 365;
            aheadYears = 100;
            afterYears = 100;

            visibleItemCount = 7;
            itemTextColor = 0XFF333333;
            itemLineColor = 0xFFCCCCCC;
            backgroundColor = 0xFFFFFFFF;
            shadowGravity = AbstractViewWheelPicker.SHADOW_RIGHT;
            shadowFactor = 0.4f;

            fingerMoveFactor = 1.0f;
            flingAnimFactor = 0.7f;
        }

        public Builder setLeftTitleColor(int leftTitleColor) {
            this.leftTitleColor = leftTitleColor;
            return this;
        }

        public Builder setRightTitleColor(int rightTitleColor) {
            this.rightTitleColor = rightTitleColor;
            return this;
        }

        public Builder setMiddleTitleColor(int middleTitleColor) {
            this.middleTitleColor = middleTitleColor;
            return this;
        }

        public Builder setLeftTitleText(String leftTitleText) {
            this.leftTitleText = leftTitleText;
            return this;
        }

        public Builder setRightTitleText(String rightTitleText) {
            this.rightTitleText = rightTitleText;
            return this;
        }

        public Builder setMiddleTitleText(String middleTitleText) {
            this.middleTitleText = middleTitleText;
            return this;
        }

        public Builder setTitleHeight(int titleHeight) {
            this.titleHeight = titleHeight;
            return this;
        }

        public Builder setTitleBackground(int titleBackground) {
            this.titleBackground = titleBackground;
            return this;
        }

        public Builder setDateWitchVisible(int dateWitchVisible) {
            this.dateWitchVisible = dateWitchVisible;
            return this;
        }

        public Builder setDurationDays(int durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        @Deprecated
        public Builder setAheadYears(int aheadYears) {
            this.aheadYears = aheadYears;
            return this;
        }

        @Deprecated
        public Builder setAfterYears(int afterYears) {
            this.afterYears = afterYears;
            return this;
        }

        public Builder setVisibleItemCount(int visibleItemCount) {
            this.visibleItemCount = visibleItemCount;
            return this;
        }

        public Builder setItemTextColor(int itemTextColor) {
            this.itemTextColor = itemTextColor;
            return this;
        }

        public Builder setItemTextSize(int itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        public Builder setItemSpace(int itemSpace) {
            this.itemSpace = itemSpace;
            return this;
        }

        public Builder setItemLineColor(int itemLineColor) {
            this.itemLineColor = itemLineColor;
            return this;
        }

        public Builder setItemLineWidth(int itemLineWidth) {
            this.itemLineWidth = itemLineWidth;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setVerPadding(int verPadding) {
            this.verPadding = verPadding;
            return this;
        }

        public Builder setHorPadding(int horPadding) {
            this.horPadding = horPadding;
            return this;
        }

        public Builder setShadowGravity(int shadowGravity) {
            this.shadowGravity = shadowGravity;
            return this;
        }

        public Builder setShadowFactor(float shadowFactor) {
            this.shadowFactor = shadowFactor;
            return this;
        }

        public Builder setFingerMoveFactor(float fingerMoveFactor) {
            this.fingerMoveFactor = fingerMoveFactor;
            return this;
        }

        public Builder setFlingAnimFactor(float flingAnimFactor) {
            this.flingAnimFactor = flingAnimFactor;
            return this;
        }

        public Builder setOverScrollOffset(int overScrollOffset) {
            this.overScrollOffset = overScrollOffset;

            return this;
        }

        public PickOption build() {
            return new PickOption(this);
        }
    }

    /**
     * 获取Pick默认的设置
     * @param context
     * @return
     */
    public static PickOption.Builder getPickDefaultOptionBuilder(Context context) {
        PickOption.Builder builder = new PickOption.Builder()
                .setVisibleItemCount(9)
                .setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px20))
                .setItemTextColor(context.getResources().getColor(R.color.font_black))
                .setItemTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_36px))
                .setVerPadding(context.getResources().getDimensionPixelSize(R.dimen.px20))
                .setShadowGravity(AbstractViewWheelPicker.SHADOW_RIGHT)
                .setShadowFactor(0.5f)
                .setFingerMoveFactor(0.8f)
                .setFlingAnimFactor(0.7f)
                .setOverScrollOffset(context.getResources().getDimensionPixelSize(R.dimen.px36))
                .setBackgroundColor(Color.WHITE);

        return builder;
    }
}
