package com.wheelpicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

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
public class BottomSheet extends Dialog implements DialogInterface.OnCancelListener,
        DialogInterface.OnDismissListener, View.OnClickListener {

    private View mContentView;// dialog content view
    private String mTitleText;
    private Button mLeftBtn;
    private Button mRightBtn;
    private TextView mTitleTv;
    private View.OnClickListener mLeftBtnClickListener;
    private View.OnClickListener mRightBtnClickListener;

    private OnDialogCloseListener mOnDismissListener;

    private View mTitleLayout;


    public BottomSheet(Context context) {
        super(context, R.style.CommonDialog);
        init();
    }

    public BottomSheet(Context context, int themeResId) {
        super(context, R.style.CommonDialog);
        init();
    }

    protected BottomSheet(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        setContentView(R.layout.bottom_sheet_dialog);
        dialogWindow.setWindowAnimations(R.style.BottomSheetAnim);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.dimAmount = 0.5f;
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(params);
        dialogWindow.setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);

        mTitleLayout = dialogWindow.findViewById(R.id.title);
        mLeftBtn = (Button) dialogWindow.findViewById(R.id.left_btn);
        mRightBtn = (Button) dialogWindow.findViewById(R.id.right_btn);
        mTitleTv = (TextView) dialogWindow.findViewById(R.id.middle_txt);

        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);

        setOnCancelListener(this);
        setOnDismissListener(this);
    }

    public void setTitleVisibility(int visibility) {
        if (mTitleLayout != null) {
            mTitleLayout.setVisibility(visibility);
        }
    }

    public void setContent(View content) {
        ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
    }

    public void setContent(int contentId) {
        View content = getLayoutInflater().inflate(contentId, null);
        ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
    }

    public void setContent(View content, FrameLayout.LayoutParams params) {
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content, params);
        } else {
            ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
        }
    }

    public void setTitleBackground(@ColorInt int color) {
        if (mTitleLayout != null) {
            mTitleLayout.setBackgroundColor(color);
        }
    }

    public void setTitleHeight(int height) {
        if (height <= 0) {
            //throw new IllegalArgumentException("height must > 0");
            return;
        }

        if (mTitleLayout != null) {
            ViewGroup.LayoutParams params = mTitleLayout.getLayoutParams();

            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) params;
            titleParams.height = height;
        }
    }

    public void setMiddleText(String text) {
        mTitleTv.setText(text);
    }

    public void setMiddleTextColor(@ColorInt int color) {
        mTitleTv.setTextColor(color);
    }

    public void setLeftBtnVisibility(int visibility) {
        mLeftBtn.setVisibility(visibility);
    }

    public void setRightBtnVisibility(int visibility) {
        mRightBtn.setVisibility(visibility);
    }

    public void setLeftBtnText(String text) {
        mLeftBtn.setText(text);
    }

    public void setRightBtnText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        mRightBtn.setText(text);
    }

    public void setTitleTextSize(int leftTextSize, int middleTextSize, int rightTextSize) {
        if (leftTextSize > 0) {
            mLeftBtn.setTextScaleX(leftTextSize);
        }

        if (middleTextSize > 0) {
            mTitleTv.setTextSize(middleTextSize);
        }

        if (rightTextSize > 0) {
            mRightBtn.setTextSize(rightTextSize);
        }
    }

    public void setLeftBtnTextColor(@ColorInt int color) {
        mLeftBtn.setTextColor(color);
    }

    public void setRightBtnTextColor(@ColorInt int color) {
        mRightBtn.setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_btn) {
            dismiss();
            if (mLeftBtnClickListener != null) {
                mLeftBtnClickListener.onClick(v);
            }
        } else if (v.getId() == R.id.right_btn) {
            dismiss();
            if (mRightBtnClickListener != null) {
                mRightBtnClickListener.onClick(v);
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onCancel();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
    }

    public void setLeftBtnClickListener(View.OnClickListener listener) {
        mLeftBtnClickListener = listener;
    }

    public void setRightBtnClickListener(View.OnClickListener listener) {
        mRightBtnClickListener = listener;
    }

    public void setOnDismissListener(OnDialogCloseListener listener) {
        mOnDismissListener = listener;
    }

    public interface OnDialogCloseListener {
        public void onCancel();
        public void onDismiss();
    }
}
