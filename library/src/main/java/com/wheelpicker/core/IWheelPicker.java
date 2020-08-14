package com.wheelpicker.core;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.widget.Scroller;

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：
 *
 * 作者：yijiebuyi
 * 创建时间：2017/11/26
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public interface IWheelPicker {
    int getUnitDeltaTotal(Scroller scroller);

    /**
     * Start scrolling by providing a starting point and the distance to travel.
     * The scroll will use the default value of 250 milliseconds for the
     * duration.
     * 
     * @param scroller 
     * @param start
     * @param distance
     */
    void startScroll(Scroller scroller, int start, int distance);

    /**
     * Compute the radius of WheelPicker view
     * @param count The visible item count
     * @param space The space between two items 
     * @param width The item width
     * @param height The item height
     * @return
     */
    int computeRadius(int count, int space, int width, int height);

    /**
     * Compute the WheelPicker content view width 
     * 
     * @param radius The radius of WheelPicker view
     * @param width The item width
     * @return
     */
    int getWheelWidth(int radius, int width);

    /**
     * Compute the WheelPicker content view height 
     * 
     * @param radius The radius of WheelPicker view
     * @param height The item height
     * @return
     */
    int getWheelHeight(int radius, int height);

    int getDisplay(int count, int space, int width, int height);

    /**
     * Applies a rotation transform around the X axis by camera.
     * 
     * @param camera  A camera instance can be used to compute 3D transformations and
     *        generate a matrix that can be applied.
     * @param degree The angle of rotation around the X axis
     */
    void rotateCamera(Camera camera, float degree);

    /**
     * 
     * @param matrix The Matrix holds a 3x3 for transforming coordinates.
     * @param space The space between two items
     * @param x
     * @param y
     */
    void matrixToCenter(Matrix matrix, float space, float x, float y);

}
