package com.wheelpicker.core;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：WheelPicker adapter interface
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public interface WheelPickerAdapter {
	  /**
     * Register an observer that is called when changes happen to the data used by this adapter.
     *
     * @param observer the object that gets notified when the data set changes.
     */
    void registerDataSetObserver(DataSetObserver observer);

    /**
     * Unregister an observer that has previously been registered with this
     * adapter via {@link #registerDataSetObserver}.
     *
     * @param observer the object to unregister.
     */
    void unregisterDataSetObserver(DataSetObserver observer);
    
	/**
     * How many items are in the data set represented by this Adapter.
     * 
     * @return Count of items.
     */
    int getCount();   
	
	/**
	 * Get a View that displays the data at the specified position in the data set
	 * 
	 * @param index the item index
	 * @param convertView the old view to reuse if possible
	 * @param parent the parent that this view will eventually be attached to
	 * @return the wheel item View
	 */
	View getView(int index, View convertView, ViewGroup parent);
	
	 /**
     * Get the data item associated with the specified position in the data set.
     * 
     * @param position Position of the item whose data we want within the adapter's 
     * data set.
     * @return The data at the specified position.
     */
	Object getItem(int position);
	
	/**
     * @return true if this adapter doesn't contain any data. 
     */
	boolean isEmpty();
}
