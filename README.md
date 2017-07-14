## 集成

* add some plugins in project  build.gradle like this
```gradle
    allprojects {
	repositories {
	...
	maven { url 'https://jitpack.io' }
	}
    }
```

* in your app build.gradle
```gradle
    dependencies {
        compile 'com.github.dxsdyhm:customeView:1.0'
    }
```


## 使用
```xml
<com.example.libconfigprogress.ConfigProgress
       android:id="@+id/view_test"
       android:layout_width="63dp"
       android:layout_height="63dp"
       android:layout_centerInParent="true"/>
```

## 效果图
[](https://github.com/dxsdyhm/customeView/blob/master/device-2017-07-14-153953.png)
