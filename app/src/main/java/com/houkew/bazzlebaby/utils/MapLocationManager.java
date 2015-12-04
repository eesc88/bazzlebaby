package com.houkew.bazzlebaby.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author Name:SUGUOJUN E-mail:cceecc@sina.cn
 * @version 创建时间：2015年7月3日 上午10:51:05
 * @类说明:高得地理位置管理器
 */
public class MapLocationManager implements AMapLocationListener {

    private static AMapLocation location;

    private AMapLocationClientOption mLocationOption;

    private static MapLocationManager mapLocationManager;

    public static AMapLocationClient mLocationClient = null;

    /**
     * 该方法仅在application调用!!!
     *
     * @param context
     * @return MapLocationManager
     */
    public static void init(Context context) {
        mapLocationManager = new MapLocationManager(context);
    }

    /**
     * 获取地理位置信息时候调用
     *
     * @return
     */
    public static MapLocationManager getInstance() {
        return mapLocationManager;
    }


    private MapLocationManager(Context context) {
        // TODO Auto-generated constructor stub

        //初始化定位
        mLocationClient = new AMapLocationClient(context);

        //设置定位回调监听
        mLocationClient.setLocationListener(MapLocationManager.this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);

        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);


        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20*1000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        LogUtils.i("onLocationChanged:" + amapLocation);
        if (amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                location = amapLocation;
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    public AMapLocation getLocation() {
        return location;
    }

    public void setLocation(AMapLocation location) {
        MapLocationManager.location = location;
    }

    public static void onDestroy() {
        mLocationClient.onDestroy();
    }


}
