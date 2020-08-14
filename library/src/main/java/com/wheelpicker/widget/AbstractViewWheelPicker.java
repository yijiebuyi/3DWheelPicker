package com.wheelpicker.widget;

import com.wheelpicker.core.ScrollWheelPicker;

import android.content.Context;
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
public abstract class AbstractViewWheelPicker extends ScrollWheelPicker<ViewBaseAdapter> {

	public AbstractViewWheelPicker(Context context) {
		super(context);
	}

	public AbstractViewWheelPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AbstractViewWheelPicker(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

}
