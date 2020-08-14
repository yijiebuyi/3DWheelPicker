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
public class WheelPickerImpl implements IWheelPicker {
	private int mOrientation = ScrollWheelPicker.VETTAICL;

	public WheelPickerImpl() {
		
	}
	
	public WheelPickerImpl(int orientation) {
		mOrientation = orientation;
	}
	
	@Override
	public int getUnitDeltaTotal(Scroller scroller) {
		return 0;
	}

	@Override
	public void startScroll(Scroller scroller, int start, int distance) {
		if (mOrientation == ScrollWheelPicker.HORIZENTAL) {
			scroller.startScroll(start, 0, start, 0);
		} else {
			scroller.startScroll(0, start, 0, distance);
		}
	}

	@Override
	public int computeRadius(int count, int space, int width, int height) {
		if (mOrientation == ScrollWheelPicker.HORIZENTAL) {
			return (int) (((count + 1) * width + (count - 1) * space) / Math.PI);
		} else {
			return (int) (((count + 1) * height + (count - 1) * space) / Math.PI);
		}
	}

	@Override
	public int getWheelWidth(int radius, int width) {
		return mOrientation == ScrollWheelPicker.VETTAICL ? width : 2 * radius;
	}

	@Override
	public int getWheelHeight(int radius, int height) {
		return mOrientation == ScrollWheelPicker.VETTAICL ? 2 * radius : height;
	}

	@Override
	public int getDisplay(int count, int space, int width, int height) {
		if (mOrientation == ScrollWheelPicker.HORIZENTAL) {
			 return (count / 2 + 1) * (width + space);
		} else {
			 return (count / 2 + 1) * (height + space);
		}
	}

	@Override
	public void rotateCamera(Camera camera, float degree) {
		camera.rotateX(-degree);
	}

	@Override
	public void matrixToCenter(Matrix matrix, float space, float x, float y) {
		matrix.preTranslate(-x, -(y + space));
        matrix.postTranslate(x, y + space);
	}
}
