package com.wheelpicker;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import com.wheelpicker.widget.IPickerView;

/**
 * Copyright (C) 2016-2021,
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2021/11/18
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class DataPickerUtils {

    /**
     * 是否是月底最后一天
     * 根据当前年月日判断
     *
     * @return
     */
    public static boolean isMonthEnd(int year, int month, int day) {
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

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }

        return false;
    }


    /**
     * @param context
     * @param option
     * @param mode
     * @return
     */
    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                           @PickMode.Mode int mode) {
        DateTimePicker pickerView = null;
        switch (mode) {
            case PickMode.MODE_BIRTHDAY:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_BIRTHDAY);
                pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);
                break;
            case PickMode.MODE_FUTURE_DATE:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_PENDING);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
            case PickMode.MODE_DATE:
                pickerView = new DateTimePicker(context);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
        }

        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }

    /**
     * @param context
     * @param option
     * @return
     */
    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                           long from, long to,
                                                           @PickMode.Mode int pickMode) {

        int mode = DateTimePicker.MODE_NORMAL;
        switch (pickMode) {
            case PickMode.MODE_BIRTHDAY:
                mode = DateTimePicker.MODE_BIRTHDAY;
                break;
            case PickMode.MODE_FUTURE_DATE:
                mode = DateTimePicker.MODE_PENDING;
                break;
            case PickMode.MODE_PERIOD_DATE:
                mode = DateTimePicker.MODE_PERIOD;
                break;
            case PickMode.MODE_DATE:
                mode = DateTimePicker.MODE_NORMAL;
                break;
        }

        DateTimePicker pickerView = new DateTimePicker(context, from, to, mode);
        pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }


    /**
     * 设置滚轮样式
     *
     * @param pickerView
     * @param option
     */
    public static void setPickViewStyle(IPickerView pickerView, PickOption option) {
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
    public static BottomSheet buildBottomSheet(Context context, @Nullable PickOption option,
                                                IPickerView pickerView) {
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

    public static PickOption checkOption(Context context, @Nullable PickOption option) {
        if (option != null) {
            return option;
        }

        return PickOption.getPickDefaultOptionBuilder(context).build();
    }
}
