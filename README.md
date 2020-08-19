# 3DWheelPicker
3D效果 数据选择控件

# Demo下载
https://www.pgyer.com/wheelpicker

### 功能
 - 时间选择
 - 单数组的数据选择
 - 多数组的数据选择（支持多级联）
 - 省市区级联城市选择（城市数据可能不完整）

## 使用
 - 在project的build.gradle添加如下代码
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

 - 引用
```gradle

dependencies {
  implementation 'com.github.yijiebuyi:3DWheelPicker:v1.0.3'
}

```

### 基本用法：
#### 使用 DataPicker
- 时间选择
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
     public static void pickFutureDate(Context context, @Nullable Date initDate, int durationDays, 
                          final OnDatePickListener listener)
  
 ```    
  - 数据选择: 
  为了保证picker控件显示的数据是期望的字符串，需要对数组中的类（String数组除外）实现PickString，或者重写toString
  ```java
     /**
      * 功能描述：当没有实现PickString，picker控件上显示是toString()的内容
      */
     class Student implements PickString {
        public String name;
        public int age;

        public Student(String n, int a) {
            name = n;
            age = a;
        }

        @NonNull
        @Override
        public String toString() {
            return age + "岁";
        }

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 获取单行数据
     * @param context
     * @param initData 初始化选中的数据
     * @param srcData 数据源
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData, 
                             final OnDataPickListener listener)
    
    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    final OnMultiDataPickListener listener)
                                    
     /**
     * 多行数据选择
     * @param context
     * @param initIndex
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData, boolean wrapper,
                                    final OnMultiDataPickListener listener, final OnCascadeWheelListener cascadeListener)
    
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



### 也可以使用Textwheelpicker

```java
   参照 DateWheelPicker，SingleTextWheelPicker
   
```

