package com.future.footmark.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.future.footmark.R;
import com.future.footmark.frame.BaseActivity;

/**
 * Created by Administrator on 2016/5/25.
 */
public class AMapLocationActivity extends BaseActivity implements AMapLocationListener,LocationSource {

    private MapView mMapView=null;
    private AMap aMap=null;
    private OnLocationChangedListener mOnLocationChangedListener=null;

    private AMapLocationClient mLocationClient=null;
    private AMapLocationClientOption mLocationOption=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_amap_location);
        mMapView= (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        if(aMap==null){
            aMap=mMapView.getMap();
            setUpMap();
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        setTitleName("地图定位");
    }

    private void setUpMap(){
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        //1.定位模式，即只第一次定位到地图中心点显示 2.跟随模式，即每次定位结果地图居中显示 3.旋转模式，即每次定位结果地图居中显示，并且定位图标跟随手机方向旋转
        aMap.setMyLocationType(1);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(mOnLocationChangedListener!=null&&aMapLocation!=null){
            if(aMapLocation.getErrorCode()==0){
                mOnLocationChangedListener.onLocationChanged(aMapLocation);
            }else{
                showToast("定位失败");
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener=onLocationChangedListener;
        if(mOnLocationChangedListener!=null){
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
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener=null;
        if(mLocationClient!=null){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if(mLocationClient!=null){
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private void showToast(String content){
        Toast.makeText(AMapLocationActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}
