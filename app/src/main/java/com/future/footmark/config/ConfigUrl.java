package com.future.footmark.config;

/**
 * 基本链接
 * Created by chris on 2016/1/8.
 */
public class ConfigUrl {

    /**
     * 外网链接
     */
    public static final String OUTSIZE_URL="http://115.29.34.150:8888/LsWeatherAPI/";
    public static final String OUTSIZE_URL2="http://118.112.180.202:20232/";
    /**
     * 内网链接
     */
    public static final String TEST_URL="http://192.168.1.166:8888/LsWeatherAPI/";
    public static final String TEST_URL2="http://";

    public static String BASE_URL=OUTSIZE_URL2;

    public static final String URL = "http://118.112.180.202:20232/lesogo_scqx_mobile/";

    /**
     * 常规天气
     */
    public static final String FORECAST=OUTSIZE_URL+"cityweather/forecast";
    /**
     * 产品查询
     */
    public static final String QUERY_PRODUCT=BASE_URL+"lesogo_scqx_mobile/document/queryPDF";
    /**
     * 预警信号
     */
    public static final String EARLY_WARING_SIGNAL=BASE_URL+"lesogo_scqx_mobile/warning/sign.do";

    /**
     * 卫星云图
     */
    public static final String RADAR_URL = URL + "imag/queryImag";
    /**
     * 气象监测
     */
    public static final String MonitorURL = URL + "currenttimebymobile/currentTimeAll.do";
    /**
     * 城市
     */
    public static final String CityURL = URL + "info/city.do";
    /**
     * 24小时
     */
    public static final String Draw24hURL = URL+"currenttime/currentTimeByHourData.do";
    /**
     * 气象监测--降水
     */
    public static final String RainURL = URL+"currenttimebymobile/currentTimePrecipitation";

}
