
# PhotoPicker

一款图片选择器，支持直接拍照并裁剪，单选裁剪，图片多选，裁剪比例设置等

---

# Example

<image src="https://github.com/q805699513/PhotoPicker/blob/master/image/paizhao.gif?raw=true" width="280px"/>  <image src="https://github.com/q805699513/PhotoPicker/blob/master/image/danxuancaijian.gif?raw=true" width="280px"/>  

<image src="https://github.com/q805699513/PhotoPicker/blob/master/image/duoxuan.gif?raw=true" width="280px"/>  <image src="https://github.com/q805699513/PhotoPicker/blob/master/image/wangtu.gif?raw=true" width="280px"/>

---

# Usage

### Gradle

```groovy
dependencies {
    compile 'com.longsh:PhotoPicker:7.1.0'
    
    compile 'com.android.support:design:25.1.0'
    compile 'com.android.support:recyclerview-v7:25.1.0'
    compile "com.nineoldandroids:library:2.4.0"
    compile "com.github.bumptech.glide:glide:3.7.0"
    compile 'com.yalantis:ucrop:2.2.0'
}
```
* ```appcompat-v7```version >= 23.0.0

### eclipse
[![GO HOME](http://ww4.sinaimg.cn/large/5e9a81dbgw1eu90m08v86j20dw09a3yu.jpg)

### 拍照并裁剪
```java
PhotoPicker.builder()
    //直接拍照
    .setOpenCamera(true)
    //拍照后裁剪
    .setCrop(true)
    //设置裁剪比例(X,Y)
    //.setCropXY(1, 1)
    .start(MainActivity.this);
```

### 单选并裁剪
```java
 PhotoPicker.builder()
     //设置图片选择数量
     .setPhotoCount(1)
     //取消选择时点击图片浏览
     .setPreviewEnabled(false)
     //开启裁剪
     .setCrop(true)
     //设置裁剪比例(X,Y)
     .setCropXY(1, 1)
     //设置裁剪界面标题栏颜色，设置裁剪界面状态栏颜色
     .setCropColors(R.color.colorPrimary, R.color.colorPrimaryDark)
     .start(MainActivity.this);
```

### 图片多选
```java
 private ArrayList<String> selectedPhotos = new ArrayList<>();
 PhotoPicker.builder()
        //设置选择个数
        .setPhotoCount(9)
        //选择界面第一个显示拍照按钮
        .setShowCamera(true)
        //选择时点击图片放大浏览
        .setPreviewEnabled(true)
        //附带已经选中过的图片
        .setSelected(selectedPhotos)
        .start(MainActivity.this);           
```

### 大图浏览
```java
  //多选选中图片返回后点击大图浏览界面删除
   PhotoPreview.builder()
           //附带已经选中过的图片
          .setPhotos(selectedPhotos)
          //设置要浏览图片的第position张
          .setCurrentItem(position)
          .start(MainActivity.this);
  //对selectedPhotos进行操作可以达到选择了的图片进行删除。
  
  
  
 //图片浏览
 ArrayList<String> imgData = new ArrayList<>();
 PhotoPreview.builder()
        //设置浏览的图片数据
        .setPhotos(imgData)
        //设置点击后浏览的是第几张图
        .setCurrentItem(position)
        //浏览时不要标题栏  
        //setShowDeleteButton浏览时显示删除按钮.
        .setShowToolbar(false)
        //开启浏览时长按后显示PopuWindow,分享、保存、取消 等，可以自定义。
        .setOnLongClickListData(onLongClickListData)
        .start(PreViewImgActivity.this);   
 ```
        
### 长按后显示PopuWindow
[参考类](https://github.com/q805699513/PhotoPicker/blob/master/photopickerdemo/src/main/java/me/iwf/PhotoPickerDemo/PreViewImgActivity.java)
## 
```java
        onLongClickListData.add("分享");
        onLongClickListData.add("保存");
        onLongClickListData.add("取消");
        //图片长按后的item点击事件回调
        PhotoOnLongClickManager photoOnLongClickManager = PhotoOnLongClickManager.getInstance();
        photoOnLongClickManager.setOnLongClickListener(new PhotoOnLongClick() {
            @Override
            public void sendOnLongClick(int position, String path) {
                Toast.makeText(PreViewImgActivity.this, "你点击了：" + onLongClickListData.get(position) + "，图片路径：" + path, Toast.LENGTH_LONG).show();
            }
        });
      
 ```  
### 图片返回
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            iv_crop.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
        //拍照功能或者裁剪功能返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            iv_crop.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            Glide.with(getApplicationContext()).load(Uri.fromFile(new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH)))).into(iv_crop);
        }
    }
```

### manifest
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    >
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA" />
  <application
    ...
    >
    ...
   
       <activity
            android:name="me.iwf.photopicker.PhotoPickerActivity"
            android:theme="@style/customTheme" />

        <activity
            android:name="me.iwf.photopicker.PhotoPagerActivity"
            android:theme="@style/Theme.AppCompat.NoActionBar" />

        <activity
            android:name="com.yalantis.ucrop.UCropActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar" />
    
  </application>
</manifest>
```
### Custom style
```xml
    <style name="actionBarTheme" parent="ThemeOverlay.AppCompat.Dark.ActionBar">
        <item name="android:textColorPrimary">#fff</item>
        <item name="actionBarSize">56dip</item>
    </style>

    <style name="customTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="titleTextColor">#ffffff</item>
        <item name="actionBarTheme">@style/actionBarTheme</item>
        <item name="colorPrimary">#38393E</item>
        <item name="actionBarSize">56dip</item>
        <item name="colorPrimaryDark">#2F3034</item>
    </style>
```

### Proguard

```
# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }
# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
# ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
```

---

## Thanks
* [uCrop](https://github.com/Yalantis/uCrop)
* [Glide](https://github.com/bumptech/glide)
* [donglua](https://github.com/donglua/PhotoPicker)

## License
```text

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



