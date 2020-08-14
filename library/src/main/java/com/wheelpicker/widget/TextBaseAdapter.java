package com.wheelpicker.widget;

import com.wheelpicker.core.WheelPickerAdapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class TextBaseAdapter implements WheelPickerAdapter {
	private final DataSetObservable mDataSetObservable = new DataSetObservable();
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		 mDataSetObservable.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mDataSetObservable.unregisterObserver(observer);
	}

	/**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
    
    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }
    
    @Override
	public View getView(int index, View convertView, ViewGroup parent) {
		return null;
	}
    
    @Override
	public Object getItem(int position) {
		return null;
	}
    
	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}
	
	/**
     * Get the text of item associated with the specified position in the data set.
     * 
     * @param position Position of the item whose data we want within the adapter's 
     * data set.
     * @return The text at the specified position.
     */
	abstract String getItemText(int position);

}
