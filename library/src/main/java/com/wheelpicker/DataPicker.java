package com.wheelpicker;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：数据选择器（包括日期，时间，未来时间，数据等）
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class DataPicker {

    /**
     * 获取生日日期
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickBirthday(Context context, @Nullable Date initDate, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = PickOption.getPickDefaultOptionBuilder(context).build();
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_BIRTHDAY);
        picker.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, picker);
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
    public static void pickDate(Context context, @Nullable Date initDate, int witchPickVisible,
                         int aheadYears, int afterYears, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = PickOption.getPickDefaultOptionBuilder(context)
                .setDateWitchVisible(witchPickVisible)
                .setAheadYears(aheadYears)
                .setAfterYears(afterYears)
                .build();
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_DATE);
        picker.setCurrentTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        picker.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, picker);
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
    public static void pickFutureDate(Context context, @Nullable Date initDate, int durationDays, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        PickOption option = PickOption.getPickDefaultOptionBuilder(context)
                .setDurationDays(durationDays)
                .build();
        final FutureTimePicker picker = (FutureTimePicker) buildDateWheelPicker(context, option, PickMode.MODE_FUTURE_DATE);
        picker.setPickedTime(calendar.getTimeInMillis());
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, picker);
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
     * 获取单行数据
     * @param context
     * @param initData
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData, final OnDataPickListener listener) {
        PickOption option = PickOption.getPickDefaultOptionBuilder(context).build();
        final SingleTextWheelPicker picker = new SingleTextWheelPicker(context);
        setPickViewStyle(picker, option);

        TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(srcData);
        picker.setAdapter(adapter);

        BottomSheet bottomSheet = buildBottomSheet(context, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int index = picker.getPickedIndex();
                    listener.onDataPicked(index, picker.getPickedData(), srcData.get(index));
                }
            }
        });
    }

    /**
     * 多行数据选择
     * @see #pickData(Context, List, List, OnMultiDataPickListener, OnCascadeWheelListener)
     */
    public static <T> void pickData(Context context, @Nullable List<?> initData, @NonNull List<List<?>> srcData,
                                    final OnMultiDataPickListener listener) {
        pickData(context, initData, srcData, false, listener, null);
    }

    /**
     * 多行数据选择
     * @param context
     * @param initData
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<?> initData, @NonNull List<List<?>> srcData, boolean wrapper,
                                    final OnMultiDataPickListener listener, final OnCascadeWheelListener cascadeListener) {
        PickOption option = PickOption.getPickDefaultOptionBuilder(context).build();

        List<WheelPickerData> pickerData = WheelPickerData.wrapper(initData, srcData);
        //WheelPickerData.disScrollable(0, pickerData);
        //WheelPickerData.placeHold(1, pickerData);
        final MultipleTextWheelPicker picker = wrapper ? new MultipleTextWheelPicker(context, pickerData) :
                new MultipleTextWheelPicker(context, srcData);

        picker.setOnCascadeWheelListener(cascadeListener);
        setPickViewStyle(picker, option);

        BottomSheet bottomSheet = buildBottomSheet(context, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    List<String> pickedVal = picker.getPickedVal();
                    List<Integer> pickedIndex = picker.getPickedIndex();
                    List<T> pickedData = picker.getPickedData();
                    listener.onDataPicked(pickedIndex, pickedVal, pickedData);
                }
            }
        });
    }

    /**
     * @param context
     * @param option
     * @param mode
     * @return
     */
    private static IPickerView buildDateWheelPicker(Context context, PickOption option, @PickMode.Mode int mode) {
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

    /**
     * 设置滚轮样式
     * @param pickerView
     * @param option
     */
    private static void setPickViewStyle(IPickerView pickerView, PickOption option) {
        ((View) pickerView).setBackgroundColor(option.getBackgroundColor());
        ((View) pickerView).setPadding(0, option.getVerPadding(), 0, option.getVerPadding());

        pickerView.setTextColor(option.getItemTextColor());
        pickerView.setVisibleItemCount(option.getVisibleItemCount());
        pickerView.setTextSize(option.getItemTextSize());
        pickerView.setItemSpace(option.getItemSpace());
    }

    /**
     * 获取底部弹出框
     * @param context
     * @param pickerView
     * @return
     */
    private static BottomSheet buildBottomSheet(Context context, IPickerView pickerView) {
        BottomSheet bottomSheet = new BottomSheet(context);
        bottomSheet.setContent(pickerView.asView());
        return bottomSheet;
    }
}
