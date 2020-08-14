package com.wheelpicker.core;

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
public interface OnWheelPickedListener {
	
	@SuppressWarnings("rawtypes")
	public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data);
}
