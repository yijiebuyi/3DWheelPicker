package com.wheelpicker.widget;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.view.AbstractTextWheelPicker;

/**
 * The wheel picker for text
 */
public class TextWheelPicker extends AbstractTextWheelPicker {
	private final static String TAG = "TextWheelPicker";
	private final static float DEPTH_FACTOR = 0.6F; //0 ~ 1.0F
	private final Camera mCamera = new Camera();
	private final Matrix mRotateMatrix = new Matrix();
	private final Matrix mDepthMatrix = new Matrix();

	private int mRadius;
	
	private int mOldOffsetItemIndex = 0;
	private int mOffsetItemIndex = 0;
	private float mOffsetItemDelta = 0;
	
	private float mShadowOffset = 0;
	private float mLineOffset = 0;
	
	private float mRelRadius;
	
	private OnWheelPickedListener mOnWheelPickedListener;

	public TextWheelPicker(Context context) {
		super(context);
	}
	
	public TextWheelPicker(Context context, int id) {
		super(context);
		setId(id);
	}

	public TextWheelPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void computeWheelSize() {
		mRadius = mWheelPickerImpl.computeRadius(mVisibleItemCount, mItemSpace, mItemMaxWidth, mItemMaxHeight);
		mUnitDegree = (int) (180 * 1.0F / mVisibleItemCount);

		mWheelContentWidth = mWheelPickerImpl.getWheelWidth(mRadius, mItemMaxWidth);
		mWheelContentHeight = mWheelPickerImpl.getWheelHeight(mRadius, mItemMaxHeight);

		mLineOffset = mItemMaxHeight / 2 + mItemSpace * 0.8f;

		mShadowOffset = SHADOW_MAX * mShadowFactor;
		mOverOffset = 90;
        mRelRadius = mRadius * DEPTH_FACTOR;
        
        if (mAdapter != null) {
            mOldOffsetItemIndex = 0;
            
            if (mCurrItemIndex < 0) {
            	mCurrItemIndex = 0;
            }
            
            if (mCurrItemIndex >= mAdapter.getCount()) {
            	mCurrItemIndex = mAdapter.getCount() - 1;
            }
            
        	setScrollRange(-(mAdapter.getCount() - 1 - mCurrItemIndex) * mUnitDegree, mCurrItemIndex * mUnitDegree);
        }
	}

	@Override
	protected void drawBackground(Canvas canvas) {
		if (mAdapter != null && !mAdapter.isEmpty()) {
			canvas.drawLine(0, mWheelCenterY - mLineOffset , mViewWidth, 
					mWheelCenterY - mLineOffset, mLinePaint);
			canvas.drawLine(0, mWheelCenterY + mLineOffset, mViewWidth, 
					mWheelCenterY + mLineOffset, mLinePaint);
		}
	}

	@Override
	protected void drawItems(Canvas canvas) {
		if (mAdapter == null || mAdapter.isEmpty()) {
			return;
		}
		
		for (int i = mItemSartIndex; i <= mItemEndIndex; i++) {
			float rotateDegree = mUnitDegree * (i - mCurrItemIndex)  + mOffsetItemDelta;
			if (rotateDegree > 90 || rotateDegree < -90) {
				continue;
			}

			//为了避免角度太小计算产生误差，所以进行一个校正
			if (Math.abs(rotateDegree)  < 0.1f) {
				if (rotateDegree < 0) {
					rotateDegree = -0.1f;
				} else {
					rotateDegree = 0.1f;
				}
			}
			
			float space = computeSpace(rotateDegree, mRadius);
			float relDegree = Math.abs(rotateDegree) / 90;
			canvas.save();
			mCamera.save();
			mRotateMatrix.reset();
			
			//apply gravity
			if (mShadowGravity == SHADOW_RIGHT) {
				mCamera.translate(-mShadowOffset, 0, 0);
			} else if (mShadowGravity == SHADOW_LEFT) {
				mCamera.translate(mShadowOffset, 0, 0);
			}
			//rotate
			mWheelPickerImpl.rotateCamera(mCamera, rotateDegree);
			mCamera.getMatrix(mRotateMatrix);
			mCamera.restore();
			mWheelPickerImpl.matrixToCenter(mRotateMatrix, space, mWheelCenterX, mWheelCenterY);
			//apply gravity
			if (mShadowGravity == SHADOW_RIGHT) {
				mRotateMatrix.postTranslate(mShadowOffset, 0);
			} else if (mShadowGravity == SHADOW_LEFT) {
				mRotateMatrix.postTranslate(-mShadowOffset, 0);
			}
			
			float depth = computeDepth(rotateDegree, mRelRadius); 
			mCamera.save(); 
			mDepthMatrix.reset();
			mCamera.translate(0, 0, depth); 
			mCamera.getMatrix(mDepthMatrix);
			mCamera.restore();
			mWheelPickerImpl.matrixToCenter(mDepthMatrix, space, mWheelCenterX, mWheelCenterY);
			 
			mRotateMatrix.postConcat(mDepthMatrix);
			canvas.concat(mRotateMatrix);

			if (i == getHighLightItem(mCurrItemIndex)) {
				mPaint.setAlpha(255);
			} else {
				mPaint.setAlpha(128 - (int)(128 * relDegree));
			}
			draw(canvas, mPaint, mAdapter.getItemText(i), space, mWheelCenterX, mWheelCenterTextY);
			canvas.restore();
		}
		
	}

	@Override
	protected void drawForeground(Canvas canvas) {

	}

	public void setOnWheelPickedListener(OnWheelPickedListener listener) {
		mOnWheelPickedListener = listener;
	}

	@Override
	protected void onWheelSelected(int index) {
		if (mAdapter != null && index > -1 && index < mAdapter.getCount()) {
			if (mOnWheelPickedListener != null) {
				mOnWheelPickedListener.onWheelSelected(this, index, mAdapter.getItemText(index));
			}
		} else {
			Log.i(TAG, "error index:"+index);
		}
	}

	@Override
	public void onScrolling(float offsetX, float offsetY, boolean isFinshed) {
		mOffsetItemIndex = (int)(offsetY / mUnitDegree);
		mOffsetItemDelta = offsetY % mUnitDegree;
		if (mOffsetItemIndex != mOldOffsetItemIndex) {
			mCurrItemIndex = mCurrItemIndex - (mOffsetItemIndex - mOldOffsetItemIndex);
		}
		
		mOldOffsetItemIndex = mOffsetItemIndex;
		updateItemIndexRange();
		postInvalidate();
		
		if (isFinshed) {
			correctLocation(mOffsetItemIndex, 0, mOffsetItemDelta);
			if (Math.abs(mOffsetItemDelta) < 0.01f ) {
				onWheelSelected(mCurrItemIndex);
			}
		}
	}

	@Override
	protected void draw(Canvas canvas, Paint paint, String data, float space, float x, float y) {
		canvas.drawText(getDrawText(data), x, y + space, paint);
	}
	
	@Override
	public int getCurrentItem() {
		int currentItem = super.getCurrentItem();
		if (currentItem < 0) {
			return 0;
		}
		
		if (mAdapter == null) {
			return currentItem;
		}
		
		if (currentItem >= mAdapter.getCount()) {
			currentItem = mAdapter.getCount() - 1;
		}
		
		return currentItem;
	}

	/**
	 * 获取高亮显示的item索引
	 * @param item
	 * @return
	 */
	private int getHighLightItem (int item) {
		if (mOffsetItemDelta > 0) {
			if (mOffsetItemDelta > mUnitDegree / 2) {
				return item - 1;
			} else {
				return item;
			}
		} else {
			if (Math.abs(mOffsetItemDelta) > mUnitDegree / 2)
				return item + 1;
			else
				return item;
		}
	}
	
	private String getDrawText(String data) {
		if (data == null) {
			return data;
		}
		
		float itemWidth = mItemMaxWidth;
		int viewWidth = getMeasuredWidth();
		int len = data.length();
		while (itemWidth > viewWidth && len > 0) {
			mPaint.getTextBounds(data, 0, --len, mItemBounds);
			itemWidth = mItemBounds.width();
		}
		
		return data.substring(0, len);
	}

}
