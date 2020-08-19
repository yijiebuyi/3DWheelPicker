package com.wheelpicker;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.widget.IPickerView;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：多个WheelPicker
 * 作者：yijiebuyi
 * 创建时间：2018/4/20
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class MultipleTextWheelPicker extends LinearLayout
        implements OnWheelPickedListener<String>, IPickerView {
    protected List<WheelPickerData> mSrcDataList;
    protected List<TextWheelPicker> mWheelPickers;
    protected List<TextWheelPickerAdapter> mTextWheelPickerAdapters;

    protected List<String> mPickedVal;
    protected List<Integer> mPickedIndex;
    protected List mPickedData;

    public MultipleTextWheelPicker(Context context) {
        super(context);
    }

    public MultipleTextWheelPicker(Context context, List<WheelPickerData> data) {
        super(context);
        init(data);
    }

    public void setData(List<WheelPickerData> data) {
        init(data);
    }

    private void init(List<WheelPickerData> data) {
        mSrcDataList = data;

        //set style
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        //init data
        if (data != null && !data.isEmpty()) {
            int size = data.size();

            int count = 0;
            for (int i = 0; i < size; i++) {
                WheelPickerData mp = data.get(i);
                if (!mp.placeHoldView) {
                    count++;
                }
            }

            mWheelPickers = new ArrayList<TextWheelPicker>(count);
            mTextWheelPickerAdapters = new ArrayList<TextWheelPickerAdapter>(count);
            mPickedVal = new ArrayList<String>(count);
            mPickedIndex = new ArrayList<Integer>(count);
            mPickedData = new ArrayList<>();

            LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            llParams.weight = 1;

            Context context = getContext();
            int j = 0;
            int k = size;
            for (int i = 0; i < size; i++) {
                WheelPickerData mp = data.get(i);
                if (mp.placeHoldView) {
                    //占位view
                    TextWheelPicker holdView = new TextWheelPicker(context, k++);
                    holdView.setTouchable(false);
                    holdView.setLineStorkeWidth(0);

                    addView(holdView, llParams);
                } else if (mp.data != null && !mp.data.isEmpty()) {
                    int id = j++;
                    TextWheelPicker twp = new TextWheelPicker(context, id);
                    twp.setTouchable(mp.scrollable);
                    twp.setOnWheelPickedListener(this);

                    addView(twp, llParams);
                    mWheelPickers.add(twp);

                    TextWheelPickerAdapter adapter = new TextWheelPickerAdapter();
                    adapter.setData(mp.data);
                    mTextWheelPickerAdapters.add(adapter);

                    //set current
                    int index = Math.max(0, mp.indexOf(mp.currentText));
                    twp.setCurrentItemWithoutReLayout(index);
                    mPickedVal.add(mp.getStringVal(index));
                    mPickedIndex.add(index);
                    mPickedData.add(mp.get(index));

                    twp.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data) {
        //默认不联动
        mPickedVal.set(wheelPicker.getId(), data);
        mPickedIndex.set(wheelPicker.getId(), index);
        mPickedData.set(wheelPicker.getId(), mSrcDataList.get(wheelPicker.getId()).get(index));
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