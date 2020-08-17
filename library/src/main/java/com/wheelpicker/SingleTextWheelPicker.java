package com.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.widget.TextWheelPicker;

import org.w3c.dom.Text;

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

public class SingleTextWheelPicker extends TextWheelPicker implements IPickerView {
    public SingleTextWheelPicker(Context context) {
        super(context);
    }

    public SingleTextWheelPicker(Context context, int id) {
        super(context, id);
    }

    public SingleTextWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleTextWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTextSize(int textSize) {
        super.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        super.setTextColor(textColor);
    }

    public void setLineColor(int lineColor) {
        super.setLineColor(lineColor);
    }

    public void setLineWidth(int width) {
        super.setLineStorkeWidth(width);
    }

    public void setItemSpace(int space) {
        super.setItemSpace(space);
    }

    public void setVisibleItemCount(int itemCount) {
        super.setVisibleItemCount(itemCount);
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        super.setItemSize(itemWidth, itemHeight);
    }

}
