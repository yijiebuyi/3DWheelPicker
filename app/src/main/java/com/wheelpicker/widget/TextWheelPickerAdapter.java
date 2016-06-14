package com.wheelpicker.widget;

import java.util.List;

public class TextWheelPickerAdapter extends TextBaseAdapter {
	private List<String> mDatas;
	
	public TextWheelPickerAdapter() {
		//empty constructor
	}
	
	public TextWheelPickerAdapter(List<String> datas) {
		mDatas = datas;
	}
	
	public void setDatas(List<String> datas) {
		mDatas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	String getItemText(int position) {
		return  mDatas == null ? null : mDatas.get(position);
	}
}
