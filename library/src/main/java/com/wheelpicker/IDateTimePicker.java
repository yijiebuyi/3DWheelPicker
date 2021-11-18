package com.wheelpicker;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：日期时间picker
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/9/28
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public interface IDateTimePicker {
    /**
     * 设置默认选择日期：年月日
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public void setDefaultSelectedDate(int year, int month, int day);

    /**
     * 设置默认选中的时间：时分秒
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     */
    public void setDefaultSelectedTime(int hour, int minute, int second);

    /**
     * 设置那些picker 显示或隐藏
     * 1.若当前设置visibility 设置为VISIBLE,
     * wheelType匹配的picker显示，没匹配的就隐藏
     * <p>
     * 2.若当前设置visibility 设置为GONE
     * wheelType匹配的picker隐藏，没匹配的就显示
     *
     * @param wheelType  哪些picker
     * @param visibility 显示或隐藏, 不能取值为INVISIBLE
     */
    public void setWheelPickerVisibility(int wheelType, int visibility);

    /**
     * 获取选择的毫秒，距离1970
     *
     * @return
     */
    public long getTime();

    /**
     * 选中的年
     *
     * @return
     */
    public int getSelectedYear();

    /**
     * 选中的月
     *
     * @return
     */
    public int getSelectedMonth();

    /**
     * 选中的日
     *
     * @return
     */
    public int getSelectedDay();

    /**
     * 选中的小时
     *
     * @return
     */
    public int getSelectedHour();

    /**
     * 选择的分
     *
     * @return
     */
    public int getSelectedMinute();

    /**
     * 选择的秒
     *
     * @return
     */
    public int getSelectedSecond();
}
