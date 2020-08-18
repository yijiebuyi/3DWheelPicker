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

/*
 * Copyright (C) 2017
 * 版权所有
 *
 * 功能描述：多个wheelpicker
 * 作者：yijiebuyi
 * 创建时间：2018/4/20
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class MultipleTextWheelPicker<T extends MultiplePickerData> extends LinearLayout
        implements OnWheelPickedListener<String>, IPickerView {
    protected List<T> mData;
    protected List<TextWheelPicker> mWheelPickers;
    protected List<TextWheelPickerAdapter> mTextWheelPickerAdapters;
    protected List<String> mPickedData;
    protected OnMultiPickListener mOnMultiPickListener;
    private Integer mLineColor;
    private int mTextColor;
    private int mTextSize;
    private boolean mFakeBoldText;

    public MultipleTextWheelPicker(Context context) {
        super(context);
    }

    public MultipleTextWheelPicker(Context context, List<T> data, OnMultiPickListener listener) {
        super(context);
        init(data);
        setOnMultiPickListener(listener);
    }

    public void setData(List<T> data) {
        init(data);
    }

    public void setOnMultiPickListener(OnMultiPickListener listener) {
        mOnMultiPickListener = listener;
    }

    private void init(List<T> data) {
        mData = data;

        //set style
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        //init data
        if (data != null && !data.isEmpty()) {
            int size = data.size();

            int count = 0;
            for (int i = 0; i < size; i++) {
                MultiplePickerData mp = data.get(i);
                if (!mp.placeHoldView) {
                    count++;
                }
            }

            mWheelPickers = new ArrayList<TextWheelPicker>(count);
            mTextWheelPickerAdapters = new ArrayList<TextWheelPickerAdapter>(count);
            mPickedData = new ArrayList<String>(count);

            LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            llParams.weight = 1;

            Context context = getContext();
            int j = 0;
            int k = size;
            for (int i = 0; i < size; i++) {
                MultiplePickerData mp = data.get(i);
                if (mp.placeHoldView) {
                    //占位view
                    TextWheelPicker holdView = new TextWheelPicker(context, k++);
                    holdView.setTouchable(false);
                    holdView.setLineStorkeWidth(0);

                    addView(holdView, llParams);
                } else if (mp.texts != null && !mp.texts.isEmpty()) {
                    int id = j++;
                    TextWheelPicker twp = new TextWheelPicker(context, id);
                    twp.setTouchable(mp.texts.size() != 1);
                    twp.setOnWheelPickedListener(this);

                    twp.setTextColor(mTextColor > 0 ? mTextColor : context.getResources().getColor(R.color.font_black));
                    twp.setVisibleItemCount(7);
                    twp.setTextSize(mTextSize > 0 ? mTextSize : context.getResources().getDimensionPixelSize(R.dimen.font_32px));
                    twp.setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px25));
                    if (mLineColor != null) {
                        twp.setLineColor(mLineColor);
                    }
                    twp.getPaint().setFakeBoldText(mFakeBoldText);
                    addView(twp, llParams);
                    mWheelPickers.add(twp);

                    TextWheelPickerAdapter adapter = new TextWheelPickerAdapter();
                    adapter.setData(mp.texts);
                    mTextWheelPickerAdapters.add(adapter);

                    //set current
                    int index = Math.max(0, mp.texts.indexOf(mp.currentText));
                    twp.setCurrentItemWithoutReLayout(index);
                    mPickedData.add(mp.texts.get(index));

                    twp.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data) {
        //默认不联动
        mPickedData.set(wheelPicker.getId(), data);

        if (mOnMultiPickListener != null) {
            mOnMultiPickListener.onDataPicked(mPickedData);
        }
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }
        mTextSize = textSize;
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setTextSize(textSize);
        }
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        if (mWheelPickers == null) {
            return;
        }

        for (TextWheelPicker picker : mWheelPickers) {
            picker.setTextColor(textColor);
        }
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
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
        mFakeBoldText = fakeBoldText;
        for (TextWheelPicker picker : mWheelPickers) {
            picker.getPaint().setFakeBoldText(fakeBoldText);
        }
    }

    public List<String> getPickedData() {
        return mPickedData;
    }


    public interface OnMultiPickListener {
        public void onDataPicked(List<String> pickedData);

        public void onCancel();
    }
}