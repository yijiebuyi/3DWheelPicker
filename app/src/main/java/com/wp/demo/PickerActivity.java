package com.wp.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wheelpicker.AdministrativeMap;
import com.wheelpicker.AdministrativeUtil;
import com.wheelpicker.DataPicker;
import com.wheelpicker.DateWheelPicker;
import com.wheelpicker.IDateTimePicker;
import com.wheelpicker.OnCascadeWheelListener;
import com.wheelpicker.OnDataPickListener;
import com.wheelpicker.OnDatePickListener;
import com.wheelpicker.OnMultiDataPickListener;
import com.wheelpicker.PickOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickerActivity extends Activity {
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

        setContentView(R.layout.activity_picker_new);
        mContext = this;

        //选择生日
        findViewById(R.id.picker_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择生日日期")
                        .build();
                DataPicker.pickBirthday(mContext, mInitBirthday, option,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        //选择时间
        findViewById(R.id.picker_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择时间")
                        .setDateWitchVisible(DateWheelPicker.TYPE_ALL)
                        .setAheadYears(100)
                        .setAfterYears(100)
                        .build();
                DataPicker.pickDate(mContext, mInitDate, option, new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitDate.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD_HH_MM_SS), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

        //选择未来时间（年月日时分秒）
        findViewById(R.id.picker_future_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择时间")
                        .setDateWitchVisible(DateWheelPicker.TYPE_ALL)
                        .setAheadYears(0)
                        .setAfterYears(100)
                        .build();
                DataPicker.pickFutureDateTime(mContext, mInitFutureDateTime, option, new OnDatePickListener() {

                            @Override
                            public void onDatePicked(IDateTimePicker picker) {
                                mInitFutureDateTime.setTime(picker.getTime());
                                Toast.makeText(mContext, formatDate(picker.getTime(), TIME_YYYY_MM_DD_HH_MM_SS), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

        //选择未来日期(天，小时，分钟组合)
        findViewById(R.id.picker_future).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择日期")
                        .setDurationDays(100)
                        .build();
                DataPicker.pickFutureDate(PickerActivity.this, new Date(System.currentTimeMillis() + 30 * 60 * 1000),
                        option, new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker picker) {
                                Toast.makeText(mContext, formatDate(picker.getTime(), TIME_YYYY_MM_DD_HH_MM), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择未来日期
        findViewById(R.id.picker_period_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择日期")
                        .setDurationDays(100)
                        .build();
                DataPicker.pickDateTimePeriod(PickerActivity.this, new Date(System.currentTimeMillis() + 30 * 60 * 1000),
                        option, new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker picker) {
                                Toast.makeText(mContext, formatDate(picker.getTime(), TIME_YYYY_MM_DD_HH_MM), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        //选择单个文本
        findViewById(R.id.picker_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择")
                        .setItemTextColor(0XFFFF0000)
                        .setItemLineColor(0xFF00FF00)
                        .setItemTextSize(mContext.getResources().getDimensionPixelSize(com.wheelpicker.R.dimen.font_22px))
                        .setItemSpace(mContext.getResources().getDimensionPixelSize(com.wheelpicker.R.dimen.px36))
                        .build();
                DataPicker.pickData(PickerActivity.this, mInitData, getStudents(1), option, new OnDataPickListener<Student>() {
                    @Override
                    public void onDataPicked(int index, String val, Student data) {
                        mInitData = data;
                        Toast.makeText(PickerActivity.this, val, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //多文本选择
        findViewById(R.id.multi_picker_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<List<?>> stu = new ArrayList<>();
                stu.add(getStudents(0));
                stu.add(getStudents(1));
                stu.add(getStudents(2));

                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("请选择")
                        .setFlingAnimFactor(0.2f)
                        .setVisibleItemCount(9)
                        .setItemLineColor(0xFF0022FF)
                        .build();

                DataPicker.pickData(mContext, mMultiInitIndex, stu, option, new OnMultiDataPickListener<Student>() {

                    @Override
                    public void onDataPicked(List<Integer> indexArr, List<String> val, List<Student> data) {
                        mMultiInitIndex = indexArr;
                        String s = indexArr.toString() + ":" + val.toString();
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //城市选择（级联操作）设置OnCascadeWheelListener即可满足级联
        findViewById(R.id.city_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCity(AdministrativeUtil.PROVINCE_CITY_AREA, mCascadeInitIndex);
            }
        });

        findViewById(R.id.city_picker_no_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCity(AdministrativeUtil.PROVINCE_CITY, mCascadeInitIndexNoArea);
            }
        });
    }


    private void pickCity(int mode, final List<Integer> initIndex) {
        if (mAdministrativeMap == null) {
            mAdministrativeMap = AdministrativeUtil.loadCity(PickerActivity.this);
        }

        PickOption option = getPickDefaultOptionBuilder(mContext)
                .setMiddleTitleText("请选择城市")
                .setFlingAnimFactor(0.4f)
                .setVisibleItemCount(7)
                .setItemTextSize(mContext.getResources().getDimensionPixelSize(com.wheelpicker.R.dimen.font_24px))
                .setItemLineColor(0xFF558800)
                .build();

        DataPicker.pickData(mContext, initIndex,
                AdministrativeUtil.getPickData(mAdministrativeMap, initIndex, mode), option,
                new OnMultiDataPickListener() {
                    @Override
                    public void onDataPicked(List indexArr, List val, List data) {
                        String s = indexArr.toString() + ":" + val.toString();
                        Toast.makeText(PickerActivity.this, s, Toast.LENGTH_SHORT).show();
                        initIndex.clear();
                        initIndex.addAll(indexArr);
                    }
                }, new OnCascadeWheelListener<List<?>>() {

                    @Override
                    public List<?> onCascade(int wheelIndex, List<Integer> itemIndex) {
                        //级联数据
                        if (wheelIndex == 0) {
                            return mAdministrativeMap.provinces.get(itemIndex.get(0)).city;
                        } else if (wheelIndex == 1) {
                            return mAdministrativeMap.provinces.get(itemIndex.get(0)).city.get(itemIndex.get(1)).areas;
                        }

                        return null;
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

    private List<Student> getStudents(int c) {
        List<Student> data = new ArrayList<Student>();
        data.add(new Student("杨过" + c, 22));
        data.add(new Student("张无忌" + c, 24));
        data.add(new Student("小龙女" + c, 16));
        data.add(new Student("猪儿" + c, 18));
        data.add(new Student("周芷若" + c, 16));
        data.add(new Student("令狐冲" + c, 40));
        data.add(new Student("王姑娘" + c, 20));

        return data;
    }

    /**
     * 格式化时间
     *
     * @param date   需要被处理的日期,距离1970的long
     * @param format 最终返回的日期字符串的格式串
     * @return
     */
    public static String formatDate(long date, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private PickOption.Builder getPickDefaultOptionBuilder(Context context) {
        return PickOption.getPickDefaultOptionBuilder(context)
                .setLeftTitleColor(0xFF1233DD)
                .setRightTitleColor(0xFF1233DD)
                .setMiddleTitleColor(0xFF333333)
                .setTitleBackground(0XFFDDDDDD)
                .setLeftTitleText("取消")
                .setRightTitleText("确定");
    }
}
