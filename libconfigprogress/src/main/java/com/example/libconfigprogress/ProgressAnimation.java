package com.example.libconfigprogress;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by USER on 2017/7/14.
 */

public class ProgressAnimation extends Animation {
    private AnimationProgressListner listner;

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if(listner!=null){
            listner.applyTransformation(interpolatedTime, t);
        }
    }

    public interface AnimationProgressListner{
        void applyTransformation(float interpolatedTime, Transformation t);
    }

    public void setAnimationProgressListner(AnimationProgressListner listner){
        this.listner=listner;
    }
}
