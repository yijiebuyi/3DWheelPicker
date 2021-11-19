package com.wheelpicker;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wheelpicker.core.WheelPickerUtil;
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
 * 1. 日期时间选择：生日日期，未来日期，过去日期，时间段日期
 * 2. 数据选择：单行数据，多行数据，多行级联数据
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class DataPicker {

    //==============================================================================================
    //==============================================================================================
    //=========================================date picker==========================================
    //==============================================================================================
    //==============================================================================================

    /**
     * 获取日期
     *
     * @param context
     * @param initDate 初始化时选择的日期
     * @param mode     获取哪一种数据
     * @param option
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        option = DataPickerUtils.checkOption(context, option);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, picker, listener);
    }

    /**
     * 获取日期
     *
     * @param context
     * @param initDate
     * @param mode
     * @param from     开始日期
     * @param to       结束日期
     * @param option
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                long from, long to,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        option = DataPickerUtils.checkOption(context, option);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option,
                from, to, mode);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

        showPicker(context, option, picker, listener);
    }

    /**
     * 获取未来日期：显示（天、小时、分钟）的组合
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickFutureDate(Context context, @Nullable Date initDate,
                                      @Nullable PickOption option,
                                      final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        option = DataPickerUtils.checkOption(context, option);
        final FutureTimePicker picker = new FutureTimePicker(context);
        //必须设置Duration Days
        picker.setFutureDuration(option.getDurationDays());
        DataPickerUtils.setPickViewStyle(picker, option);
        picker.setPickedTime(calendar.getTimeInMillis());

        showPicker(context, option, picker, listener);
    }


    /**
     * 显示 Time Picker
     *
     * @param context
     * @param option
     * @param picker
     * @param listener
     */
    private static void showPicker(Context context, @NonNull PickOption option,
                                   final IPickerView picker,
                                   final OnDatePickListener listener) {
        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked((IDateTimePicker) picker);
                }
            }
        });
    }


    //==============================================================================================
    //==============================================================================================
    //=========================================data picker==========================================
    //==============================================================================================
    //==============================================================================================

    /**
     * 获取单行数据
     *
     * @param context
     * @param initData
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData,
                                    @Nullable PickOption option, final OnDataPickListener listener) {
        option = DataPickerUtils.checkOption(context, option);
        final SingleTextWheelPicker picker = new SingleTextWheelPicker(context);
        DataPickerUtils.setPickViewStyle(picker, option);

        TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(srcData);
        picker.setAdapter(adapter);
        int index = WheelPickerUtil.indexOf(initData, srcData);
        picker.setCurrentItem(index < 0 ? 0 : index);

        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, picker);
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
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    final OnMultiDataPickListener listener) {
        pickData(context, initIndex, srcData, option, false, listener, null);
    }

    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    final OnMultiDataPickListener listener,
                                    final OnCascadeWheelListener cascadeListener) {
        pickData(context, initIndex, srcData, option, false, listener, cascadeListener);
    }

    /**
     * 多行数据选择
     *
     * @param context
     * @param initIndex
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    boolean wrapper, final OnMultiDataPickListener listener,
                                    final OnCascadeWheelListener cascadeListener) {
        option = DataPickerUtils.checkOption(context, option);
        //List<WheelPickerData> pickerData = WheelPickerData.wrapper(initData, srcData);
        //WheelPickerData.disScrollable(0, pickerData);
        //WheelPickerData.placeHold(1, pickerData);
        final MultipleTextWheelPicker picker = wrapper ?
                new MultipleTextWheelPicker(context, WheelPickerData.wrapper(initIndex, srcData)) :
                new MultipleTextWheelPicker(context, initIndex, srcData);

        picker.setOnCascadeWheelListener(cascadeListener);
        DataPickerUtils.setPickViewStyle(picker, option);

        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, picker);
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
}
