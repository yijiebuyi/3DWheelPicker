package com.wheelpicker.widget;

import android.content.Context;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：The simple Array wheel adapter
 *  @param <T>
 *            the element type
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class ArrayWheelAdapter<T> extends TextBaseAdapter {

	// items
	private T items[];

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the current context
	 * @param items
	 *            the items
	 */
	public ArrayWheelAdapter(Context context, T items[]) {
		this.items = items;
	}

	@Override
	public String getItemText(int index) {
		if (index >= 0 && index < items.length) {
			T item = items[index];
			if (item instanceof String) {
				return (String) item;
			}
			if (item != null) {
				return item.toString();
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		if (items == null) {
			return 0;
		}
		return items.length;
	}

}
