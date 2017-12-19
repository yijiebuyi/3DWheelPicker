package com.wheelpicker;
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
 * Date:2016/11/4
 * Desc:
 *
 * ======================================================================================== */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.Date;
import java.util.List;

public class DataPicker {
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    private static int mHour;
    private static int mMinute;
    private static int mSecond;

    private static Object mPickedData;

    public static void pickBirthday(Context context, Date birthday, final OnBirthdayPickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        final DateWheelPicker picker = new DateWheelPicker(context);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());
        int dy = calendar.get(java.util.Calendar.YEAR);
        int dm = calendar.get(java.util.Calendar.MONTH);
        int dd = calendar.get(java.util.Calendar.DATE);

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));

        picker.setOnDatePickListener(new DateWheelPicker.OnDatePickListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
            }
        });

        picker.setDateRange(dy - 100, dy);
        //after set onDatePickerLister
        if (birthday != null) {
            calendar.setTime(birthday);
            mYear = calendar.get(java.util.Calendar.YEAR);
            mMonth = calendar.get(java.util.Calendar.MONTH);
            mDay= calendar.get(java.util.Calendar.DATE);
        } else {
            mYear = dy;
            mMonth = dm;
            mDay = dd;
        }
        picker.setCurrentDate(mYear, mMonth, mDay);

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

    public static void pickData(Context context, List<String> data, final OnDataPickListener pickedListener) {
        if (data == null || data.isEmpty()) {
            return;
        }

        BottomSheet bottomSheet = new BottomSheet(context);

        TextWheelPicker picker = new TextWheelPicker(context);
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
        picker.setOnWheelPickedListener(new OnWheelPickedListener() {
            @Override
            public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data) {
                mPickedData = data;
            }
        });

        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickedListener != null) {
                    pickedListener.onDataPicked(mPickedData);
                }
            }
        });

    }

    public static void pickFutureDate(Context context, Date currentDate, final OnDatePickListener pickListener) {
        BottomSheet bottomSheet = new BottomSheet(context);
        FutureTimePicker picker = new FutureTimePicker(context);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(new Date());

        picker.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        picker.setTextColor(context.getResources().getColor(R.color.font_black));
        picker.setVisibleItemCount(7);
        picker.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px));
        picker.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));
        picker.setFutureDuration(365);

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

