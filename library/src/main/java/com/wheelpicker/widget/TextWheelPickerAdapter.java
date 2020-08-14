package com.wheelpicker.widget;

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
public class TextWheelPickerAdapter extends TextBaseAdapter {
	private List<String> mData;
	
	public TextWheelPickerAdapter() {
		//empty constructor
	}
	
	public TextWheelPickerAdapter(List<String> data) {
		mData = data;
	}
	
	public void setData(List<String> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public String getItemText(int position) {
		return  mData == null ? null : mData.get(position);
	}

	@Override
	public Object getItem(int position) {
		return getItemText(position);
	}
}
