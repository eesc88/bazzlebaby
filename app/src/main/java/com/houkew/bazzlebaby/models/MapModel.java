package com.houkew.bazzlebaby.models;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.houkew.bazzlebaby.utils.AppShow;
import com.houkew.bazzlebaby.utils.CallBack;
import com.houkew.bazzlebaby.utils.MapLocationManager;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;

/**
 * @author Name:SUGUOJUN Email:ccecc@sina.cn
 * @Create Date：2015-7-13
 * @Version:1.0
 * @类说明:高德相关查询
 */
public class MapModel {


    private Context context;

    public MapModel(Context context) {
        this.context = context;
    }

    /**
     * 查询附近公交站对应的公交车列表，已经对应的同路赛手信息
     */
    public void getNearCommunity(final CallBack cb) {

        LogUtils.i("查询第一步<----->热点查询附近公交站开始....");
        AMapLocation location = MapLocationManager.getInstance().getLocation();
        if (location == null) {
            LogUtils.e("地理位置获取失败....");
            cb.callBack(-1,null);
            return;
        }
        // 查询附近的公交站
        PoiSearch.Query queryCurrent = new PoiSearch.Query("", "生活服务|商务住宅",
                location.getCityCode());
        queryCurrent.setPageSize(100);// 设置每页最多返回多少条poiitem
        queryCurrent.setPageNum(0);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(context, queryCurrent);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(location.getLatitude(),
                location.getLongitude()), 1000));
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {

            @Override
            public void onPoiSearched(PoiResult poiResult, int rCode) {
                // TODO Auto-generated method stub
                LogUtils.i("附近小区查询-->rCode:"+rCode+"<>poiResult:" + poiResult);
                if (rCode != 0 || poiResult == null
                        || poiResult.getPois() == null
                        || poiResult.getPois().isEmpty()) {
                    LogUtils.e("附近小区查询异常...poiItems=" + poiResult);
                    AppShow.showMapError(rCode);
                } else {
                    ArrayList<PoiItem> poiItems = poiResult.getPois();
                    cb.callBack(1,poiItems);
                }
            }
        });
        poiSearch.searchPOIAsyn();
    }

}
