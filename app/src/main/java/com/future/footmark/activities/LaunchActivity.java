package com.future.footmark.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.future.footmark.R;

/**
 * 启动页
 * Created by Administrator on 2016/4/28.
 */
public class LaunchActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            }
        }).start();
        finish();
    }
}
