package com.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

public abstract class AbsDatePicker extends LinearLayout implements
        OnWheelPickedListener<String>, IPickerView, IDateTimePicker {
    //所有
    public final static int TYPE_ALL = TYPE_YEAR | TYPE_MONTH | TYPE_DAY |
            TYPE_HOUR | TYPE_MINUTE | TYPE_SECOND;
    //年月日
    public final static int TYPE_YY_MM_DD = TYPE_YEAR | TYPE_MONTH | TYPE_DAY;
    //时分秒
    public final static int TYPE_HH_MM_SS = TYPE_HOUR | TYPE_SECOND | TYPE_MINUTE;

    /**
     * 一天时长的毫秒数
     */
    private final static long ONE_DAY_TIME_LENGTH = 24 * 60 * 60 * 1000;
    /**
     * 一年时长的毫秒数（默认按365天算，不考虑闰年）
     */
    private final static long ONE_YEAR_TIME_LENGTH = 365 * ONE_DAY_TIME_LENGTH;

    /**
     * 未来模式，时间下限为当前时间, 默认是100年后
     */
    public final static int MODE_PENDING = 0;
    /**
     * 生日模式，时间上限为当前时间，默认100年
     */
    public final static int MODE_BIRTHDAY = 1;
    /**
     * 正常模式，前后100年
     */
    public final static int MODE_NORMAL = 2;
    /**
     * 时间段，给定开始时间和结束时间
     */
    public final static int MODE_PERIOD = 3;

    protected String mYearStr;
    protected String mMonthStr;
    protected String mDayStr;
    protected String mHourStr;
    protected String mMinuteStr;
    protected String mSecondStr;

    protected int mMode = MODE_PERIOD;

    protected List<DateTimeItem> mDateTimeItems;
    protected HashMap<Integer, List<String>> mData =
            new HashMap<Integer, List<String>>(6);

    /**
     * 当前时间的年月日时分秒
     */
    protected int mCurrYear;
    protected int mCurrMonth;
    protected int mCurrDay;
    protected int mCurrHour;
    protected int mCurrMinute;
    protected int mCurrSecond;

    /**
     * 被选中的的年月日时分秒
     */
    protected int mSelectedYear;
    protected int mSelectedMonth;
    protected int mSelectedDay;
    protected int mSelectedHour;
    protected int mSelectedMinute;
    protected int mSelectedSecond;

    protected long mFrom = 0;
    protected long mTo = 0;

    //============================= constructor ==============================
    public AbsDatePicker(Context context) {
        super(context);
        init();
    }

    public AbsDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbsDatePicker(Context context, int mode) {
        super(context);
        init(mode);
    }

    public AbsDatePicker(Context context, long from, long to, int mode) {
        super(context);
        init(from, to, mode);
    }

    public AbsDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //============================= init ==============================
    private void init() {
        init(MODE_NORMAL);
    }

    private void init(int mode) {
        long curr = System.currentTimeMillis();
        long from = curr - ONE_YEAR_TIME_LENGTH * 100;
        long to = curr + ONE_YEAR_TIME_LENGTH * 100;
        init(from, to, mode);
    }

    private void init(long from, long to, int mode) {
        mMode = mode;
        mFrom = from;
        mTo = to;

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        initPickerLabelStr();
        initCurrDateTime();
        initTextWheelPicker();
        addPickers();

        initData();
    }

    /**
     * 初始化picker 标签字符串
     * 年、月、日、时、分、秒
     */
    private void initPickerLabelStr() {
        mYearStr = getResources().getString(R.string._year);
        mMonthStr = getResources().getString(R.string._month);
        mDayStr = getResources().getString(R.string._day);
        mHourStr = getResources().getString(R.string._hour);
        mMinuteStr = getResources().getString(R.string._minute);
        mSecondStr = getResources().getString(R.string._second);
    }

    /**
     * 初始化当前日期
     * 年月日时分秒
     */
    private void initCurrDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);
        mCurrHour = calendar.get(Calendar.HOUR_OF_DAY);
        mCurrMinute = calendar.get(Calendar.MINUTE);
        mCurrSecond = calendar.get(Calendar.SECOND);
    }

    /**
     * 初始化wheelPicker
     */
    private void initTextWheelPicker() {
        if (mDateTimeItems == null) {
            mDateTimeItems = new ArrayList<DateTimeItem>(6);
        }

        Context context = getContext();
        DateTimeItem yearPicker = new DateTimeItem(TYPE_YEAR, mYearStr,
                new TextWheelPicker(context, TYPE_YEAR));
        DateTimeItem monthPicker = new DateTimeItem(TYPE_MONTH, mMonthStr,
                new TextWheelPicker(context, TYPE_MONTH));
        DateTimeItem dayPicker = new DateTimeItem(TYPE_DAY, mDayStr,
                new TextWheelPicker(context, TYPE_DAY));

        DateTimeItem hourPicker = new DateTimeItem(TYPE_HOUR, mHourStr,
                new TextWheelPicker(context, TYPE_HOUR));
        DateTimeItem minutePicker = new DateTimeItem(TYPE_MINUTE, mMonthStr,
                new TextWheelPicker(context, TYPE_MINUTE));
        DateTimeItem secondPicker = new DateTimeItem(TYPE_SECOND, mSecondStr,
                new TextWheelPicker(context, TYPE_SECOND));

        mDateTimeItems.add(yearPicker);
        mDateTimeItems.add(monthPicker);
        mDateTimeItems.add(dayPicker);
        mDateTimeItems.add(hourPicker);
        mDateTimeItems.add(minutePicker);
        mDateTimeItems.add(secondPicker);
    }

    /**
     * 将pickers添加到父布局中
     */
    private void addPickers() {
        LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llParams.weight = 1;
        for (DateTimeItem itemPicker : mDateTimeItems) {
            TextWheelPicker picker = itemPicker.getWheelPicker();
            picker.setOnWheelPickedListener(this);
            addView(picker, llParams);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (DateTimeItem item : mDateTimeItems) {
            //给具体的wheelPicker 设置adapter
            TextWheelPicker picker = item.getWheelPicker();
            picker.setAdapter(new TextWheelPickerAdapter(new ArrayList<String>()));
            List<String> data = ((TextWheelPickerAdapter) picker.getAdapter()).getData();
            mData.put(item.getType(), data);
        }

        fillData(mData);
        notifyDataSetChanged();
    }

    public void setWheelPickerVisibility(int wheelType, int visibility) {
        int antiVisibility = VISIBLE;
        if (visibility == VISIBLE) {
            antiVisibility = GONE;
        } else if (visibility == GONE) {
            antiVisibility = VISIBLE;
        }

        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int visible = (wheelType & item.getType()) != 0 ? visibility : antiVisibility;
            picker.setVisibility(visible);
        }
    }

    public void notifyDataSetChanged() {
        //年月日市时分秒是联动的，所以只需要通知年的数据变化(默认年是第一个)
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            if (item.getType() == TYPE_YEAR) {
                picker.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }

    private void setDateItemIndex(int yearIndex, int monthIndex, int dayIndex) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int type = item.getType();
            switch (type) {
                case TYPE_YEAR:
                    picker.setCurrentItem(yearIndex);
                    break;
                case TYPE_MONTH:
                    picker.setCurrentItem(monthIndex);
                    break;
                case TYPE_DAY:
                    picker.setCurrentItem(dayIndex);
                    break;
            }
        }
    }

    private void setTimeItemIndex(int hourIndex, int minuteIndex, int secondIndex) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int type = item.getType();
            switch (type) {
                case TYPE_HOUR:
                    picker.setCurrentItem(hourIndex);
                    break;
                case TYPE_MINUTE:
                    picker.setCurrentItem(minuteIndex);
                    break;
                case TYPE_SECOND:
                    picker.setCurrentItem(secondIndex);
                    break;
            }
        }
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }

        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setTextSize(textSize);
        }
    }

    public void setTextColor(int textColor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setTextColor(textColor);
        }
    }

    public void setLineColor(int lineColor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setLineColor(lineColor);
        }
    }

    public void setLineWidth(int width) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setLineStorkeWidth(width);
        }
    }

    public void setItemSpace(int space) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setItemSpace(space);
        }
    }

    public void setVisibleItemCount(int itemCount) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setVisibleItemCount(itemCount);
        }
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setItemSize(itemWidth, itemHeight);
        }
    }

    @Override
    public void setShadow(int gravity, float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setShadowGravity(gravity);
            picker.setShadowFactor(factor);
        }
    }

    @Override
    public void setScrollAnimFactor(float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setFlingAnimFactor(factor);
        }
    }

    @Override
    public void setScrollMoveFactor(float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setFingerMoveFactor(factor);
        }
    }

    @Override
    public void setScrollOverOffset(int offset) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setOverOffset(offset);
        }
    }

    @Override
    public View asView() {
        return this;
    }

    public int getDateMode() {
        return mMode;
    }


    protected int getCurrentDate(String data, String suffix) {
        int suffixLeg = suffix == null ? 0 : suffix.length();
        String temp = data;
        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));

    }

    protected int getCurrentMonth(String data, String suffix) {
        int suffixLeg = suffix == null ? 0 : suffix.length();
        String temp = data;
        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg)) - 1;
    }

    protected void updateYears(int from, int to) {
        List<String> years = mData.get(TYPE_YEAR);
        if (years == null) {
            return;
        }

        years.clear();
        int size = to - from;
        for (int i = from; i <= from + size; i++) {
            years.add(i + mYearStr);
        }
    }

    protected void updateMonth(int minMonth, int maxMonth) {
        if (minMonth > maxMonth) {
            return;
        }

        List<String> months = mData.get(TYPE_MONTH);
        if (months == null) {
            return;
        }
        months.clear();

        for (int i = minMonth; i <= maxMonth; i++) {
            months.add((i + 1) + mMonthStr);
        }
    }

    protected void updateDay(int minDay, int maxDay) {
        if (minDay > maxDay) {
            return;
        }

        List<String> days = mData.get(TYPE_DAY);
        if (days == null) {
            return;
        }
        days.clear();

        for (int i = minDay; i <= maxDay; i++) {
            days.add(i + mDayStr);
        }
    }

    protected void updateHour(int minHour, int maxHour) {
        if (minHour > maxHour) {
            return;
        }

        List<String> hours = mData.get(TYPE_HOUR);
        if (hours == null) {
            return;
        }
        hours.clear();

        for (int i = minHour; i <= maxHour; i++) {
            hours.add(i + mHourStr);
        }
    }

    protected void updateMinute(int minMinute, int maxMinute) {
        if (minMinute > maxMinute) {
            return;
        }

        List<String> minutes = mData.get(TYPE_MINUTE);
        if (minutes == null) {
            return;
        }
        minutes.clear();

        for (int i = minMinute; i <= maxMinute; i++) {
            minutes.add(i + mMinuteStr);
        }
    }

    protected void updateSecond(int minSecond, int maxSecond) {
        if (minSecond > maxSecond) {
            return;
        }

        List<String> seconds = mData.get(TYPE_SECOND);
        if (seconds == null) {
            return;
        }
        seconds.clear();

        for (int i = minSecond; i <= maxSecond; i++) {
            seconds.add(i + mSecondStr);
        }
    }

    /**
     * 校正当前选择月份的天数
     */
    private void correctMaxDays() {
        correctDays(mSelectedMonth + 1, 1);
    }

    /**
     * 校正某月的天数，
     *
     * @param month    校正的哪一个月
     * @param startDay 当前最小开始天数
     */
    private void correctDays(int month, int startDay) {
        switch (month) {
            case 2:
                if (isLeapYear(mSelectedYear)) {
                    updateDay(startDay, 29);
                } else {
                    updateDay(startDay, 28);
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                updateDay(startDay, 31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                updateDay(startDay, 30);
                break;
        }
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    protected boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }

        return false;
    }

    /**
     * 是否是月底最后一天
     * 根据当前年月日判断
     *
     * @return
     */
    private boolean isMonthEnd(int year, int month, int day) {
        month = month + 1;
        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    return day == 29;
                } else {
                    return day == 28;
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day == 31;
            default:
                return day == 30;
        }
    }

    @Override
    public long getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mSelectedYear, mSelectedMonth, mSelectedDay, mSelectedHour, mSelectedMinute,
                mSelectedSecond);
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

    /**
     * 填充数据
     *
     * @param data
     */
    protected abstract void fillData(HashMap<Integer, List<String>> data);


}
