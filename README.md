# 3DWheelPicker
3D效果 数据选择控件

# Demo下载
https://www.pgyer.com/wheelpicker

### 功能
 - 时间选择
 - 单行选择
 - 多个滚轮选择
 - 城市选择（待开发。。。）

## 使用
 - 1.在project的build.gradle添加如下代码
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

 - 2.依赖androidx
```gradle

dependencies {
  implementation 'com.github.yijiebuyi:3DWheelPicker:v1.0.2'
}

```

### 基本用法：
- 使用Datapick
```java
   /**
   * 选择生日
   *
   * @param initDate 初始化选中的日期
   */
   pickBirthday(Context context, @Nullable Date initDate, final OnDatePickListener listener)
   
   /**
     * 获取时间
     *
     * @param context
     * @param initDate 初始化选中的日期
     * @param witchPickVisible 需要显示的pick，@see com.wheelpicker.DateWheelPicker
     * @param aheadYears 距离当前时间，往前显示多少年
     * @param afterYears 距离当前时间，往后显示多少年
     */
    public static void pickDate(Context context, @Nullable Date initDate, int witchPickVisible,
                         int aheadYears, int afterYears, final OnDatePickListener listener)
                         
    /**
     * 获取未来日期
     *
     * @param context
     * @param initDate 初始化选中的日期
     * @param durationDays 距离当前时间往后显示的天数
     */
     public static void pickFutureDate(Context context, @Nullable Date initDate, int durationDays, final OnDatePickListener listener)
     
     /**
     * 获取单行数据
     * @param context
     * @param initData 初始化选中的数据
     * @param srcData 数据源
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData, final OnDataPickListener listener)
    
    /**
     * 多行数据选择
     * @param context
     * @param initData 初始化选中的数据
     * @param srcData 数据源
     */
    public static <T> void pickData(Context context, @Nullable List<T> initData, @NonNull List<List<T>> srcData, final OnMultiDataPickListener listener) 
    
      /**
     * 设置滚轮样式
     * @param pickerView
     * @param option
     */
    private static void setPickViewStyle(IPickerView pickerView, PickOption option) {
        ((View) pickerView).setBackgroundColor(option.getBackgroundColor());
        ((View) pickerView).setPadding(0, option.getVerPadding(), 0, option.getVerPadding());

        pickerView.setTextColor(option.getItemTextColor());
        pickerView.setVisibleItemCount(option.getVisibleItemCount());
        pickerView.setTextSize(option.getItemTextSize());
        pickerView.setItemSpace(option.getItemSpace());
        .....
    }
```



- 也可以使用Textwheelpicker
```java
   参照DateWheelPicker，
```

