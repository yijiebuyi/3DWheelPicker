package com.wheelpicker;

import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

public class PickMode {
    /**
     * 生日（过去的日期）: 年月日
     */
    public static final int MODE_BIRTHDAY = 1;
    /**
     * 普通日期选择，在一个时间范围内选择，默认当前日期前后100年
     */
    public static final int MODE_DATE = 2;
    /**
     * 未来日期选择, 当前时间往后推100年
     */
    public static final int MODE_FUTURE_DATE = 3;
    /**
     * 时间段
     */
    public static final int MODE_PERIOD_DATE = 4;

    /**
     * 普通数据选择（单列表数据）
     */
    public static final int MODE_DATA = 5;
    /**
     * 多行数据数据选择
     */
    public static final int MODE_MULTI_DATA = 6;

    private PickMode() {
    }

    @IntDef({MODE_BIRTHDAY, MODE_DATE, MODE_FUTURE_DATE, MODE_DATA, MODE_MULTI_DATA})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public @interface Mode {
    }
}
