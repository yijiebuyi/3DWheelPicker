package com.wheelpicker;

import androidx.annotation.NonNull;

import com.wheelpicker.widget.PickString;

import java.util.ArrayList;
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

public class PickDataWrapper<T> {
    /**
     * 选择器显示的文本
     */
    public String val;
    /**
     * 源数据
     */
    public T data;

    public PickDataWrapper(String v, T d) {
        val = v;
        data = d;
    }

    public List<PickDataWrapper> wrapper(@NonNull List<T> data) {
        List<PickDataWrapper> wrappers = new ArrayList<PickDataWrapper>();
        for (T d : data) {
            if (d instanceof String) {
                wrappers.add(new PickDataWrapper((String) d, d));
            } else if (d instanceof PickString) {
                wrappers.add(new PickDataWrapper(((PickString) d).pickDisplayName(), d));
            } else {
                wrappers.add(new PickDataWrapper(d.toString(), d));
            }
        }

        return wrappers;
    }
}
