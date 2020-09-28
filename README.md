# 3DWheelPicker
3D效果 数据选择控件，赶紧点击下面链接下载体验

# Demo下载
http://d.firim.pro/3dwheelpicker

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
  implementation 'com.github.yijiebuyi:3DWheelPicker:v1.0.5'
}

```

### 基本用法：
#### 使用 DataPicker (可参照demo的用法)
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
    

```

#### 设置滚轮样式
```java
   final SingleTextWheelPicker picker = new SingleTextWheelPicker(context);
   PickOption option = PickOption.getPickDefaultOptionBuilder(context)
                .setItemTextColor(0XFFFF0000)
                .setItemLineColor(0xFF00FF00)
                .setItemTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_22px))
                .setItemS
   setPickViewStyle(picker, option);
        
    /**
     * 设置滚轮样式
     * @param pickerView
     * @param option
     */
    private static void setPickViewStyle(IPickerView pickerView, PickOption option) {
        //设置view样式
        pickerView.asView().setBackgroundColor(option.getBackgroundColor());
        pickerView.asView().setPadding(0, option.getVerPadding(), 0, option.getVerPadding());

        //设置Item样式
        pickerView.setTextColor(option.getItemTextColor()); //设置item的文本颜色
        pickerView.setVisibleItemCount(option.getVisibleItemCount()); //设置可见item的数量，必须是奇数： 如1,3,5,7,9...
        pickerView.setTextSize(option.getItemTextSize());//设置item的文本字体大小
        pickerView.setItemSpace(option.getItemSpace());//设置item的间距
        pickerView.setLineColor(option.getItemLineColor());//设置item的分割线的颜色
        pickerView.setLineWidth(option.getItemLineWidth());//设置item分割线的宽度

        //设置滚轮效果
        pickerView.setShadow(option.getShadowGravity(), option.getShadowFactor()); //设置滚轮偏向，偏向因子（偏向因子取值[0,1]）
        pickerView.setScrollMoveFactor(option.getFingerMoveFactor()); //设置手指移动是item跟随滚动灵敏度（取值(0,1]）
        pickerView.setScrollAnimFactor(option.getFlingAnimFactor()); //设置滚动动画阻尼因子（取值(0,1]）
        pickerView.setScrollOverOffset(option.getOverScrollOffset()); //设置滚轮滑动到顶端或底端的最大回弹的偏移量
    }
```

#### 也可以使用Textwheelpicker

```java
   参照 DateWheelPicker，SingleTextWheelPicker
   
```
