package com.wheelpicker;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：未来时间
 * 显示（天、小时、分钟）的组合
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class FutureTimePicker extends LinearLayout implements
        OnWheelPickedListener<String>, IPickerView, IDateTimePicker {
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

    //Is selected today
    private boolean mOldSelectionIsToday = false;

    private String mTodayStr = "今天";
    private String mTomorrowStr = "明天";
    //private String mAfterTomorrowStr = "后天";
    private String mNextYear = "明年";
    private String mYearStr = "年";
    private String mMonthStr = "月";
    private String mDayStr = "日";
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

        mYearStr = getResources().getString(com.wheelpicker.R.string._year);
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
        mCurrMonth = calendar.get(Calendar.MONTH);
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

        StringBuilder sb = new StringBuilder();
        String str = "";
        for (int i = 0; i < durationDays; i++) {
            sb.delete(0, sb.length());
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

                    if (year == mCurrYear) {
                        //do nothing, 当年只含月份
                    } else if (year == mCurrYear + 1) {
                        sb.append(mNextYear);
                    } else {
                        sb.append(year);
                        sb.append(mYearStr);
                    }

                    //month
                    if (month < 10) {
                        sb.append("0");
                    }
                    sb.append(month);
                    sb.append(mMonthStr);

                    //day
                    if (day < 10) {
                        sb.append("0");
                    }
                    sb.append(day);
                    sb.append(mDayStr);

                    str = sb.toString();
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

    public void setPickedTime(long pickedTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pickedTime);

        int y = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
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
        //Is today
        mOldSelectionIsToday = mCurrYear == mSelectedYear && mCurrMonth == mSelectedMonth && mCurrDay == mSelectedDay;

        c.set(y, month, d, 0, 0, 0);
        long pickedDay = c.getTimeInMillis();
        int dayIndex = Math.max(0, (int) ((pickedDay - mStartDay) / ONE_DAY));
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

        mSelectedHour = getCurrentDate(mHourPickerAdapter.getItemText(hIndex), mHourStr);
        mSelectedMinute = getCurrentDate(mMinutePickerAdapter.getItemText(mIndex), mMinuteStr);
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
        mHourWheelPicker.setLineStorkeWidth(width);
        mMinuteWheelPicker.setLineStorkeWidth(width);
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
    public void setShadow(int gravity, float factor) {
        mDayWheelPicker.setShadowGravity(gravity);
        mHourWheelPicker.setShadowGravity(gravity);
        mMinuteWheelPicker.setShadowGravity(gravity);

        mDayWheelPicker.setShadowFactor(factor);
        mHourWheelPicker.setShadowFactor(factor);
        mMinuteWheelPicker.setShadowFactor(factor);
    }

    @Override
    public void setScrollAnimFactor(float factor) {
        mDayWheelPicker.setFlingAnimFactor(factor);
        mHourWheelPicker.setFlingAnimFactor(factor);
        mMinuteWheelPicker.setFlingAnimFactor(factor);
    }

    @Override
    public void setScrollMoveFactor(float factor) {
        mDayWheelPicker.setFingerMoveFactor(factor);
        mHourWheelPicker.setFingerMoveFactor(factor);
        mMinuteWheelPicker.setFingerMoveFactor(factor);
    }

    @Override
    public void setScrollOverOffset(int offset) {
        mDayWheelPicker.setOverOffset(offset);
        mHourWheelPicker.setOverOffset(offset);
        mMinuteWheelPicker.setOverOffset(offset);
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data, boolean touch) {
        Calendar calendar = Calendar.getInstance();
        int hourIndex = 0;
        int minuteIndex = 0;
        switch (wheelPicker.getId()) {
            case TYPE_DAY:
                long day = mDayMap.get(data.toString());
                calendar.setTimeInMillis(day);
                mSelectedYear = calendar.get(Calendar.YEAR);
                mSelectedMonth = calendar.get(Calendar.MONTH);
                mSelectedDay = calendar.get(Calendar.DATE);

                if (mSelectedYear == mCurrYear && mSelectedMonth == mCurrMonth && mSelectedDay == mCurrDay) {
                    hourIndex = 0;
                    minuteIndex = 0;
                    updateMinHour(mCurrHour);
                    updateMinMinute(mCurrMinute);
                    mOldSelectionIsToday = true;
                } else {
                    //hourIndex = mOldSelectionIsToday ? 0 : mHourWheelPicker.getCurrentItem();
                    //minuteIndex = mOldSelectionIsToday ? 0 : mMinuteWheelPicker.getCurrentItem();
                    String hour = mHours.get(mHourWheelPicker.getCurrentItem());
                    String minute = mMinutes.get(mMinuteWheelPicker.getCurrentItem());
                    updateMinHour(0);
                    updateMinMinute(0);
                    hourIndex = getHourIndex(hour);
                    minuteIndex = getMinuteIndex(minute);
                    mOldSelectionIsToday = false;
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
                if (mSelectedYear == mCurrYear && mSelectedMonth == mCurrMonth &&
                        mSelectedDay == mCurrDay && mSelectedHour == mCurrHour) {
                    minuteIndex = 0;
                    updateMinMinute(mCurrMinute);
                } else {
                    //minuteIndex = mMinuteWheelPicker.getCurrentItem();
                    String minute = mMinutes.get(mMinuteWheelPicker.getCurrentItem());
                    updateMinMinute(0);
                    minuteIndex = getMinuteIndex(minute);
                }

                mSelectedMinute = getCurrentDate(mMinutePickerAdapter.getItemText(minuteIndex), mMinuteStr);
                mMinuteWheelPicker.setCurrentItem(minuteIndex);
                mMinutePickerAdapter.setData(mMinutes);
                break;
            case TYPE_MINUTE:
                mSelectedMinute = getCurrentDate(data, mMinuteStr);
                break;
        }
    }

    private int getHourIndex(String hour){
        if (hour == null) {
            return 0;
        }

        return mHours.indexOf(hour);
    }

    private int getMinuteIndex(String minute){
        if (minute == null) {
            return 0;
        }

        return mMinutes.indexOf(minute);
    }

    public void notifyDataSetChanged() {
        //年月日，时分秒是联动的，所以只需要通知day的数据变化
        mDayPickerAdapter.notifyDataSetChanged();
    }

    private int getCurrentDate(String data, String suffix) {
        int suffixLeg = suffix == null ? 0 : suffix.length();
        String temp = data;
        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));
    }

    /**
     * 设置默认选择日期：年月日
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    @Override
    public void setDefaultSelectedDate(int year, int month, int day) {

    }

    /**
     * 设置默认选中的时间：时分秒
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     */
    @Override
    public void setDefaultSelectedTime(int hour, int minute, int second) {

    }

    /**
     * 设置那些picker 显示或隐藏
     * 1.若当前设置visibility 设置为VISIBLE,
     * wheelType匹配的picker显示，没匹配的就隐藏
     * <p>
     * 2.若当前设置visibility 设置为GONE
     * wheelType匹配的picker隐藏，没匹配的就显示
     *
     * @param wheelType  哪些picker
     * @param visibility 显示或隐藏, 不能取值为INVISIBLE
     */
    @Override
    public void setWheelPickerVisibility(int wheelType, int visibility) {

    }

    @Override
    public long getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mSelectedYear, mSelectedMonth, mSelectedDay, mSelectedHour, mSelectedMinute, mSelectedSecond);
        return calendar.getTimeInMillis();
    }

    public int getSelectedYear() {
        return mSelectedYear;
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public int getSelectedDay() {
        return mSelectedDay;
    }

    public int getSelectedHour() {
        return mSelectedHour;
    }

    public int getSelectedMinute() {
        return mSelectedMinute;
    }

    public int getSelectedSecond() {
        return mSelectedSecond;
    }
}
