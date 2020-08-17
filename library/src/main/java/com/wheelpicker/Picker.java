package com.wheelpicker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wheelpicker.widget.IPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class Picker {

    /**
     * 获取生日日期
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public void pickBirthday(Context context, @Nullable Date initDate, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = getPickDefaultOptionBuilder(context).build();
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_BIRTHDAY);
        picker.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        BottomSheet bottomSheet = buildBottomSheet(context);
        bottomSheet.setContent(picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    calendar.set(picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(), 0, 0, 0);
                    listener.onDatePicked(calendar.getTimeInMillis(),
                            picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(), 0, 0, 0);
                }
            }
        });

    }

    /**
     * 获取时间
     *
     * @param context
     * @param initDate
     * @param witchPickVisible
     * @param aheadYears
     * @param afterYears
     * @param listener
     */
    public void pickDate(Context context, @Nullable Date initDate, int witchPickVisible,
                         int aheadYears, int afterYears, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = getPickDefaultOptionBuilder(context)
                .setDateWitchVisible(witchPickVisible)
                .setAheadYears(aheadYears)
                .setAfterYears(afterYears)
                .build();
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_DATE);
        BottomSheet bottomSheet = buildBottomSheet(context);

        bottomSheet.setContent(picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    calendar.set(picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(),
                            picker.getSelectedHour(), picker.getSelectedMinute(), picker.getSelectedSecond());
                    listener.onDatePicked(calendar.getTimeInMillis(),
                            picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(),
                            picker.getSelectedHour(), picker.getSelectedMinute(), picker.getSelectedSecond());
                }
            }
        });
    }

    /**
     * 获取未来日期
     *
     * @param context
     * @param initDate
     * @param durationDays
     * @param listener
     */
    public void pickFutureDate(Context context, @Nullable Date initDate, int durationDays, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = getPickDefaultOptionBuilder(context)
                .setDurationDays(durationDays)
                .build();
        final FutureTimePicker picker = (FutureTimePicker) buildDateWheelPicker(context, option, PickMode.MODE_FUTURE_DATE);

        BottomSheet bottomSheet = buildBottomSheet(context);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    calendar.set(picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(),
                            picker.getSelectedHour(), picker.getSelectedMinute(), picker.getSelectedSecond());
                    listener.onDatePicked(calendar.getTimeInMillis(),
                            picker.getSelectedYear(), picker.getSelectedMonth(), picker.getSelectedDay(),
                            picker.getSelectedHour(), picker.getSelectedMinute(), picker.getSelectedSecond());
                }
            }
        });
    }


    public <T> void pickData(Context context, @Nullable T initData, @NonNull List<T> srcData, final OnDataPickListener listener) {

    }

    public <T> void pickData(Context context, @Nullable List<T> initData, @NonNull List<List<T>> srcData, final OnMultiDataPickListener listener) {

    }

    /**
     * @param context
     * @param option
     * @param mode
     * @return
     */
    private IPickerView buildDateWheelPicker(Context context, PickOption option, @PickMode.Mode int mode) {
        IPickerView pickerView = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int dy = calendar.get(Calendar.YEAR);

        switch (mode) {
            case PickMode.MODE_BIRTHDAY:
                pickerView = new DateWheelPicker(context);
                ((DateWheelPicker) pickerView).setWheelPickerVisibility(DateWheelPicker.TYPE_HH_MM_SS, View.GONE);
                ((DateWheelPicker) pickerView).setDateRange(dy - 100, dy);
                break;
            case PickMode.MODE_FUTURE_DATE:
                pickerView = new FutureTimePicker(context);
                ((FutureTimePicker) pickerView).setFutureDuration(option.getDurationDays());
                break;
            default:
                pickerView = new DateWheelPicker(context);
                ((DateWheelPicker) pickerView).setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                ((DateWheelPicker) pickerView).setDateRange(dy - option.getAheadYears(), dy + option.getAfterYears());
                break;
        }

        setPickViewStyle(pickerView, option);

        return pickerView;
    }

    private void setPickViewStyle(IPickerView pickerView, PickOption option) {
        ((View) pickerView).setBackgroundColor(option.getBackgroundColor());
        ((View) pickerView).setPadding(0, option.getVerPadding(), 0, option.getVerPadding());

        pickerView.setTextColor(option.getItemTextColor());
        pickerView.setVisibleItemCount(option.getVisibleItemCount());
        pickerView.setTextSize(option.getItemTextSize());
        pickerView.setItemSpace(option.getItemSpace());
    }

    private BottomSheet buildBottomSheet(Context context) {
        BottomSheet bottomSheet = new BottomSheet(context);

        return bottomSheet;
    }

    /**
     * 获取Pick默认的设置
     * @param context
     * @return
     */
    public PickOption.Builder getPickDefaultOptionBuilder(Context context) {
        PickOption.Builder builder = new PickOption.Builder()
                .setVisibleItemCount(7)
                .setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25))
                .setItemTextColor(context.getResources().getColor(R.color.font_black))
                .setItemTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_32px))
                .setVerPadding(context.getResources().getDimensionPixelSize(R.dimen.px20))
                .setBackgroundColor(Color.WHITE);

        return builder;
    }

}
