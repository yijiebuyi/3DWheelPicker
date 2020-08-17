package com.wheelpicker;

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
    /**
     * 年月日，时分秒，哪些需要显示的
     * @see com.wheelpicker.DateWheelPicker
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
    private int shadowFactor;

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

    public int getShadowFactor() {
        return shadowFactor;
    }

    private PickOption(Builder builder) {
        dateWitchVisible = builder.dateWitchVisible;
        durationDays = builder.durationDays;
        aheadYears = builder.aheadYears;
        afterYears = builder.afterYears;

        visibleItemCount = builder.visibleItemCount;
        itemTextColor = builder.itemTextColor;
        itemTextSize = builder.itemTextSize;
        itemLineColor = builder.itemLineColor;
        itemLineWidth = builder.itemLineWidth;
        backgroundColor = builder.backgroundColor;
        verPadding = builder.verPadding;
        horPadding = builder.horPadding;
        shadowGravity = builder.shadowGravity;
        shadowFactor = builder.shadowFactor;
    }

    /**
     * Builder
     */
    public static final class Builder {
        private int dateWitchVisible;
        private int durationDays;
        private int aheadYears;
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
        private int shadowFactor;

        //默认配置
        public Builder() {
            dateWitchVisible = DateWheelPicker.TYPE_ALL;
            durationDays = 365;
            aheadYears = 100;
            afterYears = 100;

            visibleItemCount = 5;
            itemTextColor = 0XFF333333;
            itemLineColor = 0xBB000000;
            backgroundColor = 0xFFFFFFFF;
        }

        public Builder setDateWitchVisible(int dateWitchVisible) {
            this.dateWitchVisible = dateWitchVisible;
            return this;
        }

        public Builder setDurationDays(int durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public Builder setAheadYears(int aheadYears) {
            this.aheadYears = aheadYears;
            return this;
        }

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

        public Builder setShadowFactor(int shadowFactor) {
            this.shadowFactor = shadowFactor;
            return this;
        }

        public PickOption build() {
            return new PickOption(this);
        }
    }
}
