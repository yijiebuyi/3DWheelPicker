package com.wheelpicker;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：日期选择器回调
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/17
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public interface OnDatePickListener {
    /**
     * @param time   返回选择时间距离 1970的时间戳
     * @param year   返回选择日期的是哪一年
     * @param month  返回选择日期的是哪一月
     * @param day    返回选择日期的是哪一天
     * @param hour   返回选择日期某一天的哪一个小时
     * @param minute 返回选择日期某一天的哪一分钟
     * @param second 返回选择日期某一天的哪一秒钟
     */
    public void onDatePicked(long time, int year, int month,
                             int day, int hour, int minute, int second);
}
