package com.wheelpicker;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：数据选择回调
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public interface OnDataPickListener<T> {
    /**
     * @param index 选中项的index
     * @param val   选中项的显示字符串
     * @param data  选中项的数据
     */
    public void onDataPicked(int index, String val, T data);
}
