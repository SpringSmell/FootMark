package com.future.footmark.tools;

import android.content.Context;
import android.widget.ImageView;

import com.future.footmark.R;


/**
 * Created by Administrator on 2016/5/9.
 */
public class ResourceUtils {

    public static void setImgResource(Context context, ImageView imageView,String prefix, String postfix){
        String resName=prefix+postfix;
        try{
            int resId=context.getResources().getIdentifier(resName,"mipmap",context.getPackageName());
            imageView.setImageResource(resId);
        }catch (Exception ex){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

}
