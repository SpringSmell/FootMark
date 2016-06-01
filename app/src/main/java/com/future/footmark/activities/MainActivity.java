package com.future.footmark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.future.footmark.R;
import com.future.footmark.frame.BaseActivity;
import com.future.footmark.tools.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    private TextView content,sha1;
    private Button btnBaiduTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();

        // 设置定位模式为高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void initView() {
        content= (TextView) findViewById(R.id.tvContent);
        sha1= (TextView) findViewById(R.id.sha1);
        btnBaiduTrack= (Button) findViewById(R.id.btnBaiduTrack);
        btnBaiduTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BaiduTrackActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        sha1.setText(CommonUtils.sHA1(this));
    }

    @Override
    public void initTitle() {
        setRightView("定位地图", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AMapLocationActivity.class));
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                String contentStr="";
                //定位成功回调信息，设置相关消息
                contentStr+="定位成功回调信息:";
                contentStr+="\n当前定位结果来源:"+aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                contentStr+="\n纬度:"+aMapLocation.getLatitude();//获取纬度
                contentStr+="\n经度:"+aMapLocation.getLongitude();//获取经度
                contentStr+="\n精度信息:"+aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                contentStr+="\n时间:"+df.format(date);//定位时间
                contentStr+="\n地址:"+aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                contentStr+="\n国家信息:"+aMapLocation.getCountry();//国家信息
                contentStr+="\n省信息:"+aMapLocation.getProvince();//省信息
                contentStr+="\n城市信息:"+aMapLocation.getCity();//城市信息
                contentStr+="\n城区信息:"+aMapLocation.getDistrict();//城区信息
                contentStr+="\n街道信息:"+aMapLocation.getStreet();//街道信息
                contentStr+="\n街道门牌号信息:"+aMapLocation.getStreetNum();//街道门牌号信息
                contentStr+="\n城市编码:"+aMapLocation.getCityCode();//城市编码
                contentStr+="\n地区编码:"+aMapLocation.getAdCode();//地区编码
                contentStr+="\n当前定位点的AOI信息:"+aMapLocation.getAoiName();//获取当前定位点的AOI信息
                content.setText(contentStr);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
