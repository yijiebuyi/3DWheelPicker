package com.wheelpicker.widget;

import com.wheelpicker.core.WheelPickerUtil;

import java.util.List;

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
public class TextWheelPickerAdapter<T> extends TextBaseAdapter {
	private List<T> mData;
	
	public TextWheelPickerAdapter() {
		//empty constructor
	}
	
	public TextWheelPickerAdapter(List<T> data) {
		mData = data;
	}

	public List<T> getData() {
		return mData;
	}
	
	public void setData(List<T> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public String getItemText(int position) {
		T d = mData == null ? null : mData.get(position);
		return WheelPickerUtil.formString(d);
	}

	@Override
	public String getItem(int position) {
		return getItemText(position);
	}
}
