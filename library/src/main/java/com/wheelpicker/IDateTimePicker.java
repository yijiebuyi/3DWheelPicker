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
     * 获取选择的毫秒，距离1970
     * @return
     */
    public long getTime();

    /**
     * 选中的年
     * @return
     */
    public int getSelectedYear();

    /**
     * 选中的月
     * @return
     */
    public int getSelectedMonth();

    /**
     * 选中的日
     * @return
     */
    public int getSelectedDay();
    /**
     * 选中的小时
     * @return
     */
    public int getSelectedHour();

    /**
     * 选择的分
     * @return
     */
    public int getSelectedMinute();

    /**
     * 选择的秒
     * @return
     */
    public int getSelectedSecond();
}
