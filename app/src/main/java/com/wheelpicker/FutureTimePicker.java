package com.wheelpicker;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*  =======================================================================================
 *  Copyright (C) 2016 Xiaojs.cn. All rights reserved.
 *
 *  This computer program source code file is protected by copyright law and international
 *  treaties. Unauthorized distribution of source code files, programs, or portion of the
 *  package, may result in severe civil and criminal penalties, and will be prosecuted to
 *  the maximum extent under the law.
 *
 *  ---------------------------------------------------------------------------------------
 * Author:
 * Date:2016/11/7
 * Desc:
 *
 * ======================================================================================== */

public class FutureTimePicker extends LinearLayout implements OnWheelPickedListener {
    public final static int TYPE_DAY = 1 << 1;
    public final static int TYPE_HOUR = 1 << 2;
    public final static int TYPE_MINUTE = 1 << 3;
    private final static long ONE_DAY = 24 * 60 * 60 * 1000;
    private int DURATION = 365;

    private TextWheelPicker mDayWheelPicker;
    private TextWheelPicker mHourWheelPicker;
    private TextWheelPicker mMinuteWheelPicker;

    private int mCurrYear;
    private int mCurrMonth;
    private int mCurrDay;
    private int mCurrHour;
    private int mCurrMinute;

    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDay;
    private int mSelectedHour;
    private int mSelectedMinute;
    private int mSelectedSecond;

    private String mTodayStr = "今天";
    private String mTomorrowStr = "明天";
    //private String mAfterTomorrowStr = "后天";
    private String mNextYear = "明年";
    private String mMonthStr;
    private String mDayStr;
    private String mHourStr;
    private String mMinuteStr;
    private long mStartDay;

    private Map<String, Long> mDayMap;
    private List<String> mDays;
    private List<String> mHours;
    private List<String> mMinutes;

    private TextWheelPickerAdapter mDayPickerAdapter;
    private TextWheelPickerAdapter mHourPickerAdapter;
    private TextWheelPickerAdapter mMinutePickerAdapter;

    private OnFutureDatePickListener mOnFutureDatePickListener;

    public FutureTimePicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        mTodayStr = getResources().getString(com.wheelpicker.R.string._today);
        mTomorrowStr = getResources().getString(com.wheelpicker.R.string._tomorrow);
        //mAfterTomorrowStr = getResources().getString(com.wheelpicker.R.string._after_tomorrow);
        mNextYear = getResources().getString(com.wheelpicker.R.string._next_year);

        mMonthStr = getResources().getString(com.wheelpicker.R.string._month);
        mDayStr = getResources().getString(com.wheelpicker.R.string._day);
        mHourStr = getResources().getString(com.wheelpicker.R.string._hour);
        mMinuteStr = getResources().getString(com.wheelpicker.R.string._minute);

        LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llParams.weight = 1;

        mDayWheelPicker = new TextWheelPicker(getContext(), TYPE_DAY);
        mHourWheelPicker = new TextWheelPicker(getContext(), TYPE_HOUR);
        mMinuteWheelPicker = new TextWheelPicker(getContext(), TYPE_MINUTE);

        mDayWheelPicker.setOnWheelPickedListener(this);
        mHourWheelPicker.setOnWheelPickedListener(this);
        mMinuteWheelPicker.setOnWheelPickedListener(this);

        addView(mDayWheelPicker, llParams);
        addView(mHourWheelPicker, llParams);
        addView(mMinuteWheelPicker, llParams);

        initData();
    }

    private void initData() {
        initDate();

        mDayPickerAdapter = new TextWheelPickerAdapter();
        mHourPickerAdapter = new TextWheelPickerAdapter();
        mMinutePickerAdapter = new TextWheelPickerAdapter();

        mDayMap = new HashMap<String, Long>();
        mDays = new ArrayList<String>();
        mHours = new ArrayList<String>();
        mMinutes = new ArrayList<String>();

        //default one year
        updateDate(DURATION);
        updateMinHour(mCurrHour);
        updateMinMinute(mCurrMinute);

        mDayPickerAdapter.setData(mDays);
        mHourPickerAdapter.setData(mHours);
        mMinutePickerAdapter.setData(mMinutes);

        mDayWheelPicker.setAdapter(mDayPickerAdapter);
        mHourWheelPicker.setAdapter(mHourPickerAdapter);
        mMinuteWheelPicker.setAdapter(mMinutePickerAdapter);

    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH) + 1;
        mCurrDay = calendar.get(Calendar.DATE);
        mCurrHour = calendar.get(Calendar.HOUR_OF_DAY);
        mCurrMinute = calendar.get(Calendar.MINUTE);

        mSelectedYear = mCurrYear;
        mSelectedMonth = mCurrMonth;
        mSelectedDay = mCurrDay;
        mSelectedHour = mCurrHour;
        mSelectedMinute = mCurrMinute;
    }

    private void updateDate(int durationDays) {
        long today = System.currentTimeMillis();
        long oneDay = ONE_DAY;

        mDays.clear();
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.setTime(new Date(0));
        calendar.set(year, month, day, 0, 0, 0);
        mStartDay = calendar.getTimeInMillis();

        for (int i = 0; i < durationDays; i++) {
            String str = "";
            switch (i) {
                case 0:
                    //today
                    str = mTodayStr;
                    mDayMap.put(mTodayStr, today);
                    break;
                case 1:
                    //tomorrow
                    str = mTomorrowStr;
                    mDayMap.put(mTomorrowStr, today + oneDay);
                    break;
                //case 2:
                    //after tomorrow
                    //str = mAfterTomorrowStr;
                    //mDayMap.put(mAfterTomorrowStr, today + 2 * oneDay);
                    //break;
                default:
                    //xx year xx month xx day
                    calendar.setTimeInMillis(today + i * oneDay);
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH) + 1;
                    day = calendar.get(Calendar.DATE);

                    String m = String.valueOf(month);
                    if (month < 10) {
                        m = "0" + m;
                    }

                    String d = String.valueOf(day);
                    if (day < 10) {
                        d = "0" + d;
                    }

                    if (year == mCurrYear) {
                        str = m + mMonthStr + d + mDayStr;
                    } else {
                        str = mNextYear + m + mMonthStr + d + mDayStr;
                    }
                    mDayMap.put(str, today + i * oneDay);
                    break;
            }
            mDays.add(str);
        }
    }

    public void setFutureDuration(int days) {
        if (days > 0) {
            updateDate(days);
            DURATION = days;
        }
    }

    public void setPickedTime(long currentTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(currentTime);

        int y = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DATE);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        int s = c.get(Calendar.SECOND);

        mSelectedYear = y;
        mSelectedMonth = month;
        mSelectedDay = d;
        mSelectedHour = h;
        mSelectedMinute = m;
        mSelectedSecond = s;

        c.set(y, month - 1, d, 0, 0, 0);
        long pickedDay = c.getTimeInMillis();
        int dayIndex = Math.max(0, (int)((pickedDay - mStartDay) / ONE_DAY));
        dayIndex = Math.min(dayIndex, DURATION);

        if (dayIndex > 0) {
            updateMinHour(0);
            mHourPickerAdapter.setData(mHours);
        }
        int hIndex = Math.max(0, mHours.indexOf(h + mHourStr));

        if (hIndex > 0) {
            updateMinMinute(0);
            mMinutePickerAdapter.setData(mMinutes);
        }
        int mIndex = Math.max(0, mMinutes.indexOf(m + mMinuteStr));

        mDayWheelPicker.setCurrentItem(dayIndex);
        mHourWheelPicker.setCurrentItem(hIndex);
        mMinuteWheelPicker.setCurrentItem(mIndex);

        mSelectedHour = getCurrentDate(mHourPickerAdapter.getItem(hIndex), mHourStr);
        mSelectedMinute = getCurrentDate(mMinutePickerAdapter.getItemText(mIndex), mMinuteStr);

        if (mOnFutureDatePickListener != null) {
            mOnFutureDatePickListener.onDatePicked(mSelectedYear, mSelectedMonth - 1, mSelectedDay,
                    mSelectedHour, mSelectedMinute, 0);
        }
    }

    public void setOnFutureDatePickListener(OnFutureDatePickListener listener) {
        mOnFutureDatePickListener = listener;
        if (mOnFutureDatePickListener != null) {
            mOnFutureDatePickListener.onDatePicked(mSelectedYear, mSelectedMonth - 1, mSelectedDay,
                    mSelectedHour, mSelectedMinute, mSelectedSecond);
        }
    }

    private void updateMinHour(int minHour) {
        mHours.clear();
        for (int i = minHour; i < 24; i++) {
            mHours.add(i + mHourStr);
        }
    }

    private void updateMinMinute(int minMinute) {
        mMinutes.clear();
        for (int i = minMinute; i < 60; i++) {
            mMinutes.add(i + mMinuteStr);
        }
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }

        mDayWheelPicker.setTextSize(textSize);
        mHourWheelPicker.setTextSize(textSize);
        mMinuteWheelPicker.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        mDayWheelPicker.setTextColor(textColor);
        mHourWheelPicker.setTextColor(textColor);
        mMinuteWheelPicker.setTextColor(textColor);
    }

    public void setLineColor(int lineColor) {
        mDayWheelPicker.setLineColor(lineColor);
        mHourWheelPicker.setLineColor(lineColor);
        mMinuteWheelPicker.setLineColor(lineColor);
    }

    public void setLineWidth(int width) {
        mDayWheelPicker.setLineStorkeWidth(width);
        mHourWheelPicker.setLineColor(width);
        mMinuteWheelPicker.setLineColor(width);
    }

    public void setItemSpace(int space) {
        mDayWheelPicker.setItemSpace(space);
        mHourWheelPicker.setItemSpace(space);
        mMinuteWheelPicker.setItemSpace(space);
    }

    public void setVisibleItemCount(int itemCount) {
        mDayWheelPicker.setVisibleItemCount(itemCount);
        mHourWheelPicker.setVisibleItemCount(itemCount);
        mMinuteWheelPicker.setVisibleItemCount(itemCount);
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        mDayWheelPicker.setItemSize(itemWidth, itemHeight);
        mHourWheelPicker.setItemSize(itemWidth, itemHeight);
        mMinuteWheelPicker.setItemSize(itemWidth, itemHeight);
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data) {
        Calendar calendar = Calendar.getInstance();
        int hourIndex = 0;
        int minuteIndex = 0;
        switch (wheelPicker.getId()) {
            case TYPE_DAY:
                long day = mDayMap.get(data.toString());
                calendar.setTimeInMillis(day);
                mSelectedYear = calendar.get(Calendar.YEAR);
                mSelectedMonth = calendar.get(Calendar.MONTH) + 1;
                mSelectedDay = calendar.get(Calendar.DATE);

                if (mSelectedDay == mCurrDay) {
                    hourIndex = 0;
                    minuteIndex = 0;
                    updateMinHour(mCurrHour);
                    updateMinMinute(mCurrMinute);
                } else {
                    hourIndex = mHourWheelPicker.getCurrentItem();
                    minuteIndex = mMinuteWheelPicker.getCurrentItem();
                    updateMinHour(0);
                    updateMinMinute(0);
                }

                mSelectedHour = getCurrentDate(mHourPickerAdapter.getItem(hourIndex), mHourStr);
                mSelectedMinute = getCurrentDate(mMinutePickerAdapter.getItemText(minuteIndex), mMinuteStr);

                mHourWheelPicker.setCurrentItem(hourIndex);
                mMinuteWheelPicker.setCurrentItem(minuteIndex);
                mHourPickerAdapter.setData(mHours);
                mMinutePickerAdapter.setData(mMinutes);

                break;
            case TYPE_HOUR:
                mSelectedHour = getCurrentDate(data, mHourStr);
                if (mSelectedHour == mCurrHour) {
                    minuteIndex = 0;
                    updateMinMinute(mCurrMinute);
                } else {
                    minuteIndex = mMinuteWheelPicker.getCurrentItem();
                    updateMinMinute(0);
                }

                mSelectedMinute = getCurrentDate(mMinutePickerAdapter.getItemText(minuteIndex), mMinuteStr);
                mMinuteWheelPicker.setCurrentItem(minuteIndex);
                mMinutePickerAdapter.setData(mMinutes);
                break;
            case TYPE_MINUTE:
                mSelectedMinute = getCurrentDate(data, mMinuteStr);
                break;
        }

        if (mOnFutureDatePickListener != null) {
            mOnFutureDatePickListener.onDatePicked(mSelectedYear, mSelectedMonth - 1, mSelectedDay,
                    mSelectedHour, mSelectedMinute, 0);
        }
    }

    private int getCurrentDate(Object data, String suffix) {
        if (data instanceof String) {
            int suffixLeg = suffix == null ? 0 : suffix.length();
            String temp = (String) data;
            return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));
        }

        return -1;
    }


    public interface OnFutureDatePickListener {
        public void onDatePicked(int year, int month, int day, int hour, int minute, int second);
    }


}
