package com.example.libconfigprogress;

/**
 * Created by USER on 2017/7/14.
 */

import android.view.View;

public interface ProgressListnerIml<T extends ConfigProgress> {
    void onStart(T t);
    void onPause(T t);
    void onFinish(T t,int mark);
    void onClear(T t);
}
