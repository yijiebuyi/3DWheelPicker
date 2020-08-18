package com.wheelpicker.widget;

import android.view.View;

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

public interface IPickerView {
    /**
     * 设置Item的文本大小
     * @param textSize
     */
    public void setTextSize(int textSize);

    /**
     * 设置item文本的颜色
     * @param textColor
     */
    public void setTextColor(int textColor);

    /**
     * 设置item分割线的颜色
     * @param lineColor
     */
    public void setLineColor(int lineColor);

    /**
     * 设置item分割线的宽度
     * @param width
     */
    public void setLineWidth(int width);

    /**
     * 设置item的space
     * @param
     */
    public void setItemSpace(int space);

    /**
     * 设置显示的item counte
     * @param itemCount
     */
    public void setVisibleItemCount(int itemCount);

    /**
     * 设置item的宽高
     * @param itemWidth
     * @param itemHeight
     */
    public void setItemSize(int itemWidth, int itemHeight);

    public View asView();
}
