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
        mFromHour = calendar.get(Calendar.HOUR);
        mFromMinute = calendar.get(Calendar.MINUTE);
        mFromSecond = calendar.get(Calendar.SECOND);

        calendar.setTimeInMillis(mTo);
        mToYear = calendar.get(Calendar.YEAR);
        mToMonth = calendar.get(Calendar.MONTH);
        mToDay = calendar.get(Calendar.DAY_OF_MONTH);
        mToHour = calendar.get(Calendar.HOUR);
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
                if (year > 0) {
                    mSelectedYear = year;
                }

                notifyMonthChange();
                break;
            case TYPE_MONTH:
                break;
        }
    }

    /**
     * 通知更新月份数据
     */
    private void notifyMonthChange() {
        boolean changed = false;
        List<String> months = mData.get(TYPE_MONTH);

        switch (mMode) {
            case MODE_PENDING:
                if (mSelectedYear == mCurrYear) {
                    //current year
                    updateMonth(mCurrMonth, 11);
                } else {
                    changed = months.size() != 12;
                    if (changed) {
                        updateMonth(0, 11);
                    }
                }
                break;
            case MODE_BIRTHDAY:
                if (mSelectedYear == mCurrYear) {
                    updateMonth(0, mCurrMonth);
                } else {
                    changed = months.size() != 12;
                    if (changed) {
                        updateMonth(0, 11);
                    }
                }
                break;
            case MODE_PERIOD:
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
                break;
            default:
                changed = months.size() != 12;
                if (changed) {
                    updateMonth(0, 11);
                }
                break;
        }

        //update month index
        int monthIndex = Math.max(0, months.indexOf((mSelectedMonth + 1) + mMonthStr));
        TextWheelPicker picker = getPicker(TYPE_MONTH);
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter)picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(monthIndex);
        //update month
        adapter.setData(months);
    }

    private TextWheelPicker getPicker(int type) {
        for (DateTimeItem item : mDateTimeItems) {
            if (item.getType() == type) {
                return item.getWheelPicker();
            }
        }

        return mDateTimeItems.get(0).getWheelPicker();
    }
}
