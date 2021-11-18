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
 * {@link DataPicker}
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */
@Deprecated
public class OldDataPicker {

    /**
     * 获取生日日期
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickBirthday(Context context, @Nullable Date initDate, @Nullable PickOption option, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        option = checkOption(context, option);
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_BIRTHDAY);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked(picker);
                }
            }
        });

    }

    /**
     * 获取时间
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, @Nullable PickOption option, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        option = checkOption(context, option);
        final DateWheelPicker picker = (DateWheelPicker) buildDateWheelPicker(context, option, PickMode.MODE_DATE);
        picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked(picker);
                }
            }
        });
    }

    /**
     * 获取未来日期
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickFutureDate(Context context, @Nullable Date initDate, @Nullable PickOption option, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        option = checkOption(context, option);
        final FutureTimePicker picker = (FutureTimePicker) buildDateWheelPicker(context, option, PickMode.MODE_FUTURE_DATE);
        picker.setPickedTime(calendar.getTimeInMillis());
        //picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked(picker);
                }
            }
        });
    }

    /**
     * 获取单行数据
     *
     * @param context
     * @param initData
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData, @Nullable PickOption option, final OnDataPickListener listener) {
        option = checkOption(context, option);
        final SingleTextWheelPicker picker = new SingleTextWheelPicker(context);
        setPickViewStyle(picker, option);

        TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(srcData);
        picker.setAdapter(adapter);
        int index = WheelPickerUtil.indexOf(initData, srcData);
        picker.setCurrentItem(index < 0 ? 0 : index);

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
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
     * 获取年月日时分秒
     *
     * @param context
     * @param initDate
     * @param listener
     */
    public static void pickDateTime(Context context, @Nullable Date initDate, @Nullable PickOption option, final OnDatePickListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());

        option = checkOption(context, option);
        final DateTimePicker picker = (DateTimePicker)buildDateTimeWheelPicker(context, option, PickMode.MODE_PERIOD_DATE);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        picker.notifyDataSetChanged();

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked(picker);
                }
            }
        });

    }

    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    @Nullable PickOption option, final OnMultiDataPickListener listener) {
        pickData(context, initIndex, srcData, option, false, listener, null);
    }

    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    @Nullable PickOption option, final OnMultiDataPickListener listener,
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
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    @Nullable PickOption option, boolean wrapper,
                                    final OnMultiDataPickListener listener, final OnCascadeWheelListener cascadeListener) {
        option = checkOption(context, option);
        //List<WheelPickerData> pickerData = WheelPickerData.wrapper(initData, srcData);
        //WheelPickerData.disScrollable(0, pickerData);
        //WheelPickerData.placeHold(1, pickerData);
        final MultipleTextWheelPicker picker = wrapper ? new MultipleTextWheelPicker(context, WheelPickerData.wrapper(initIndex, srcData))
                : new MultipleTextWheelPicker(context, initIndex, srcData);

        picker.setOnCascadeWheelListener(cascadeListener);
        setPickViewStyle(picker, option);

        BottomSheet bottomSheet = buildBottomSheet(context, option, picker);
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
            case PickMode.MODE_DATE:
                pickerView = new DateWheelPicker(context);
                ((DateWheelPicker) pickerView).setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                ((DateWheelPicker) pickerView).setDateRange(dy - option.getAheadYears(), dy + option.getAfterYears());
                break;
        }

        setPickViewStyle(pickerView, option);

        return pickerView;
    }

    /**
     * @param context
     * @param option
     * @param mode
     * @return
     */
    private static IPickerView buildDateTimeWheelPicker(Context context, PickOption option, @PickMode.Mode int mode) {
        DateTimePicker pickerView = null;
        switch (mode) {
            case PickMode.MODE_BIRTHDAY:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_BIRTHDAY);
                pickerView.setWheelPickerVisibility(DateWheelPicker.TYPE_HH_MM_SS, View.GONE);
                break;
            case PickMode.MODE_FUTURE_DATE:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_PENDING);
                //pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.GONE);
                break;
            case PickMode.MODE_DATE:
                pickerView = new DateTimePicker(context);
                break;
            case PickMode.MODE_PERIOD_DATE:
                long current = System.currentTimeMillis();
                long from = current - 50 * 24 * 60 * 60 * 100;
                long to = current + 10 * 24 * 60 * 60 * 100;
                pickerView = new DateTimePicker(context, from, to, DateTimePicker.MODE_PERIOD);
                break;
        }

        setPickViewStyle(pickerView, option);

        return pickerView;
    }

    /**
     * 设置滚轮样式
     *
     * @param pickerView
     * @param option
     */
    private static void setPickViewStyle(IPickerView pickerView, PickOption option) {
        pickerView.asView().setBackgroundColor(option.getBackgroundColor());
        pickerView.asView().setPadding(0, option.getVerPadding(), 0, option.getVerPadding());

        //设置Item样式
        pickerView.setTextColor(option.getItemTextColor());
        pickerView.setVisibleItemCount(option.getVisibleItemCount());
        pickerView.setTextSize(option.getItemTextSize());
        pickerView.setItemSpace(option.getItemSpace());
        pickerView.setLineColor(option.getItemLineColor());
        pickerView.setLineWidth(option.getItemLineWidth());

        pickerView.setShadow(option.getShadowGravity(), option.getShadowFactor());
        pickerView.setScrollMoveFactor(option.getFingerMoveFactor());
        pickerView.setScrollAnimFactor(option.getFlingAnimFactor());
        pickerView.setScrollOverOffset(option.getOverScrollOffset());
    }

    /**
     * 获取底部弹出框
     *
     * @param context
     * @param pickerView
     * @return
     */
    private static BottomSheet buildBottomSheet(Context context, @Nullable PickOption option, IPickerView pickerView) {
        BottomSheet bottomSheet = new BottomSheet(context);
        if (option != null) {
            bottomSheet.setLeftBtnText(option.getLeftTitleText());
            bottomSheet.setRightBtnText(option.getRightTitleText());
            bottomSheet.setMiddleText(option.getMiddleTitleText());
            bottomSheet.setLeftBtnTextColor(option.getLeftTitleColor());
            bottomSheet.setRightBtnTextColor(option.getRightTitleColor());
            bottomSheet.setMiddleTextColor(option.getMiddleTitleColor());
            bottomSheet.setTitleBackground(option.getTitleBackground());

            bottomSheet.setTitleHeight(option.getTitleHeight());
        }
        bottomSheet.setContent(pickerView.asView());
        return bottomSheet;
    }

    private static PickOption checkOption(Context context, @Nullable PickOption option) {
        if (option != null) {
            return option;
        }

        return PickOption.getPickDefaultOptionBuilder(context).build();
    }
}
