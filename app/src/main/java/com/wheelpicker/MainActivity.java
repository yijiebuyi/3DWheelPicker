package com.wheelpicker;

import java.util.ArrayList;
import java.util.List;

import com.wheelpicker.view.DateWheelPicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		List<String> datas = new ArrayList<String>();
		for (int i = 2; i < 100; i++) {
			datas.add(String.valueOf(i+"年"));
		}

//		TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(datas);
//		TextWheelPicker picker = new TextWheelPicker(this);
//		picker.setTextSize(65);
//		picker.setItemSpace(20);
//		picker.setVisibileItemCount(7);
//		picker.setLineColor(Color.parseColor("#e5e5e5"));
//		picker.setLineStorkeWidth(4);
//		picker.setAdapter(adapter);
//		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		setContentView(picker, params);
		
		DateWheelPicker dwp = new DateWheelPicker(this);
		dwp.setCurrentDate(2000, 2, 20);
		dwp.setTextSize(60);
		dwp.setItemSpace(16);
		dwp.setVisibleItemCount(7);
		dwp.setLineWidth(3);
		dwp.setLineColor(Color.LTGRAY);
		dwp.setDateRange(1917, 2016);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		setContentView(dwp, params);
		//dwp.hideWheelPicker(DateWheelPicker.TYPE_YEAR | DateWheelPicker.TYPE_DAY);
		
	}

}
