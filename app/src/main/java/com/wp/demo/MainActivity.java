package com.wp.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wheelpicker.AdministrativeMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String TIME_YYYY_MM_DD = "yyyy-MM-dd";

    private Date mInitBirthday = new Date();
    private Date mInitDate = new Date();
    private Date mInitFutureDateTime = new Date();
    private Date mInitFutureDate = new Date();

    private Student mInitData = null;
    private List<Integer> mMultiInitIndex = null;
    private List<Integer> mCascadeInitIndex = new ArrayList<Integer>();
    private List<Integer> mCascadeInitIndexNoArea = new ArrayList<Integer>();

    private AdministrativeMap mAdministrativeMap;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;

        findViewById(R.id.new_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PickerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.old_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OldPickerActivity.class);
                startActivity(intent);
            }
        });
    }

}
