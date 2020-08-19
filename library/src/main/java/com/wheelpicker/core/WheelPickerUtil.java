package com.wheelpicker.core;

import android.text.TextUtils;

import com.wheelpicker.widget.PickString;

import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/18
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class WheelPickerUtil {
    public static <T> String formString(T d) {
        if (d == null) {
            return null;
        }

        if (d instanceof String) {
            return (String) d;
        } else if (d instanceof PickString) {
            return ((PickString) d).pickDisplayName();
        } else {
            return d.toString();
        }
    }

    public static <T> int indexOf(T targetElement, List<T> data) {
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

    public static <T> String getStringVal(int index, List<T> data) {
        return WheelPickerUtil.formString(data.get(index));
    }
}
