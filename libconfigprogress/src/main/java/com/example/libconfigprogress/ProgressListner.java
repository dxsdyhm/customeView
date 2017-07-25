package com.example.libconfigprogress;

import android.view.View;

/**
 * Created by USER on 2017/7/14.
 */

public abstract  class ProgressListner<E extends ConfigProgress> implements ProgressListnerIml<E>{
    @Override
    public void onStart(E view) {
        view.start();
    }

    @Override
    public void onPause(E view) {
        view.pause();
    }

    @Override
    public void onClear(E view) {
        view.clear();
    }
}
