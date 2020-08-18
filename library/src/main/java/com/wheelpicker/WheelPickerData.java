package com.wheelpicker;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wheelpicker.core.WheelPickerUtil;
import com.wheelpicker.widget.PickString;

import java.util.ArrayList;
import java.util.List;

/*
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

    public static <D>  List<WheelPickerData> wrapper(@Nullable List<D> initData, @NonNull List<List<D>> srcData) {
        List<WheelPickerData> wrappers = new ArrayList<WheelPickerData>();
        int size = initData != null ? initData.size() : 0;
        for (int i = 0; i < srcData.size(); i++) {
            List<D> d = srcData.get(i);
            WheelPickerData wp = new WheelPickerData();
            wp.data = d;
            wp.currentText = i < size ? initData.get(i) : null;
            wrappers.add(wp);
        }

        return wrappers;
    }
}
