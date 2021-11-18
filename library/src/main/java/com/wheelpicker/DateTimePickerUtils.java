package com.wheelpicker;

/**
 * Copyright (C) 2016-2021,
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2021/11/18
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class DateTimePickerUtils {

    /**
     * 获取具体的日期数字部分 如'2021年' 返回 2021
     * @param data  显示的数据 xxxx年  xx月  xx日 xx时 xx分 xx秒
     * @param suffix 年 月 日 时 分 秒
     * @return 具体的年月日时分秒的数字
     */
    public static int getCurrentDate(String data, String suffix) {
        int suffixLeg = suffix == null ? 0 : suffix.length();
        String temp = data;
        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));

    }

    /**
     * 获取具体的月份数字部分 如'10月' 返回 10
     * @param data 显示的数据 xx月
     * @param suffix 月
     * @return 具体月的数字
     */
    public static int getCurrentMonth(String data, String suffix) {
        return getCurrentDate(data, suffix) - 1;
    }

    /**
     * 是否是月底最后一天
     * 根据当前年月日判断
     *
     * @return
     */
    public static boolean isMonthEnd(int year, int month, int day) {
        month = month + 1;
        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    return day == 29;
                } else {
                    return day == 28;
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day == 31;
            default:
                return day == 30;
        }
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }

        return false;
    }
}
