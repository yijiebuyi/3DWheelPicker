package com.wheelpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateWheelPicker extends LinearLayout implements OnWheelPickedListener {
    public final static int TYPE_YEAR = 1 << 1;
    public final static int TYPE_MONTH = 1 << 2;
    public final static int TYPE_DAY = 1 << 3;
    public final static int TYPE_HOUR = 1 << 4;
    public final static int TYPE_MINUTE = 1 << 5;
    public final static int TYPE_SECOND = 1 << 6;

    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;
    private String mHourStr;
    private String mMinuteStr;
    private String mSecondStr;

    /**
     * 未来模式，时间下限为当前时间
     */
    public final static int MODE_PENDING = 0;
    /**
     * 生日模式，时间上限为当前时间
     */
    public final static int MODE_BIRTHDAY = 1;
    /**
     * 正常模式
     */
    public final static int MODE_RANGE = 2;

    public final static int BIRTHDAY_RANGE = 100;

    private TextWheelPicker mYearWheelPicker;
    private TextWheelPicker mMonthWheelPicker;
    private TextWheelPicker mDayWheelPicker;
    private TextWheelPicker mHourWheelPicker;
    private TextWheelPicker mMinuteWheelPicker;
    private TextWheelPicker mSecondWheelPicker;

    private int mCurrYear;
    private int mCurrMonth;
    private int mCurrDay;
    private int mCurrHour;
    private int mCurrMinute;
    private int mCurrSecond;
    private int mMode = MODE_BIRTHDAY;

    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDay;
    private int mSelectedHour;
    private int mSelectedMinute;
    private int mSelectedSecond;

    private List<String> mYears;
    private List<String> mMonths;
    private List<String> mDays;
    private List<String> mHours;
    private List<String> mMinutes;
    private List<String> mSeconds;

    private TextWheelPickerAdapter mYearPickerAdapter;
    private TextWheelPickerAdapter mMonthPickerAdapter;
    private TextWheelPickerAdapter mDayPickerAdapter;

    private TextWheelPickerAdapter mHourPickerAdapter;
    private TextWheelPickerAdapter mMinutePickerAdapter;
    private TextWheelPickerAdapter mSecondPickerAdapter;

    private OnDatePickListener mOnDatePickListener;

    public DateWheelPicker(Context context) {
        super(context);
        init();
    }

    public DateWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        mYearStr = getResources().getString(R.string._year);
        mMonthStr = getResources().getString(R.string._month);
        mDayStr = getResources().getString(R.string._day);
        mHourStr = getResources().getString(R.string._hour);
        mMinuteStr = getResources().getString(R.string._minute);
        mSecondStr = getResources().getString(R.string._second);

        LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llParams.weight = 1;

        mYearWheelPicker = new TextWheelPicker(getContext(), TYPE_YEAR);
        mMonthWheelPicker = new TextWheelPicker(getContext(), TYPE_MONTH);
        mDayWheelPicker = new TextWheelPicker(getContext(), TYPE_DAY);
        mHourWheelPicker = new TextWheelPicker(getContext(), TYPE_HOUR);
        mMinuteWheelPicker = new TextWheelPicker(getContext(), TYPE_MINUTE);
        mSecondWheelPicker = new TextWheelPicker(getContext(), TYPE_SECOND);

        mYearWheelPicker.setOnWheelPickedListener(this);
        mMonthWheelPicker.setOnWheelPickedListener(this);
        mDayWheelPicker.setOnWheelPickedListener(this);
        mHourWheelPicker.setOnWheelPickedListener(this);
        mMinuteWheelPicker.setOnWheelPickedListener(this);
        mSecondWheelPicker.setOnWheelPickedListener(this);

        addView(mYearWheelPicker, llParams);
        addView(mMonthWheelPicker, llParams);
        addView(mDayWheelPicker, llParams);
        addView(mHourWheelPicker, llParams);
        addView(mMinuteWheelPicker, llParams);
        addView(mSecondWheelPicker, llParams);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);
        mCurrHour = calendar.get(Calendar.HOUR);
        mCurrMinute = calendar.get(Calendar.MINUTE);
        mCurrSecond = calendar.get(Calendar.SECOND);

        initData();

        setWheelPickerVisibility(TYPE_HOUR | TYPE_SECOND | TYPE_MINUTE, View.GONE);
    }

    private void initData() {
        mYearPickerAdapter = new TextWheelPickerAdapter();
        mMonthPickerAdapter = new TextWheelPickerAdapter();
        mDayPickerAdapter = new TextWheelPickerAdapter();
        mHourPickerAdapter = new TextWheelPickerAdapter();
        mMinutePickerAdapter = new TextWheelPickerAdapter();
        mSecondPickerAdapter = new TextWheelPickerAdapter();

        mYears = new ArrayList<String>();
        mMonths = new ArrayList<String>();
        mDays = new ArrayList<String>();
        mHours = new ArrayList<String>();
        mMinutes = new ArrayList<String>();
        mSeconds = new ArrayList<String>();

        updateYears(mCurrYear - BIRTHDAY_RANGE + 1, mCurrYear);
        updateMaxMonths(11);
        updateMaxDays(31);
        updateMaxHour(24);
        updateMaxMinute(60);
        updateMaxSecond(60);

        mYearPickerAdapter.setData(mYears);
        mMonthPickerAdapter.setData(mMonths);
        mDayPickerAdapter.setData(mDays);
        mHourPickerAdapter.setData(mHours);
        mMinutePickerAdapter.setData(mMinutes);
        mSecondPickerAdapter.setData(mSeconds);

        mYearWheelPicker.setAdapter(mYearPickerAdapter);
        mMonthWheelPicker.setAdapter(mMonthPickerAdapter);
        mDayWheelPicker.setAdapter(mDayPickerAdapter);
        mHourWheelPicker.setAdapter(mHourPickerAdapter);
        mMinuteWheelPicker.setAdapter(mMinutePickerAdapter);
        mSecondWheelPicker.setAdapter(mSecondPickerAdapter);

    }

    public void setWheelPickerVisibility(int wheelType, int visibility) {
        if ((wheelType & TYPE_YEAR) != 0) {
            mYearWheelPicker.setVisibility(visibility);
        }

        if ((wheelType & TYPE_MONTH) != 0) {
            mMonthWheelPicker.setVisibility(visibility);
        }

        if ((wheelType & TYPE_DAY) != 0) {
            mDayWheelPicker.setVisibility(visibility);
        }

        if ((wheelType & TYPE_HOUR) != 0) {
            mHourWheelPicker.setVisibility(visibility);
        }

        if ((wheelType & TYPE_MINUTE) != 0) {
            mMinuteWheelPicker.setVisibility(visibility);
        }

        if ((wheelType & TYPE_SECOND) != 0) {
            mSecondWheelPicker.setVisibility(visibility);
        }
    }

    public void setOnDatePickListener(OnDatePickListener listener) {
        mOnDatePickListener = listener;
    }

    public void setDateRange(int from, int to) {
        if (from >= to) {
            throw new IllegalArgumentException("the from year less than to year!");
        }

        if (from < 0 || to < 0) {
            throw new IllegalArgumentException("the passed year must be > 0");
        }

        mMode = to == mCurrYear ? MODE_BIRTHDAY : MODE_RANGE;

        updateYears(from, to);
        mYearPickerAdapter.setData(mYears);
    }

    public void setCurrentDate(int year, int month, int day) {
        if (mYears.isEmpty() || mMonths.isEmpty() || mDays.isEmpty()) {
            return;
        }

        mSelectedYear = year;
        mSelectedMonth = month;
        mSelectedDay = day;

        if (mCurrYear == mSelectedYear && mMode == MODE_BIRTHDAY) {
            updateMaxMonths(mCurrMonth);
            if (mCurrMonth == mSelectedMonth) {
                updateMaxDays(mCurrDay);
            } else {
                correctDays();
            }
        } else {
            updateMaxMonths(11);
        }

        int yearIndex = Math.max(0, mYears.indexOf(year + mYearStr));
        int monthIndex = Math.max(0, mMonths.indexOf((month + 1) + mMonthStr));
        int dayIndex = Math.max(0, mDays.indexOf(day + mDayStr));

        setDateItemIndex(yearIndex, monthIndex, dayIndex);

        mMonthPickerAdapter.setData(mMonths);
        mDayPickerAdapter.setData(mDays);

        if (mOnDatePickListener != null) {
            mOnDatePickListener.onDatePicked(mSelectedYear, mSelectedMonth, mSelectedDay);
        }
    }

    public void setCurrentTime(int hour, int minute, int second) {
        if (mHours.isEmpty() || mMinutes.isEmpty() || mSeconds.isEmpty()) {
            return;
        }

        mSelectedHour = hour;
        mSelectedMinute = minute;
        mSelectedSecond = second;

        int HourIndex = Math.max(0, mYears.indexOf(hour + mHourStr));
        int MonthIndex = Math.max(0, mMonths.indexOf(minute + mMinuteStr));
        int SecondIndex = Math.max(0, mDays.indexOf(second + mSecondStr));

        updateMaxHour(mCurrHour);
        updateMaxMinute(mCurrMinute);
        updateMaxMinute(mCurrSecond);
        setTimeItemIndex(HourIndex, MonthIndex, SecondIndex);
    }

    private void setDateItemIndex(int yearIndex, int monthIndex, int dayIndex) {
        mYearWheelPicker.setCurrentItem(yearIndex);
        mMonthWheelPicker.setCurrentItem(monthIndex);
        mDayWheelPicker.setCurrentItem(dayIndex);
    }

    private void setTimeItemIndex(int hourIndex, int minuteIndex, int secondIndex) {
        mYearWheelPicker.setCurrentItem(hourIndex);
        mMonthWheelPicker.setCurrentItem(minuteIndex);
        mDayWheelPicker.setCurrentItem(secondIndex);
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }

        mYearWheelPicker.setTextSize(textSize);
        mMonthWheelPicker.setTextSize(textSize);
        mDayWheelPicker.setTextSize(textSize);
        mHourWheelPicker.setTextSize(textSize);
        mMinuteWheelPicker.setTextSize(textSize);
        mSecondWheelPicker.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        mYearWheelPicker.setTextColor(textColor);
        mMonthWheelPicker.setTextColor(textColor);
        mDayWheelPicker.setTextColor(textColor);
        mHourWheelPicker.setTextColor(textColor);
        mMinuteWheelPicker.setTextColor(textColor);
        mSecondWheelPicker.setTextColor(textColor);
    }

    public void setLineColor(int lineColor) {
        mYearWheelPicker.setLineColor(lineColor);
        mMonthWheelPicker.setLineColor(lineColor);
        mDayWheelPicker.setLineColor(lineColor);
        mHourWheelPicker.setLineColor(lineColor);
        mMinuteWheelPicker.setLineColor(lineColor);
        mSecondWheelPicker.setLineColor(lineColor);
    }

    public void setLineWidth(int width) {
        mYearWheelPicker.setLineStorkeWidth(width);
        mMonthWheelPicker.setLineStorkeWidth(width);
        mDayWheelPicker.setLineStorkeWidth(width);
        mHourWheelPicker.setLineColor(width);
        mMinuteWheelPicker.setLineColor(width);
        mSecondWheelPicker.setLineColor(width);
    }

    public void setItemSpace(int space) {
        mYearWheelPicker.setItemSpace(space);
        mMonthWheelPicker.setItemSpace(space);
        mDayWheelPicker.setItemSpace(space);
        mHourWheelPicker.setItemSpace(space);
        mMinuteWheelPicker.setItemSpace(space);
        mSecondWheelPicker.setItemSpace(space);
    }

    public void setVisibleItemCount(int itemCount) {
        mYearWheelPicker.setVisibleItemCount(itemCount);
        mMonthWheelPicker.setVisibleItemCount(itemCount);
        mDayWheelPicker.setVisibleItemCount(itemCount);
        mHourWheelPicker.setVisibleItemCount(itemCount);
        mMinuteWheelPicker.setVisibleItemCount(itemCount);
        mSecondWheelPicker.setVisibleItemCount(itemCount);
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        mYearWheelPicker.setItemSize(itemWidth, itemHeight);
        mMonthWheelPicker.setItemSize(itemWidth, itemHeight);
        mDayWheelPicker.setItemSize(itemWidth, itemHeight);
        mHourWheelPicker.setItemSize(itemWidth, itemHeight);
        mMinuteWheelPicker.setItemSize(itemWidth, itemHeight);
        mSecondWheelPicker.setItemSize(itemWidth, itemHeight);
    }

    public int getDateMode() {
        return mMode;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data) {
        switch (wheelPicker.getId()) {
            case TYPE_YEAR:
                int year = getCurrentDate(data, mYearStr);
                if (year > 0) {
                    mSelectedYear = year;
                }

                boolean changed = false;
                if (index == mYears.size() - 1 && mSelectedYear == mCurrYear) {
                    //current year
                    updateMaxMonths(mCurrMonth);
                } else {
                    changed = mMonths.size() != 12;
                    if (changed) {
                        updateMaxMonths(11);
                    }
                }

                //update month
                mMonthPickerAdapter.setData(mMonths);
                break;
            case TYPE_MONTH:
                int month = getCurrentMonth(data, mMonthStr);
                if (month >= 0) {
                    mSelectedMonth = month;
                }
                if (index == mMonths.size() - 1 && mSelectedYear == mCurrYear) {
                    //current day
                    updateMaxDays(mCurrDay);
                } else {
                    correctDays();
                }
                mDayPickerAdapter.setData(mDays);
                break;
            case TYPE_DAY:
                mSelectedDay = getCurrentDate(data, mDayStr);
                break;
            default:
                break;
        }

        if (mOnDatePickListener != null) {
            mOnDatePickListener.onDatePicked(mSelectedYear, mSelectedMonth, mSelectedDay);
        }
    }


    private void correctDays() {
        int month = mSelectedMonth + 1;
        if (month == 2) {
            if (isLeapYear(mSelectedYear)) {
                updateMaxDays(29);
            } else {
                updateMaxDays(28);
            }
        } else {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    updateMaxDays(31);
                    break;
                default:
                    updateMaxDays(30);
                    break;
            }
        }
    }


    private void updateYears(int from, int to) {
        mYears.clear();

        int size = to - from;
        for (int i = from; i <= from + size; i++) {
            mYears.add(i + mYearStr);
        }
    }

    private void updateMaxMonths(int maxMonth) {
        mMonths.clear();

        for (int i = 0; i <= maxMonth; i++) {
            mMonths.add((i + 1) + mMonthStr);
        }
    }

    private void updateMaxDays(int maxDay) {
        mDays.clear();

        for (int i = 1; i <= maxDay; i++) {
            mDays.add(i + mDayStr);
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

    private void updateMinSecond(int minSecond) {
        mSeconds.clear();
        for (int i = minSecond; i < 60; i++) {
            mSeconds.add(i + mSecondStr);
        }
    }

    private void updateMaxHour(int maxHour) {
        mHours.clear();
        maxHour = Math.max(24, maxHour);
        for (int i = 0; i < maxHour; i++) {
            mHours.add(i + mHourStr);
        }
    }

    private void updateMaxMinute(int maxMinute) {
        mMinutes.clear();
        maxMinute = Math.max(60, maxMinute);
        for (int i = 0; i < maxMinute; i++) {
            mMinutes.add(i + mMinuteStr);
        }
    }

    private void updateMaxSecond(int maxSecond) {
        mSeconds.clear();
        maxSecond = Math.max(60, maxSecond);
        for (int i = 0; i < maxSecond; i++) {
            mSeconds.add(i + mSecondStr);
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

    private int getCurrentMonth(Object data, String suffix) {
        if (data instanceof String) {
            int suffixLeg = suffix == null ? 0 : suffix.length();
            String temp = (String) data;
            return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg)) - 1;
        }

        return -1;
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }

        return false;
    }

    public interface OnDatePickListener {
        public void onDatePicked(int year, int month, int day);
    }
}
