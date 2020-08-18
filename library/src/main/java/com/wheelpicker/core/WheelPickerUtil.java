package com.wheelpicker.core;

import com.wheelpicker.widget.PickString;

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
}
