package com.houkew.bazzlebaby.models;

import com.amap.api.location.AMapLocation;
import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.utils.AppShow;
import com.houkew.bazzlebaby.utils.CallBack;
import com.houkew.bazzlebaby.utils.Leancloud;
import com.houkew.bazzlebaby.utils.MapLocationManager;
import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/1 17:56
 * @Description: 该用户创建的附近的客户
 */

public class CustomerModel {
    public static void getNearCustomer(final CallBack cb) {
//        HashMap<String, String> params = new HashMap<>();
//        AMapLocation location = MapLocationManager.getInstance().getLocation();
//        params.put("uid", AVUser.getCurrentUser().getObjectId());
//        params.put("Lat", "" + location.getLatitude());
//        params.put("Lng", "" + location.getLongitude());
//        AVCloud.callFunctionInBackground("NearCustomer", params, new FunctionCallback<List<AVOCustomer>>() {
//            @Override
//            public void done(List<AVOCustomer> list, AVException e) {
//                if (e == null) {
//                    LogUtils.i("查询成功-->NearCustomer:"+list);
//                    cb.callBack(1, list);
//                } else {
//                    e.printStackTrace();
//                    Leancloud.showError(e.getCode());
//                    cb.callBack(0, list);
//                }
//            }
//        });

        AVQuery<AVOCustomer> query = AVQuery.getQuery(AVOCustomer.class);
        AMapLocation location = MapLocationManager.getInstance().getLocation();
        if (location == null) {
            AppShow.showToast("正在获取位置信息");
            cb.callBack(0, null);
            return;
        }
        AVGeoPoint avGeoPoint = new AVGeoPoint(location.getLatitude(), location.getLongitude());
        query.whereNear("LatLng", avGeoPoint);
        query.whereEqualTo("UserID", AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVOCustomer>() {
            @Override
            public void done(List<AVOCustomer> list, AVException e) {
                if (e == null && list != null && !list.isEmpty()) {
                    cb.callBack(1, list);
                } else {
                    cb.callBack(0, list);
                    if (e != null) {
                        e.printStackTrace();
                        Leancloud.showError(e.getCode());
                    } else {
                        AppShow.showToast("数据为空");
                    }
                }
            }
        });
    }

    /**
     * 获取用户拜访记录
     *
     * @param cb
     */
    public static void getVisitRecord(final CallBack cb) {
        AVQuery<AVOVisit> query = AVQuery.getQuery(AVOVisit.class);

        AMapLocation location = MapLocationManager.getInstance().getLocation();
        if (location == null) {
            AppShow.showToast("正在获取位置信息");
            cb.callBack(0, null);
            return;
        }

        AVGeoPoint avGeoPoint = new AVGeoPoint(location.getLatitude(), location.getLongitude());
        query.whereNear("LatLng", avGeoPoint);
        query.whereEqualTo("UserID", AVUser.getCurrentUser());
        query.include("CusID");
        query.include("UserID");
        query.findInBackground(new FindCallback<AVOVisit>() {
            @Override
            public void done(List<AVOVisit> list, AVException e) {
                if (e == null && list != null && !list.isEmpty()) {
                    cb.callBack(1, list);
                } else {
                    cb.callBack(0, list);
                    if (e != null) {
                        e.printStackTrace();
                        Leancloud.showError(e.getCode());
                    } else {
                        AppShow.showToast("数据为空");
                    }
                }
            }
        });
    }

    /**
     * 获取用户拜访记录
     *
     * @param cb
     */
    public static void getVisitRecord(AVOCustomer avoCustomer, final CallBack cb) {
        AVQuery<AVOVisit> query = AVQuery.getQuery(AVOVisit.class);
        query.whereEqualTo("CusID", avoCustomer);
        //query.whereEqualTo("UserID",AVUser.getCurrentUser());
        query.include("CusID");
        query.include("UserID");
        query.findInBackground(new FindCallback<AVOVisit>() {
            @Override
            public void done(List<AVOVisit> list, AVException e) {
                if (e == null && list != null && !list.isEmpty()) {
                    cb.callBack(1, list);
                } else {
                    cb.callBack(0, list);
                    if (e != null) {
                        e.printStackTrace();
                        Leancloud.showError(e.getCode());
                    } else {
                        AppShow.showToast("数据为空");
                    }
                }
            }
        });
    }


}
