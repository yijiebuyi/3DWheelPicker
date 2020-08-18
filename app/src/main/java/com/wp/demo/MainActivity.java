package com.wp.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wheelpicker.DateWheelPicker;
import com.wheelpicker.OnDataPickListener;
import com.wheelpicker.OnDatePickListener;
import com.wheelpicker.Picker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //选择生日
        findViewById(R.id.picker_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picker.pickBirthday(MainActivity.this, new Date(System.currentTimeMillis()),
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(long time, int year, int month, int day, int hour, int minute, int second) {
                                Toast.makeText(MainActivity.this, year + "-" + (month + 1) + "-" + day, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.picker_future_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picker.pickDate(MainActivity.this, new Date(System.currentTimeMillis()),
                        DateWheelPicker.TYPE_ALL, 0, 100, new OnDatePickListener() {
                            @Override
                            public void onDatePicked(long time, int year, int month, int day, int hour, int minute, int second) {
                                Toast.makeText(MainActivity.this, year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.picker_future).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picker.pickFutureDate(MainActivity.this, new Date(System.currentTimeMillis() + 30 * 60 * 1000),
                        365, new OnDatePickListener() {
                            @Override
                            public void onDatePicked(long time, int year, int month, int day, int hour, int minute, int second) {
                                Toast.makeText(MainActivity.this, year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.picker_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picker.pickData(MainActivity.this, "", getTextList(), new OnDataPickListener() {
                    @Override
                    public void onDataPicked(int index, String val, Object data) {
                        Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private List<String> getTextList() {
        List<String> data = new ArrayList<>();
        data.add("杨过");
        data.add("张无忌");
        data.add("郭靖");
        data.add("乔峰");
        data.add("令狐冲");
        data.add("赵敏");
        data.add("东方不败");
        data.add("小龙女");
        data.add("黄蓉");
        data.add("阿朱");
        data.add("王菇凉");

        data.add("杨过2");
        data.add("张无忌2");
        data.add("郭靖2");
        data.add("乔峰2");
        data.add("令狐冲2");
        data.add("赵敏2");
        data.add("东方不败2");
        data.add("小龙女2");
        data.add("黄蓉2");
        data.add("阿朱2");
        data.add("王菇凉2");

        data.add("杨过3");
        data.add("张无忌3");
        data.add("郭靖3");
        data.add("乔峰3");
        data.add("令狐冲3");
        data.add("赵敏3");
        data.add("东方不败3");
        data.add("小龙女3");
        data.add("黄蓉3");
        data.add("阿朱3");
        data.add("王菇凉3");

        return data;
    }
}
