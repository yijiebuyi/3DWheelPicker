package com.wheelpicker;

import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：
 *
 * 作者：yijiebuyi
 * 创建时间：2021/11/13
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class DateTimeItem {
    public final static int TYPE_YEAR = 1 << 1;
    public final static int TYPE_MONTH = 1 << 2;
    public final static int TYPE_DAY = 1 << 3;
    public final static int TYPE_HOUR = 1 << 4;
    public final static int TYPE_MINUTE = 1 << 5;
    public final static int TYPE_SECOND = 1 << 6;

    private int type;
    private String label;
    private TextWheelPicker wheelPicker;

    public DateTimeItem(int type, String label, TextWheelPicker picker) {
        this.type = type;
        this.label = label;
        this.wheelPicker = picker;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public TextWheelPicker getWheelPicker() {
        return wheelPicker;
    }
}
