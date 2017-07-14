package com.example.user.customeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.libconfigprogress.ConfigProgress;
import com.example.libconfigprogress.ProgressListner;

public class MainActivity extends AppCompatActivity {
    ConfigProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
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
        progress.setProgress(100,1);
        progress.setProgress(100,0);
    }

}
