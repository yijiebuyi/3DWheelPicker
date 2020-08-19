package com.wheelpicker;

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
    public static class Province {
        public String name;
        public String code;
        public List<City> city;
    }

    /**
     * 市
     */
    public static class City {
        public String name;
        public String code;
        public List<Area> areas;
    }

    /**
     * 区县
     */
    public static class Area {
        public String name;
        public String code;
        public List<Country> countries;
    }

    /**
     * 乡镇
     */
    public static class Country {
        public String name;
        public String code;
    }
}
