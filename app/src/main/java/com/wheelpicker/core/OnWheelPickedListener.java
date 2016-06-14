package com.wheelpicker.core;

/**
 * wheel listener
 */
public interface OnWheelPickedListener {
	
	@SuppressWarnings("rawtypes")
	public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data);
}
