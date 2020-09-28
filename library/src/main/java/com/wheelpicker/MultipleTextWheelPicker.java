package com.wheelpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.core.WheelPickerUtil;
import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 * <p>
 * 功能描述：多wheel数据选择Picker
 * 作者：yijiebuyi
 * 创建时间：2018/4/20
 * <p>
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class MultipleTextWheelPicker<D, T> extends LinearLayout
        implements OnWheelPickedListener<String>, IPickerView {
    /**
     * 正常
     */
    private static final int MODE_NORMAL = 0;
    /**
     * 级联
     */
    private static final int MODE_CASCADE = 1;

    protected List<T> mSrcDataList;
    protected List<Integer> mInitIndex;
    protected List<TextWheelPicker> mWheelPickers;
    protected List<TextWheelPickerAdapter> mTextWheelPickerAdapters;

    protected List<String> mPickedVal;
    protected List<Integer> mPickedIndex;
    protected List mPickedData;
    /**
     * 级联数据监听器，当设置监听器时，表示数据级联；否则数据非级联
     */
    private OnCascadeWheelListener<List<T>> mOnCascadeWheelListener;
    private boolean mTouch = false;

    public MultipleTextWheelPicker(Context context) {
        this(context, null);
    }

    public MultipleTextWheelPicker(Context context, List<T> data) {
        super(context);
        init(data);
    }

    public MultipleTextWheelPicker(Context context, List<Integer> index, List<T> data) {
        super(context);
        mInitIndex = index;
        init(data);
    }

    public void setOnCascadeWheelListener (OnCascadeWheelListener listener) {
        mOnCascadeWheelListener = listener;
    }

    private void init(List<T> data) {
        mSrcDataList = data;

        //set style
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        //init data
        if (data != null && !data.isEmpty()) {
            int size = data.size();

            mWheelPickers = new ArrayList<TextWheelPicker>(size);
            mTextWheelPickerAdapters = new ArrayList<TextWheelPickerAdapter>(size);

            mPickedVal = new ArrayList<String>(size);
            mPickedIndex = new ArrayList<Integer>(size);
            mPickedData = new ArrayList<>(size);

            LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            llParams.weight = 1;

            Context context = getContext();
            for (int i = 0; i < size; i++) {
                T d = data.get(i);
                int id = i;
                List<D> pickerDataList = null;
                if (isHolderView(d)) {
                    //占位view
                    TextWheelPicker holdView = new TextWheelPicker(context, id);
                    holdView.setTouchable(false);
                    holdView.setLineStorkeWidth(0);

                    addView(holdView, llParams);
                } else if ((pickerDataList = asList(d)) != null /*&& !pickerDataList.isEmpty()*/) {
                    TextWheelPicker twp = new TextWheelPicker(context, id);
                    twp.setTouchable(scrollable(d));
                    twp.setOnWheelPickedListener(this);

                    addView(twp, llParams);
                    mWheelPickers.add(twp);

                    TextWheelPickerAdapter adapter = new TextWheelPickerAdapter();
                    adapter.setData(pickerDataList);
                    mTextWheelPickerAdapters.add(adapter);

                    //set current
                    int index = getIndex(i, d);
                    twp.setCurrentItemWithoutReLayout(index);

                    if (pickerDataList.isEmpty()) {
                        mPickedVal.add(null);
                        mPickedIndex.add(0);
                        mPickedData.add(null);
                    } else {
                        mPickedVal.add(WheelPickerUtil.getStringVal(index, pickerDataList));
                        mPickedIndex.add(index);
                        mPickedData.add(pickerDataList.get(index));
                    }

                    twp.setAdapter(adapter);
                }
            }
        }
    }

    private int getIndex(int i, T data) {
        int index = 0;
        if (data instanceof WheelPickerData) {
            WheelPickerData<D> wp = (WheelPickerData) data;
            index = Math.max(0, wp.currentIndex);
        } else if (mInitIndex != null && !mInitIndex.isEmpty() && i < mInitIndex.size()) {
            index = Math.max(0, mInitIndex.get(i));
        }

        return index;
    }

    private List asList(T data) {
        if (data instanceof List) {
            return (List) data;
        } else if (data instanceof WheelPickerData) {
            return ((WheelPickerData) data).data;
        }
        return null;
    }

    private boolean isHolderView(T data) {
        if (data instanceof WheelPickerData) {
            return ((WheelPickerData) data).placeHoldView;
        }

        return false;
    }

    private boolean scrollable(T data) {
        if (data instanceof WheelPickerData) {
            return ((WheelPickerData) data).scrollable;
        }

        return true;
    }

    public void notifyDataSetChanged() {
        if (mTextWheelPickerAdapters != null && !mTextWheelPickerAdapters.isEmpty()) {
            if (mOnCascadeWheelListener == null) {
                for (TextWheelPickerAdapter adapter : mTextWheelPickerAdapters) {
                    adapter.notifyDataSetChanged();
                }
            } else {
                //当时级联的时候，只需要通知第一个数据变化
                mTextWheelPickerAdapters.get(0).notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data, boolean touch) {
        //默认不联动
        int pickerId = wheelPicker.getId();
        if (touch) {
            mTouch = true;
        }

        mPickedVal.set(pickerId, data);
        mPickedIndex.set(pickerId, index);
        List<D> d = asList(mSrcDataList.get(pickerId));
        D pickedData = (d != null && !d.isEmpty() && d.size() > index) ? d.get(index) : null;
        mPickedData.set(pickerId, pickedData);

        /**
         * 第一次设置数据时(非手势触摸)，不自动级联
         */
        if (mOnCascadeWheelListener != null && mTouch) {
            int size = mSrcDataList.size();
            if (pickerId < size - 1) {
                List<T> cascadeData = mOnCascadeWheelListener.onCascade(pickerId, mPickedIndex);
                if (cascadeData != null && !cascadeData.isEmpty()) {
                    mWheelPickers.get(pickerId + 1).setCurrentItem(0);
                    mTextWheelPickerAdapters.get(pickerId + 1).setData(cascadeData);
                } else {
                    for (int i = pickerId + 1; i < mTextWheelPickerAdapters.size(); i++) {
                        mPickedVal.set(i, null);
                        mPickedIndex.set(i, 0);
                        mPickedData.set(i, null);
                        mTextWheelPickerAdapters.get(i).setData(null);
                    }

                    for (int i = pickerId + 1; i < mWheelPickers.size(); i++) {
                        mWheelPickers.get(i).setCurrentItem(0);
                    }
                }
            }
        }
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setTextSize(textSize);
        }
    }

    public void setTextColor(int textColor) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setTextColor(textColor);
        }
    }

    public void setLineColor(int lineColor) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setLineColor(lineColor);
        }
    }

    public void setLineWidth(int width) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setLineStorkeWidth(width);
        }
    }

    public void setItemSpace(int space) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setItemSpace(space);
        }
    }

    public void setVisibleItemCount(int itemCount) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setVisibleItemCount(itemCount);
        }
    }

    @Override
    public void setItemSize(int itemWidth, int itemHeight) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setItemSize(itemWidth, itemHeight);
        }
    }

    @Override
    public void setShadow(int gravity, float factor) {
        for (TextWheelPicker picker : mWheelPickers) {
            picker.setShadowGravity(gravity);
            picker.setShadowFactor(factor);
        }
    }


    @Override
    public void setScrollAnimFactor(float factor) {
        for (TextWheelPicker picker : mWheelPickers) {
            picker.setFlingAnimFactor(factor);
        }
    }

    @Override
    public void setScrollMoveFactor(float factor) {
        for (TextWheelPicker picker : mWheelPickers) {
            picker.setFingerMoveFactor(factor);
        }
    }

    @Override
    public void setScrollOverOffset(int offset) {
        for (TextWheelPicker picker : mWheelPickers) {
            picker.setOverOffset(offset);
        }
    }

    @Override
    public View asView() {
        return this;
    }

    public void setFakeBoldText(boolean fakeBoldText) {
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.getPaint().setFakeBoldText(fakeBoldText);
        }
    }

    public List<String> getPickedVal() {
        return mPickedVal;
    }

    public List<Integer> getPickedIndex() {
        return mPickedIndex;
    }

    public <T> List<T> getPickedData() {
        return mPickedData;
    }

}