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
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
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

