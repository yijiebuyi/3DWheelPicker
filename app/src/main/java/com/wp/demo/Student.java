package com.wp.demo;

import androidx.annotation.NonNull;

import com.wheelpicker.widget.PickString;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：
 * <p>
 * 作者：yijiebuyi
 * 创建时间：2020/8/18
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */

class Student implements PickString {
    public String name;
    public int age;

    public Student(String n, int a) {
        name = n;
        age = a;
    }

    @NonNull
    @Override
    public String toString() {
        return age + "岁";
    }

    @Override
    public String pickDisplayName() {
        return name;
    }
}
