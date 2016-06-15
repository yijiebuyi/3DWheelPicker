package com.wheelpicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.wheelpicker.core.AbstractWheelPicker;
import com.wheelpicker.core.OnWheelPickedListener;
import com.wheelpicker.widget.TextWheelPicker;
import com.wheelpicker.widget.TextWheelPickerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateWheelPicker extends LinearLayout implements OnWheelPickedListener{
	public final static int TYPE_YEAR = 1 << 1;
	public final static int TYPE_MONTH = 1 << 2;
	public final static int TYPE_DAY = 1 << 3;

	private static String mYearStr = "年";
	private static String mMontyStr = "月";
	private static String mDayStr = "日";

	/** 生日模式，时间上限为当前时间 */
	public final static int MODE_BIRTHDAY = 1;
	/** 正常模式 */
	public final static int MODE_RANGE = 2;

	public final static int BIRTHDAY_RANGE = 100;

	private TextWheelPicker mYearWheelPicker;
	private TextWheelPicker mMonthWheelPicker;
	private TextWheelPicker mDayWheelPicker;

	private int mCurrYear;
	private int mCurrMonth;
	private int mCurrDay;
	private int mMode = MODE_BIRTHDAY;

	private int mSelectedYear;
	private int mSelectedMonth;
	private int mSelectedDay;

	private List<String> mYears;
	private List<String> mMonths;
	private List<String> mDays;

	private TextWheelPickerAdapter mYearPickerAdapter;
	private TextWheelPickerAdapter mMonthPickerAdapter;
	private TextWheelPickerAdapter mDayPickerAdapter;

	public DateWheelPicker(Context context) {
		super(context);
		init();
	}

	public DateWheelPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DateWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setGravity(Gravity.CENTER);
		setOrientation(HORIZONTAL);

		LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llParams.weight = 1;

		mYearWheelPicker = new TextWheelPicker(getContext(), TYPE_YEAR);
		mMonthWheelPicker = new TextWheelPicker(getContext(), TYPE_MONTH);
		mDayWheelPicker = new TextWheelPicker(getContext(), TYPE_DAY);

		mYearWheelPicker.setOnWheelPickedListener(this);
		mMonthWheelPicker.setOnWheelPickedListener(this);
		mDayWheelPicker.setOnWheelPickedListener(this);

		addView(mYearWheelPicker, llParams);
		addView(mMonthWheelPicker, llParams);
		addView(mDayWheelPicker, llParams);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		mCurrYear = calendar.get(Calendar.YEAR);
		mCurrMonth = calendar.get(Calendar.MONTH) + 1;
		mCurrDay = calendar.get(Calendar.DATE);

		initData();
	}

	private void initData() {
		mYearPickerAdapter = new TextWheelPickerAdapter();
		mMonthPickerAdapter = new TextWheelPickerAdapter();
		mDayPickerAdapter = new TextWheelPickerAdapter();

		mYears = new ArrayList<String>();
		mMonths = new ArrayList<String>();
		mDays = new ArrayList<String>();

		updateYears(mCurrYear - BIRTHDAY_RANGE + 1, mCurrYear);
		updateMonths(12);
		updateDays(31);

		mYearPickerAdapter.setDatas(mYears);
		mMonthPickerAdapter.setDatas(mMonths);
		mDayPickerAdapter.setDatas(mDays);

		mYearWheelPicker.setAdapter(mYearPickerAdapter);
		mMonthWheelPicker.setAdapter(mMonthPickerAdapter);
		mDayWheelPicker.setAdapter(mDayPickerAdapter);
	}

	public void setWheelPickerVisibility(int wheelType, int visibility) {
		if ((wheelType & TYPE_YEAR) != 0) {
			mYearWheelPicker.setVisibility(visibility);
		}

		if ((wheelType & TYPE_MONTH) != 0) {
			mMonthWheelPicker.setVisibility(visibility);
		}

		if ((wheelType & TYPE_DAY) != 0) {
			mDayWheelPicker.setVisibility(visibility);
		}
	}

	public void setDateRange(int from, int to) {
		if (from >= to) {
			throw new IllegalArgumentException("the from year less than to year!");
		}

		if (from < 0 || to < 0) {
			throw new IllegalArgumentException("the passed year must be > 0");
		}

		mMode = to == mCurrYear ? MODE_BIRTHDAY : MODE_RANGE;

		updateYears(from, to);
		setCurrentDate(mSelectedYear, mSelectedMonth, mSelectedDay);
		mYearPickerAdapter.setDatas(mYears);
	}

	public void setCurrentDate(int year, int month, int day) {
		if (mYears.isEmpty() || mMonths.isEmpty() || mDays.isEmpty()) {
			return;
		}

		mSelectedYear = year;
		mSelectedMonth = month;
		mSelectedDay = day;

		int yearIndex = Math.max(0, mYears.indexOf(year+"年"));
		int monthIndex = Math.max(0, mMonths.indexOf(month+"月"));
		int dayIndex = Math.max(0, mDays.indexOf(day+"日"));

		setItemIndex(yearIndex, monthIndex, dayIndex);

		if (mSelectedMonth == 2) {
            if (isLeapYear(mSelectedYear)) {
                updateDays(29);
            } else {
                updateDays(28);
            }
        } else {
            switch (mSelectedMonth) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    updateDays(31);
                    break;
                default:
                    updateDays(30);
                    break;
            }
        }
    }

	private void setItemIndex(int yearIndex, int monthIndex, int dayIndex) {
		mYearWheelPicker.setCurrentItem(yearIndex);
		mMonthWheelPicker.setCurrentItem(monthIndex);
		mDayWheelPicker.setCurrentItem(dayIndex);
	}

	public void setTextSize(int textSzie) {
		if (textSzie < 0) {
			return;
		}

		mYearWheelPicker.setTextSize(textSzie);
		mMonthWheelPicker.setTextSize(textSzie);
		mDayWheelPicker.setTextSize(textSzie);
	}

	public void setTextColor(int textColor) {
		mYearWheelPicker.setTextColor(textColor);
		mMonthWheelPicker.setTextColor(textColor);
		mDayWheelPicker.setTextColor(textColor);
	}

	public void setLineColor(int lineColor) {
		mYearWheelPicker.setLineColor(lineColor);
		mMonthWheelPicker.setLineColor(lineColor);
		mDayWheelPicker.setLineColor(lineColor);
	}

	public void setLineWidth(int width) {
		mYearWheelPicker.setLineStorkeWidth(width);
		mMonthWheelPicker.setLineStorkeWidth(width);
		mDayWheelPicker.setLineStorkeWidth(width);
	}

	public void setItemSpace(int space) {
		mYearWheelPicker.setItemSpace(space);
		mMonthWheelPicker.setItemSpace(space);
		mDayWheelPicker.setItemSpace(space);
	}

	public void setVisibleItemCount(int itemCount) {
		mYearWheelPicker.setVisibleItemCount(itemCount);
		mMonthWheelPicker.setVisibleItemCount(itemCount);
		mDayWheelPicker.setVisibleItemCount(itemCount);
	}

	public void setItemSize(int itemWidth, int itemHeight) {
		mYearWheelPicker.setItemSize(itemWidth, itemHeight);
		mMonthWheelPicker.setItemSize(itemWidth, itemHeight);
		mDayWheelPicker.setItemSize(itemWidth, itemHeight);
	}

	public int getDateMode() {
		return mMode;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, Object data) {
		switch (wheelPicker.getId()) {
		case TYPE_YEAR:
		    int year = getCurrentDate(data, mYearStr);
            if (year > 0) {
                mSelectedYear = year;
            }


            boolean changed = false;
			if (index == mYears.size() - 1 && mSelectedYear == mCurrYear) {
				//current year
				updateMonths(mCurrMonth);
				changed = true;
			} else {
				changed = mMonths.size() != 12;
				if (changed) {
					updateMonths(12);
				}
			}

			if (changed) {
				mMonthPickerAdapter.setDatas(mMonths);
			}

			break;
		case TYPE_MONTH:
		    int month = getCurrentDate(data, mMontyStr);
            if (month > 0) {
                mSelectedMonth = month;
            }
			if (index == mMonths.size() - 1 && mSelectedYear == mCurrYear) {
				//current month
				updateDays(mCurrDay);
			} else {
				if (month == 2) {
                    if (isLeapYear(mSelectedYear)) {
                        updateDays(29);
                    } else {
                        updateDays(28);
                    }
                } else {
                    switch (month) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            updateDays(31);
                            break;
                        default:
                            updateDays(30);
                            break;
                    }
                }
			}
			mDayPickerAdapter.setDatas(mDays);
			break;
		case TYPE_DAY:

			break;
		default:
			break;
		}
	}


	private void updateYears(int from, int to) {
		mYears.clear();

		int size = to - from;
		for (int i = from; i <= from + size; i++) {
			mYears.add(i + mYearStr);
		}
	}

	private void updateMonths(int maxMonth) {
		mMonths.clear();

		for (int i = 1; i <= maxMonth; i++) {
			mMonths.add(i + mMontyStr);
		}
	}

	private void updateDays(int maxDay) {
		mDays.clear();

		for (int i = 1; i <= maxDay; i++) {
			mDays.add(i + mDayStr);
		}
	}

	private int getCurrentDate(Object data, String suffix) {
	    if (data instanceof String) {
	    	int suffixLeg = suffix == null ? 0 : suffix.length();
	        String temp = (String)data;
	        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));
	    }

	    return -1;
	}

	private boolean isLeapYear(int year) {
	    if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
	       return true;
	    }

	    return false;
	}
}
