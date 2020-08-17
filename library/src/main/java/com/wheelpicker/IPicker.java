package com.wheelpicker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：picker的功能
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public interface IPicker {
    /**
     * 时间选择器
     *
     * @param mode
     * @param initDate 初始化时，选中项的日期， 可以不传；
     *                 当不传时，默认选中的当前日期
     * @param listener
     */
    public void pickDate(Context context, @PickMode.Mode int mode, @Nullable Date initDate, OnDatePickListener listener);

    /**
     * 单数据选择器
     *
     * @param initData 初始化时，选中项的数据， 可以不传；
     *                 当不传时，默认选中的第一条数据
     * @param listener
     * @param <T>
     */
    public <T> void pickData(Context context, @Nullable T initData, @NonNull List<T> srcData, OnDataPickListener listener);

    /**
     * 多数据选择器
     *
     * @param initData 初始化时，选中项的数据， 可以不传；
     *                 当不传时，默认选中的索引值为0的数据
     * @param listener
     * @param <T>
     */
    public <T> void pickData(Context context, @Nullable List<T> initData, @NonNull List<List<T>> srcData, OnMultiDataPickListener listener);
}
