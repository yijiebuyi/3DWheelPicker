package com.wheelpicker;

import android.content.Context;
import android.util.Log;

import com.wheelpicker.widget.PickString;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/19
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class AdministrativeUtil {
    public static AdministrativeMap loadCity(Context context) {
        AdministrativeMap map = null;
        try {
            String fileName = "pca_compress.json";
            InputStream is = context.getAssets().open(fileName);
            String json = convertStreamToString(is);
            Log.i("aaa", json);

            map = Json2AdministrativeMap(json);
            Log.i("aaa", "province: " + map.provinces.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static List<List<?>> getDefaultPickString(AdministrativeMap map) {
        if (map == null) {
            return null;
        }

        List<List<?>> pickDataList = new ArrayList<>();
        pickDataList.add(map.provinces);
        pickDataList.add(map.provinces.get(0).city);
        pickDataList.add(map.provinces.get(0).city.get(0).areas);

        return pickDataList;
    }

    /**
     * input 流转换为字符串
     *
     * @param is
     * @return
     */
    private static String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static AdministrativeMap Json2AdministrativeMap(String data) {
        AdministrativeMap map = new AdministrativeMap();
        map.provinces = new ArrayList<>(34);
        map.year = 2017;
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> itr = jsonObject.keys();

            while (itr.hasNext()) {
                String key = String.valueOf(itr.next());//得到Key
                JSONObject provinceObj = (JSONObject) jsonObject.get(key);
                //String value = jsonObject.getString(key);//得到值

                AdministrativeMap.Province province = new AdministrativeMap.Province();
                province.name = provinceObj.get("name").toString();
                province.code = key;
                province.city = new ArrayList<>();

                JSONObject cityChild = (JSONObject) provinceObj.get("child");//所有的市级
                Iterator<String> itrCty = cityChild.keys();

                while (itrCty.hasNext()) {
                    String cityCode = String.valueOf(itrCty.next());//得到Key
                    JSONObject cityObj = (JSONObject) cityChild.get(cityCode);

                    //System.err.print("市code= " + cityCode);//筛选出市级名称
                    //System.err.println(" " + cityObject.get("name") + " " + key);//筛选出市级code+上级code
                    AdministrativeMap.City city = new AdministrativeMap.City();
                    city.code = cityCode;
                    city.name = cityObj.getString("name");
                    city.areas = new ArrayList<>();
                    province.city.add(city);

                    JSONObject areaChild = (JSONObject) cityObj.get("child");//所有的区级 （取区级的时候，有些市级的区没有Key，会报错取不出来，导致之后的都取不出来，先删掉json数据！）
                    Iterator<String> itrArea = areaChild.keys();

                    while (itrArea.hasNext()) {
                        String areaKey = String.valueOf(itrArea.next());
                        AdministrativeMap.Area area = new AdministrativeMap.Area();
                        area.code = areaKey;
                        area.name = areaChild.getString(areaKey);
                        city.areas.add(area);
                    }
                }

                map.provinces.add(province);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;

    }

}
