package com.wheelpicker;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wheelpicker.core.WheelPickerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：
 * 作者：yijiebuyi
 * 创建时间：2018/4/20
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class WheelPickerData<T> {
    /**
     * 是否可滚动
     */
    public boolean scrollable = true;
    /**
     * 是否是占位view
     */
    public boolean placeHoldView = false;
    /**
     * 当前默认选择的txt
     */
    public T currentText;
    /**
     * 当前默认选中text的index
     */
    public int currentIndex;
    /**
     * 当前wheelPicker数据
     */
    public List<T> data;

    public int indexOf(T targetElement) {
        if (data == null || data.isEmpty()) {
            return -1;
        }

        String targetStr = WheelPickerUtil.formString(targetElement);
        for (int i = 0; i < data.size(); i++) {
            String str = WheelPickerUtil.formString(data.get(i));
            if (TextUtils.equals(targetStr, str)) {
                return i;
            }
        }

        return -1;
    }

    public String getStringVal(int index) {
        return WheelPickerUtil.formString(data.get(index));
    }

    public T get(int index) {
        return data.get(index);
    }


    /**
     * 默认都是可以支持滚动和不占位
     * @param initIndex
     * @param srcData
     * @return
     */
    public static List<WheelPickerData> wrapper(@Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData) {
        List<WheelPickerData> wrappers = new ArrayList<WheelPickerData>();
        int size = initIndex != null ? initIndex.size() : 0;
        for (int i = 0; i < srcData.size(); i++) {
            List<?> d = srcData.get(i);
            WheelPickerData wp = new WheelPickerData();
            wp.data = d;
            wp.currentIndex = i < size ? initIndex.get(i) : 0;
            wp.currentText = d.get(wp.currentIndex);
            wrappers.add(wp);
        }

        return wrappers;
    }

    /**
     * 设置scrollable不可滚动
     * @param index
     * @param wheelPickerData
     */
    public static void disScrollable(int index, List<WheelPickerData> wheelPickerData) {
        if (wheelPickerData == null) {
            return;
        }

        if (index < 0 || index >= wheelPickerData.size()) {
            return;
        }

        wheelPickerData.get(index).scrollable = false;
    }

    /**
     * 设置占位
     * @param index
     * @param wheelPickerData
     */
    public static void placeHold(int index, List<WheelPickerData> wheelPickerData) {
        if (wheelPickerData == null) {
            return;
        }

        if (index < 0 || index >= wheelPickerData.size()) {
            return;
        }

        wheelPickerData.get(index).placeHoldView = true;
    }
}
