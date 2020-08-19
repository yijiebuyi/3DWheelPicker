package com.wheelpicker;

import com.wheelpicker.widget.PickString;

import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：行政区域
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/19
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class AdministrativeMap {
    public int year;
    public List<Province> provinces;

    /**
     * 省
     */
    public static class Province implements PickString {
        public String name;
        public String code;
        public List<City> city;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 市
     */
    public static class City implements PickString{
        public String name;
        public String code;
        public List<Area> areas;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 区县
     */
    public static class Area implements PickString{
        public String name;
        public String code;
        public List<Country> countries;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 乡镇
     */
    public static class Country  implements PickString{
        public String name;
        public String code;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }
}
