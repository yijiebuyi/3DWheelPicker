package com.wheelpicker.widget;

import java.util.List;

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
