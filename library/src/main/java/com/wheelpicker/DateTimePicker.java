package com.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wheelpicker.DateTimeItem.TYPE_DAY;
import static com.wheelpicker.DateTimeItem.TYPE_HOUR;
import static com.wheelpicker.DateTimeItem.TYPE_MINUTE;
import static com.wheelpicker.DateTimeItem.TYPE_MONTH;
import static com.wheelpicker.DateTimeItem.TYPE_SECOND;
import static com.wheelpicker.DateTimeItem.TYPE_YEAR;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2021/11/13
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class DateTimePicker extends AbsDatePicker {
    /**
     * 最大最小年月日时分秒
     */
    private int mFromYear;
    private int mFromMonth;
    private int mFromDay;
    private int mFromHour;
    private int mFromMinute;
    private int mFromSecond;

    private int mToYear;
    private int mToMonth;
    private int mToDay;
    private int mToHour;
    private int mToMinute;
    private int mToSecond;

    public DateTimePicker(Context context) {
        super(context);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimePicker(Context context, int mode) {
        super(context, mode);
    }

    public DateTimePicker(Context context, long from, long to, int mode) {
        super(context, from, to, mode);
    }

    public DateTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 填充数据
     *
     * @param data
     */
    @Override
    protected void fillData(HashMap<Integer, List<String>> data) {
        if (mFrom == mTo || mFrom > mTo) {
            throw new IllegalArgumentException("please set legal period of time");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mFrom);
        mFromYear = calendar.get(Calendar.YEAR);
        mFromMonth = calendar.get(Calendar.MONTH);
        mFromDay = calendar.get(Calendar.DAY_OF_MONTH);
        mFromHour = calendar.get(Calendar.HOUR_OF_DAY);
        mFromMinute = calendar.get(Calendar.MINUTE);
        mFromSecond = calendar.get(Calendar.SECOND);

        calendar.setTimeInMillis(mTo);
        mToYear = calendar.get(Calendar.YEAR);
        mToMonth = calendar.get(Calendar.MONTH);
        mToDay = calendar.get(Calendar.DAY_OF_MONTH);
        mToHour = calendar.get(Calendar.HOUR_OF_DAY);
        mToMinute = calendar.get(Calendar.MINUTE);
        mToSecond = calendar.get(Calendar.SECOND);

        Iterator<Map.Entry<Integer, List<String>>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<String>> entry = it.next();
            int type = entry.getKey();
            switch (type) {
                case TYPE_YEAR:
                    updateYears(mFromYear, mToYear);
                    break;
               /* case TYPE_MONTH:
                    updateMonth(fromCalendar.get(Calendar.MONTH), toCalendar.get(Calendar.MONTH));
                    break;
                case TYPE_DAY:
                    updateDay(fromCalendar.get(Calendar.DAY_OF_MONTH), toCalendar.get(Calendar.DAY_OF_MONTH));
                    break;
                case TYPE_HOUR:
                    updateHour(fromCalendar.get(Calendar.HOUR), toCalendar.get(Calendar.HOUR));
                    break;
                case TYPE_MINUTE:
                    updateMinute(fromCalendar.get(Calendar.MINUTE), toCalendar.get(Calendar.MINUTE));
                    break;
                case TYPE_SECOND:
                    updateSecond(fromCalendar.get(Calendar.SECOND), toCalendar.get(Calendar.SECOND));
                    break;*/
            }
        }
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data, boolean touch) {
        int type = wheelPicker.getId();
        switch (type) {
            case TYPE_YEAR:
                int year = getCurrentDate(data, mYearStr);
                mSelectedYear = year > 0 ? year : mCurrYear;
                notifyMonthChange(mSelectedMonth);
                break;
            case TYPE_MONTH:
                int month = getCurrentMonth(data, mMonthStr);
                mSelectedMonth = month >= 0 ? month : 0;
                notifyDayChange(mSelectedDay);
                break;
            case TYPE_DAY:
                int day = getCurrentDate(data, mDayStr);
                mSelectedDay = day > 0 ? day : mCurrDay;
                notifyHourChange(mSelectedHour);
                break;
            case TYPE_HOUR:
                int hour = getCurrentDate(data, mHourStr);
                mSelectedHour = hour >= 0 ? hour : mCurrHour;
                notifyMinuteChange(mSelectedMinute);
                break;
            case TYPE_MINUTE:
                int minute = getCurrentDate(data, mMinuteStr);
                mSelectedMinute = minute >= 0 ? minute : mCurrMinute;
                notifySecondChange();
                break;
            case TYPE_SECOND:
                int second = getCurrentDate(data, mSecondStr);
                mSelectedSecond = second >=0 ? second : mCurrSecond;
                break;
        }
    }

    /**
     * 通知更新月份数据
     */
    private void notifyMonthChange(int selectedMonth) {
        boolean changed = false;
        List<String> months = mData.get(TYPE_MONTH);
        if (mSelectedYear == mFromYear) {
            updateMonth(mFromMonth, 11);
        } else if (mSelectedYear == mToYear) {
            updateMonth(0, mToMonth);
        } else {
            changed = months.size() != 12;
            if (changed) {
                updateMonth(0, 11);
            }
        }

        //update month index
        int monthIndex = Math.max(0, months.indexOf((selectedMonth + 1) + mMonthStr));
        TextWheelPicker picker = getPicker(TYPE_MONTH);
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(monthIndex);
        //update month
        adapter.setData(months);
    }

    private void notifyDayChange(int selectedDay) {
        TextWheelPicker picker = getPicker(TYPE_DAY);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> days = mData.get(TYPE_DAY);
        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth) {
            correctDays(mSelectedMonth + 1, mFromDay);
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth) {
            updateDay(1, mToDay);
        } else {
            correctMaxDays();
        }

        //update day index
        int dayIndex = Math.max(0, days.indexOf(selectedDay + mDayStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(dayIndex);
        //update day
        adapter.setData(days);
    }

    private void notifyHourChange(int selectedHour) {
        TextWheelPicker picker = getPicker(TYPE_HOUR);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> hours = mData.get(TYPE_HOUR);
        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth && mSelectedDay == mFromDay) {
            updateHour(mFromHour, 23);
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth && mSelectedDay == mToDay) {
            updateHour(0, mToHour);
        } else {
            updateHour(0, 23);
        }

        //update hour index
        int hourIndex = Math.max(0, hours.indexOf(selectedHour + mHourStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(hourIndex);
        //update hours
        adapter.setData(hours);
    }

    private void notifyMinuteChange(int selectedMinute) {
        TextWheelPicker picker = getPicker(TYPE_MINUTE);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }
        List<String> minutes = mData.get(TYPE_MINUTE);

        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth &&
                mSelectedDay == mFromDay && mSelectedHour == mFromHour) {
            updateMinute(mFromMinute, 59);
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth &&
                mSelectedDay == mToDay && mSelectedHour == mToHour) {
            updateMinute(0, mToMinute);
        } else {
            updateMinute(0, 59);
        }

        //update minute index
        int minuteIndex = Math.max(0, minutes.indexOf(selectedMinute + mMinuteStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(minuteIndex);
        //update minute
        adapter.setData(minutes);
    }

    private void notifySecondChange() {
        TextWheelPicker picker = getPicker(TYPE_SECOND);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> seconds = mData.get(TYPE_SECOND);

        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth &&
                mSelectedDay == mFromDay && mSelectedHour == mFromHour &&
                mSelectedMinute == mFromMinute) {
            updateSecond(mFromSecond, 59);
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth &&
                mSelectedDay == mToDay && mSelectedHour == mToHour &&
                mSelectedMinute == mToMinute) {
            updateSecond(0, mToSecond);
        } else {
            updateSecond(0, 59);
        }

        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(0);
        //update minute
        adapter.setData(seconds);
    }



}
