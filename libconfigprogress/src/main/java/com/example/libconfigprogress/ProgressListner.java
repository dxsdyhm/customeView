package com.example.libconfigprogress;

import android.view.View;

/**
 * Created by USER on 2017/7/14.
 */

public abstract class ProgressListner implements ProgressListnerIml{
    @Override
    public void onStart(ConfigProgress view) {
        view.start();
    }

    @Override
    public void onPause(ConfigProgress view) {
        view.pause();
    }

    @Override
    public void onClear(ConfigProgress view) {
        view.clear();
    }
}
