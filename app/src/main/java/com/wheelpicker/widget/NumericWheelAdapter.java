package com.wheelpicker.widget;

import android.content.Context;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter extends TextBaseAdapter {
    
    /** The default min value */
    public static final int DEFAULT_MAX_VALUE = 9;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;
    
    // Values
    private int mMinValue;
    private int mMaxValue;
    
    // format
    private String mFormat;
    
    /**
     * Constructor
     * @param context the current context
     */
    public NumericWheelAdapter(Context context) {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue) {
        this(context, minValue, maxValue, null);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format the format string
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format) {
        mMinValue = minValue;
        mMaxValue = maxValue;
        mFormat = format;
    }

    @Override
    public String getItemText(int index) {
        if (index >= 0 && index < getCount()) {
            int value = mMinValue + index;
            return mFormat != null ? String.format(mFormat, value) : Integer.toString(value);
        }
        return null;
    }

	@Override
	public int getCount() {
		return mMaxValue - mMinValue + 1;
	}    
}
