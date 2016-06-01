package com.future.footmark.tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/4.
 */
public class FormatUtils {

    private static FormatUtils instance;

    public static FormatUtils getInstance(){
        if(instance==null){
            instance=new FormatUtils();
        }
        return instance;
    }

    /**
     * 获得字体宽
     */
    public int getFontWidth(Paint paint, String str) {
        if (str == null || str.equals(""))
            return 0;
        Rect rect = new Rect();
        int length = str.length();
        paint.getTextBounds(str, 0, length, rect);
        return rect.width();
    }

    /**
     * 获得字体高度
     */
    public int getFontHeight(Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds("正", 0, 1, rect);
        return rect.height();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public String getTrim(double value, String rules) {
        DecimalFormat df = new DecimalFormat(rules);
        return df.format(value);
    }

    public String getFormatTime(String currtimeStyle, String time, String timeStyle) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currtimeStyle);
            java.util.Date date = format.parse(time);
            return new SimpleDateFormat(timeStyle, Locale.getDefault()).format(date);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 修剪浮点类型
     * @param value value
     * @param rules 规则(如:0.00保留2位小数)
     * @return string or "" or value
     */
    public String getTrim(String value, String rules)
    {
        if(value == null || value.length() == 0 || rules == null || rules.length() == 0)
        {
            return "";
        }
        try
        {
            return getTrim(Double.parseDouble(value), rules);
        }
        catch(Exception e)
        {
            return value;
        }
    }
}
