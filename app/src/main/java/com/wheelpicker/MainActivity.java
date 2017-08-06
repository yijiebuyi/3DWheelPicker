package com.wheelpicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

                DataPicker.pickBirthday(MainActivity.this, new Date(System.currentTimeMillis()), new DataPicker.OnBirthdayPickListener() {
                    @Override
                    public void onBirthPicked(int year, int month, int day) {
                        Toast.makeText(MainActivity.this, year + "-" + (month + 1) + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.picker_future).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataPicker.pickFutureDate(MainActivity.this, new Date(System.currentTimeMillis() + 30 * 60 * 1000), new DataPicker.OnDatePickListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day, int hour, int minute, int second) {
                        Toast.makeText(MainActivity.this, year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.picker_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataPicker.pickData(MainActivity.this, getTextList(), new DataPicker.OnDataPickListener() {
                    @Override
                    public void onDataPicked(Object data) {
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

        return data;
    }
}
