package com.wheelpicker;

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

public interface OnMultiDataPickListener<T> {
    /**
     * @param indexArr 选中项的index数组
     * @param val      选中项的显示字符串数组
     * @param data     选中项的数据数组
     */
    public void onDataPicked(List<Integer> indexArr, List<String> val, List<T> data);
}
