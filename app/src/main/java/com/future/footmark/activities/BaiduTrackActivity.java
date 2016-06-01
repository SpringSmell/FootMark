package com.future.footmark.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.OnTrackListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.future.footmark.R;
import com.future.footmark.config.ConfigThirdParty;
import com.future.footmark.frame.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/25.
 */
public class BaiduTrackActivity extends BaseActivity implements View.OnClickListener, OnStartTraceListener, OnStopTraceListener {

    private Button btnStart, btnStop;
    private LBSTraceClient mTraceClient = null;
    private Trace mTrace = null;

    private MapView mMapView=null;
    private BaiduMap mBaiduMap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_baidu_track);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        initTrack();
    }

    @Override
    public void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        mMapView= (MapView) findViewById(R.id.baiduMap);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {

    }


    private void initTrack() {
        //实例化轨迹服务客户端
        mTraceClient = new LBSTraceClient(getApplicationContext());
        //鹰眼服务ID
        long serviceId = ConfigThirdParty.baiduTrackServiceId;
        //entity标识
        String entityName = getImei(this);
        //轨迹服务类型（0 : 不上传位置数据，也不接收报警信息； 1 : 不上传位置数据，但接收报警信息；2 : 上传位置数据，且接收报警信息）
        int traceType = 2;
        //实例化轨迹服务
        mTrace = new Trace(getApplicationContext(), serviceId, entityName, traceType);
        //位置采集周期
        int gatherInterval = 10;
        //打包周期
        int packInterval = 60;
        //设置位置采集和打包周期
        mTraceClient.setInterval(gatherInterval, packInterval);
        String entityKey="key1=value1,key2=value2";
        mTraceClient.addEntity(serviceId,entityName,entityKey,mOnEntityListener);

    }

    private OnEntityListener mOnEntityListener = new OnEntityListener() {
        // 请求失败回调接口
        @Override
        public void onRequestFailedCallback(String arg0) {
            // TODO Auto-generated method stub
            Looper.prepare();
            Toast.makeText(getApplicationContext(),
                    "entity请求失败回调接口消息 : " + arg0, Toast.LENGTH_SHORT)
                    .show();
            Looper.loop();
        }

        // 添加entity回调接口
        @Override
        public void onAddEntityCallback(String arg0) {
            // TODO Auto-generated method stub
            Looper.prepare();
            Toast.makeText(getApplicationContext(),
                    "添加entity回调接口消息 : " + arg0, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        // 查询entity列表回调接口
        @Override
        public void onQueryEntityListCallback(String message) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onReceiveLocation(TraceLocation location) {
            Toast.makeText(getApplicationContext(),
                    "location : " + location.toString(), Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * 获取设备IMEI码
     *
     * @param context
     * @return
     */
    protected static String getImei(Context context) {
        String mImei = "NULL";
        try {
            mImei = ((TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            System.out.println("获取IMEI码失败");
            mImei = "NULL";
        }
        return mImei;
    }

    private void startTrack() {
        //开启轨迹服务
        mTraceClient.startTrace(mTrace, this);
    }

    private void stopTrack() {
        //停止轨迹服务
        mTraceClient.stopTrace(mTrace, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                startTrack();
                break;
            case R.id.btnStop:
                stopTrack();
                break;
        }
    }

    //开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
    @Override
    public void onTraceCallback(int i, String s) {
        if(i==0){//success

        }else if(i==1006){//The services has been started
            Toast.makeText(getApplicationContext(),
                    "开启轨迹服务回调接口 : " + s + " 错误码：" + i, Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
    @Override
    public void onTracePushCallback(byte b, String s) {
        Toast.makeText(getApplicationContext(),
                "轨迹服务推送接口 : " + s, Toast.LENGTH_SHORT)
                .show();
    }

    // 轨迹服务停止成功
    @Override
    public void onStopTraceSuccess() {
        Toast.makeText(getApplicationContext(),
                "轨迹服务停止成功 : ", Toast.LENGTH_SHORT)
                .show();
    }

    // 轨迹服务停止失败（arg0 : 错误编码，arg1 : 消息内容，详情查看类参考）
    @Override
    public void onStopTraceFailed(int i, String s) {
        Toast.makeText(getApplicationContext(),
                "轨迹服务停止失败 : " + s, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onResume();
    }


}
