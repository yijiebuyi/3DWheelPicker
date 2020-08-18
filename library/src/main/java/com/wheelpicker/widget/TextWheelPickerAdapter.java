package com.wheelpicker.widget;

import com.wheelpicker.PickDataWrapper;

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
		if (d instanceof String) {
			return (String)d;
		} else if (d instanceof PickString) {
			return ((PickString)d).pickDisplayName();
		} else {
			return d.toString();
		}
	}

	@Override
	public String getItem(int position) {
		return getItemText(position);
	}
}
