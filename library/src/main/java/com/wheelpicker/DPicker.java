package com.wheelpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：数据选择器
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
@Deprecated
public class DPicker {
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    private static int mHour;
    private static int mMinute;
    private static int mSecond;

    private static Object mPickedData;

    /**
     * 选择生日
     *
     * @param context
     * @param birthday
     * @param pickListener
     */
    public static void pickBirthday(Context context, Date birthday, final OnBirthdayPickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        final DateWheelPicker picker = new DateWheelPicker(context);
        picker.setWheelPickerVisibility(DateWheelPicker.TYPE_HH_MM_SS, View.GONE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dy = calendar.get(Calendar.YEAR);
        int dm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DATE);

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));

        picker.setOnDatePickListener(new DateWheelPicker.OnDatePickListener() {
            @Override
            public void onDatePicked(int year, int month, int day, int hour, int minute, int second) {
                mYear = year;
                mMonth = month;
                mDay = day;
            }
        });

        picker.setDateRange(dy - 100, dy);
        //after set onDatePickerLister
        if (birthday != null) {
            calendar.setTime(birthday);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DATE);
        } else {
            mYear = dy;
            mMonth = dm;
            mDay = dd;
        }
        picker.setCurrentDate(mYear, mMonth, mDay);
        picker.notifyDataSetChanged();

        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        picker.setPadding(0, padding, 0, padding);

        bottomSheet.setContent(picker);
        bottomSheet.show();

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickListener != null) {
                    pickListener.onBirthPicked(mYear, mMonth, mDay);
                }
            }
        });
    }

    /**
     * 选择时间，默认是显示的“年月日时分秒"组合的时间选择器
     * 时间范围为当前时间100年前后100年后
     *
     * @param context
     * @param selectedDate
     * @param pickListener
     */
    public static void pickDate(Context context, Date selectedDate, final OnDatePickListener pickListener) {
        pickDate(context, selectedDate, DateWheelPicker.TYPE_ALL, View.VISIBLE,
                100, 100, pickListener);
    }

    /**
     * 选择时间，默认是显示的“年月日时分秒"组合的时间选择器
     * 通过@param whichWheelPick和@param可以设置哪些些时间控件显示或隐藏
     *
     * @param context
     * @param selectedDate   选择的日期
     * @param whichWheelPick 哪些控件
     * @param visibility     设置的哪些控件需要显示或隐藏
     * @param aheadYear      当前时间多少年前
     * @param afterYear      当前时间多少年后
     * @param pickListener
     */
    public static void pickDate(Context context, Date selectedDate, int whichWheelPick, int visibility,
                                int aheadYear, int afterYear, final OnDatePickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        final DateWheelPicker picker = new DateWheelPicker(context);
        picker.setWheelPickerVisibility(whichWheelPick, visibility);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dy = calendar.get(Calendar.YEAR);
        int dm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DATE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        int ss = calendar.get(Calendar.SECOND);

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));

        picker.setOnDatePickListener(new DateWheelPicker.OnDatePickListener() {
            @Override
            public void onDatePicked(int year, int month, int day, int hour, int minute, int second) {
                mYear = year;
                mMonth = month;
                mDay = day;
                mHour = hour;
                mMinute = minute;
                mSecond = second;
            }
        });


        //当前时间向前推aheadYear年，向后推afterYear年
        picker.setDateRange(dy - aheadYear, dy + afterYear);
        if (selectedDate != null) {
            calendar.setTime(selectedDate);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DATE);
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            mSecond = calendar.get(Calendar.SECOND);
        } else {
            mYear = dy;
            mMonth = dm;
            mDay = dd;
            mHour = hh;
            mMinute = mm;
            mSecond = ss;
        }

        picker.setCurrentTime(mHour, mMinute, mSecond);
        picker.setCurrentDate(mYear, mMonth, mDay);
        picker.notifyDataSetChanged();

        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        picker.setPadding(0, padding, 0, padding);

        bottomSheet.setContent(picker);
        bottomSheet.show();

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickListener != null) {
                    pickListener.onDatePicked(mYear, mMonth, mDay, mHour, mMinute, mSecond);
                }
            }
        });
    }

    /**
     * 选择未来时间，默认100年后
     *
     * @param context
     * @param currentDate
     * @param year         往后推多少年
     * @param pickListener
     */
    public static void pickFutureDate(Context context, Date currentDate, int whichWheelPick, int visibility,
                                      int year, final OnDatePickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        final DateWheelPicker picker = new DateWheelPicker(context);
        picker.setWheelPickerVisibility(whichWheelPick, visibility);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dy = calendar.get(Calendar.YEAR);
        int dm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DATE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        int ss = calendar.get(Calendar.SECOND);

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));

        picker.setOnDatePickListener(new DateWheelPicker.OnDatePickListener() {
            @Override
            public void onDatePicked(int year, int month, int day, int hour, int minute, int second) {
                mYear = year;
                mMonth = month;
                mDay = day;
                mHour = hour;
                mMinute = minute;
                mSecond = second;
            }
        });

        if (year <= 0) {
            year = 100;
        }
        picker.setDateRange(dy, dy + year);
        //after set onDatePickerLister
        if (currentDate != null) {
            calendar.setTime(currentDate);
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DATE);
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            mSecond = calendar.get(Calendar.SECOND);
        } else {
            mYear = dy;
            mMonth = dm;
            mDay = dd;
            mHour = hh;
            mMinute = mm;
            mSecond = ss;
        }
        picker.setCurrentTime(mHour, mMinute, mSecond);
        picker.setCurrentDate(mYear, mMonth, mDay);
        picker.notifyDataSetChanged();

        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        picker.setPadding(0, padding, 0, padding);

        bottomSheet.setContent(picker);
        bottomSheet.show();

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickListener != null) {
                    pickListener.onDatePicked(mYear, mMonth, mDay, mHour, mMinute, mSecond);
                }
            }
        });
    }

    /**
     * 选择未来时间
     *
     * @param context
     * @param currentDate
     * @param days         多少天后(如果传的时间小于等于0，默认是365天)
     * @param pickListener
     */
    public static void pickFutureDate(Context context, Date currentDate, int days,
                                      final OnDatePickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        final FutureTimePicker picker = new FutureTimePicker(context);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));
        if (days < 0) {
            days = 365;
        }
        picker.setFutureDuration(days);

        picker.setOnFutureDatePickListener(new FutureTimePicker.OnFutureDatePickListener() {
            @Override
            public void onDatePicked(int year, int month, int day, int hour, int minute, int second) {
                mYear = year;
                mMonth = month;
                mDay = day;
                mHour = hour;
                mMinute = minute;
                mSecond = second;
            }
        });

        if (currentDate != null) {
            picker.setPickedTime(currentDate.getTime());
        }

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickListener != null) {
                    pickListener.onDatePicked(mYear, mMonth, mDay, mHour, mMinute, mSecond);
                }
            }
        });

        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        picker.setPadding(0, padding, 0, padding);

        bottomSheet.setContent(picker);
        bottomSheet.show();
    }

    /**
     * 选择数据
     *
     * @param context
     * @param data           字符串数组
     * @param pickedListener
     */
    public static void pickData(Context context, List<String> data, final OnDataPickListener pickedListener) {
        if (data == null || data.isEmpty()) {
            return;
        }

        BottomSheet bottomSheet = new BottomSheet(context);

        final TextWheelPicker picker = new TextWheelPicker(context);
        TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(data);
        picker.setAdapter(adapter);

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));

        picker.setCurrentItem(0);
        mPickedData = data.get(0);

        int padding = context.getResources().getDimensionPixelOffset(R.dimen.px20);
        picker.setPadding(0, padding, 0, padding);

        bottomSheet.setContent(picker);
        bottomSheet.show();

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickedListener != null) {
                    pickedListener.onDataPicked(picker.getPickedData());
                }
            }
        });

    }

    public interface OnBirthdayPickListener {
        public void onBirthPicked(int year, int month, int day);
    }

    public interface OnDatePickListener {
        public void onDatePicked(int year, int month, int day, int hour, int minute, int second);
    }

    public interface OnDataPickListener {
        public void onDataPicked(Object data);
    }

}

