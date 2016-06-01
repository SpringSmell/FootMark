package com.future.footmark.frame;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2016/4/28.
 */
public class MainApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        SDKInitializer.initialize(getApplicationContext());
    }

    public static Context getContext(){
        return mContext;
    }

}
