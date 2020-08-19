package com.wheelpicker;

import java.util.List;

/**
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
public interface OnCascadeWheelListener<T> {

	/**
	 *
	 * @param wheelIndex 滚轮的索引位置
	 * @param itemIndex  所有滚轮对应数据的索引位置数组
	 * @return T 级联数据
	 */
	public T onCascade(int wheelIndex, List<Integer> itemIndex);
}
