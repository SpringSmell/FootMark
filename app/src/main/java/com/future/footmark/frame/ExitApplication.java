package com.future.footmark.frame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chris on 2015/12/13.
 */
public class ExitApplication extends Application {

    private final String Tag = "ExitApplication";
    private List<Activity> activityList = new LinkedList<>();
    private static ExitApplication instance;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static ExitApplication newInstance() {
        if (instance == null) {
            instance = new ExitApplication();
        }
        return instance;
    }

    public static Context getContext(){
        return mContext;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            this.activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if(activity!=null){
            for(Activity mActivity:this.activityList){
                if(mActivity==activity) {
                    this.activityList.remove(activity);
                    break;
                }
            }
        }
    }

    public void exit() {
        try {
            for (Activity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
