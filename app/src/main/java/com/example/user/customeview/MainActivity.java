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
            public void onFinish(ConfigProgress configProgress) {
                Toast.makeText(configProgress.getContext(),"结束",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart(ConfigProgress view) {
                super.onStart(view);
                Toast.makeText(view.getContext(),"onStart",Toast.LENGTH_LONG).show();
            }
        });
    }
}
