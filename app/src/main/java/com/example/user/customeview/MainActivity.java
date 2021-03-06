package com.example.user.customeview;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.libconfigprogress.ConfigProgress;
import com.example.libconfigprogress.ConfigProgressRing;
import com.example.libconfigprogress.ProgressListner;
import com.example.libconfigprogress.Utils;

public class MainActivity extends AppCompatActivity {
    ConfigProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        final ConfigProgressRing ring=new ConfigProgressRing(this, Utils.dip2px(this,66),Utils.dip2px(this,66));
        ring.setProgrssType(ConfigProgressRing.PROGRESSTYPE_MULTI);
        setContentView(ring);
        new CountDownTimer(100 * 1000L, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int progress= (int) (((float)millisUntilFinished/100000)*100);
                //ring.setProgress(progress,0);
            }

            @Override
            public void onFinish() {

            }
        };
//        initUI();
    }

    private void initUI() {
        progress= (ConfigProgress) findViewById(R.id.view_test);
        progress.setProgressListner(new ProgressListner() {

            @Override
            public void onFinish(ConfigProgress configProgress,int mark) {
                if(mark==0){
                    Toast.makeText(configProgress.getContext(),"超市",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(configProgress.getContext(),"成功",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(configProgress.getContext(),"结束",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart(ConfigProgress view) {
                view.setSpeed((float) 0.5);
                super.onStart(view);
                Toast.makeText(view.getContext(),"开始",Toast.LENGTH_LONG).show();
            }
        });
    }

}
