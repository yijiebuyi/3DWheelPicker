# 3DWheelPicker
3D效果 数据选择控件，源码地址：https://github.com/yijiebuyi/3DWheelPicker
类似效果的开源库也有几个，公司项目也用到类似于时间选择功能，但还达不到产品所要求的，效果并没有那么丝滑。所以自己才实现，下载Demo即可体验。

# Demo下载
APK下载链接1：http://d.firim.pro/3dwheelpicker

#效果图
![3d数据选择效果](https://upload-images.jianshu.io/upload_images/16133165-f3694a3bc5d8cf94.gif?imageMogr2/auto-orient/strip)


### 功能
 - 时间选择（生日模式，时间段模式，未来时间模式）
 - 单数组的数据选择
 - 多数组的数据选择（支持多级联）
 - 省市区级联城市选择（城市数据可能不完整）
 - 可动态设置样式

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
  implementation 'com.github.yijiebuyi:3DWheelPicker:v1.2.0'
}

```

### 基本用法：
#### 使用 DataPicker (可参照demo的用法)


#### 获取单行数据具体使用
- 使用示例
```java
PickOption option = new PickOption.Builder()
                .setVisibleItemCount(9) //设置pickerView有多少个可见的item，必须是单数（1，3，5，7....）
                .setItemSpace(context.getResources().getDimensionPixelOffset(R.dimen.px20)) //设置item的间距
                .setItemTextColor(context.getResources().getColor(R.color.font_black)) //设置item的文本颜色
                .setItemTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_36px)) //设置item的字体大小
                .setVerPadding(context.getResources().getDimensionPixelSize(R.dimen.px20)) //设置item的顶部，底部的padding
                .setShadowGravity(AbstractViewWheelPicker.SHADOW_RIGHT) //设置滚动的偏向
                .setShadowFactor(0.5f) //设置滚轮的偏向因子
                .setFingerMoveFactor(0.8f) //设置手指滑动的阻尼因子
                .setFlingAnimFactor(0.7f) //设置手指快速放开后，滚动动画的阻尼因子
                .setOverScrollOffset(context.getResources().getDimensionPixelSize(R.dimen.px36)) //设置滚轮滑动到底端顶端回滚动画的最大偏移
                .setBackgroundColor(Color.WHITE) //设置滚轮的背景颜色
                .setLeftTitleColor(0xFF1233DD) //设置底部弹出框左边文本的颜色
                .setRightTitleColor(0xFF1233DD) //设置底部弹出框右边文本的颜色
                .setMiddleTitleColor(0xFF333333) //设置底部弹出框中间文本的颜色
                .setTitleBackground(0XFFDDDDDD) //设置底部弹框title栏的背景颜色
                .setLeftTitleText("取消") //设置底部弹出框左边文本
                .setRightTitleText("确定") //设置底部弹出框右边文本
                .setMiddleTitleText("请选择数据") //设置底部弹出框中间
                .setTitleHeight(context.getResources().getDimensionPixelOffset(R.dimen.px80)) //设置底部弹框title高度
                .build();
 DataPicker.pickData(MainActivity.this, mInitData, getStudents(1), option, new OnDataPickListener<Student>() {
     @Override
     public void onDataPicked(int index, String val, Student data) {
         mInitData = data;
         Toast.makeText(MainActivity.this, val, Toast.LENGTH_SHORT).show();
     }
 });
```
- 也可以使用默认的Builder，然后设置自己关注的属性
```java
    PickOption option = PickOption.getPickDefaultOptionBuilder(mContext)
    .setShadowFactor(0.5f) //设置滚轮的偏向因子
    .setFingerMoveFactor(0.8f) //设置手指滑动的阻尼因子
    .setFlingAnimFactor(0.7f) //设置手指快速放开后，滚动动画的阻尼因子
    .build();
 ```

- 时间选择
```java
      /**
     * 获取日期
     *
     * @param context
     * @param initDate 初始化时选择的日期
     * @param mode     获取哪一种数据
     * @param option
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) 

    /**
     * 获取日期 （某个时间段范围）
     *
     * @param context
     * @param initDate
     * @param mode
     * @param from     开始日期
     * @param to       结束日期
     * @param option
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                long from, long to,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
                         
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
     * @param initData 初始化时，显示的数据
     * @param srcData 数据集合
     * @param listener dataPicker数据被选中监听器
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData, 
                                    @Nullable PickOption option, final OnDataPickListener listener
    
   /**
     * 多行数据选择（级联数据）
     * @param context
     * @param initIndex 每个Wheelpicker是初始化时，对应显示的数据index，如果没有特殊需求，传null
     * @param srcData 源数据，是一个二位数组，外层List代表是WheelPicker的集合，内层List代表具体的Wheelpicker对应的数据源
     * @param listener dataPicker数据被选中监听器
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    @Nullable PickOption option, final OnMultiDataPickListener listener)
                                    
   /**
     * 多行数据选择（级联数据）
     * @param context
     * @param initIndex 每个Wheelpicker是初始化时，对应显示的数据index，如果没有特殊需求，传null
     * @param srcData 源数据，是一个二位数组，外层List代表是WheelPicker的集合，内层List代表具体的Wheelpicker对应的数据源
     * @param listener dataPicker数据被选中监听器
     * @param cascadeListener 级联监听器，这里需要自己去实现级联的数据源，可参考demo中城市数据的使用方式
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex, @NonNull List<List<?>> srcData,
                                    @Nullable PickOption option, boolean wrapper,
                                    final OnMultiDataPickListener listener, final OnCascadeWheelListener cascadeListener)
    

```

#### 设置滚轮样式(见DataPicker中的使用方法)
pickerview的样式详情见PickOption里面的属性，包括弹出框的顶部title样式，pickerview的的wheel样式，item样式
```java     
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

```java
/**
     * 获取底部弹出框
     * @param context
     * @param pickerView
     * @return
     */
    private static BottomSheet buildBottomSheet(Context context, @Nullable PickOption option, IPickerView pickerView) {
        BottomSheet bottomSheet = new BottomSheet(context);
        if (option != null) {
            bottomSheet.setLeftBtnText(option.getLeftTitleText());
            bottomSheet.setRightBtnText(option.getRightTitleText());
            bottomSheet.setMiddleText(option.getMiddleTitleText());
            bottomSheet.setLeftBtnTextColor(option.getLeftTitleColor());
            bottomSheet.setRightBtnTextColor(option.getRightTitleColor());
            bottomSheet.setMiddleTextColor(option.getMiddleTitleColor());
            bottomSheet.setTitleBackground(option.getTitleBackground());

            bottomSheet.setTitleHeight(option.getTitleHeight());
        }
        bottomSheet.setContent(pickerView.asView());
        return bottomSheet;
    }
```


#### 级联数据使用案例 （城市选择）
```java
   
        //城市选择（级联操作）设置OnCascadeWheelListener即可满足级联
        findViewById(R.id.city_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCity(AdministrativeUtil.PROVINCE_CITY_AREA, mCascadeInitIndex);
            }
        });

    private void pickCity(int mode, final List<Integer> initIndex) {
        if (mAdministrativeMap == null) {
            mAdministrativeMap = AdministrativeUtil.loadCity(MainActivity.this);
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
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
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
   
```

#### 也可以使用Textwheelpicker

```java
   参照 DateWheelPicker，SingleTextWheelPicker
   
```

#实现方式
继承View，重写 onDraw(Canvas canvas) 方法实现绘制逻辑

核心类：TextWheelPicker，使用它就可以实现你滚轮的效果，其drawItems(Canvas canvas)就是绘制滚轮效果的核心逻辑，使用系统的Camera类，Matrix矩阵变化，实现3d滚轮效果。

###绘制核心代码
```java
    .... 

    float space = computeSpace(rotateDegree, mRadius);
    float relDegree = Math.abs(rotateDegree) / 90;
    canvas.save();
    mCamera.save();
    mRotateMatrix.reset();

    //旋转矩阵变换
    if (mShadowGravity == SHADOW_RIGHT) {
        mCamera.translate(-mShadowOffset, 0, 0);
    } else if (mShadowGravity == SHADOW_LEFT) {
        mCamera.translate(mShadowOffset, 0, 0);
    }
    //旋转
    mWheelPickerImpl.rotateCamera(mCamera, rotateDegree);
    mCamera.getMatrix(mRotateMatrix);
    mCamera.restore();
    mWheelPickerImpl.matrixToCenter(mRotateMatrix, space, mWheelCenterX, mWheelCenterY);
    if (mShadowGravity == SHADOW_RIGHT) {
        mRotateMatrix.postTranslate(mShadowOffset, 0);
    } else if (mShadowGravity == SHADOW_LEFT) {
        mRotateMatrix.postTranslate(-mShadowOffset, 0);
    }
    //偏移矩阵变换
    float depth = computeDepth(rotateDegree, mRelRadius);
    mCamera.save();
    mDepthMatrix.reset();
    mCamera.translate(0, 0, depth);
    mCamera.getMatrix(mDepthMatrix);
    mCamera.restore();
    mWheelPickerImpl.matrixToCenter(mDepthMatrix, space, mWheelCenterX, mWheelCenterY);

    mRotateMatrix.postConcat(mDepthMatrix);
    canvas.concat(mRotateMatrix);

    //绘制文本
    .......
```

相关关联类：ScrollWheelPicker ；AbstractWheelPicker

#原理
![滚轮计算原理图（图片来源网络，见文末参考引用连接）](https://upload-images.jianshu.io/upload_images/16133165-76b61bcccc57bfb3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



####参考文献
滚轮计算原理图：[https://blog.csdn.net/qq_22393017/article/details/59488906](https://blog.csdn.net/qq_22393017/article/details/59488906)
