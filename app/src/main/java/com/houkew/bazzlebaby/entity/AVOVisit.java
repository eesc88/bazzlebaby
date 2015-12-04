package com.houkew.bazzlebaby.entity; /**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-12-02
 * Time: 10:31
 * Description:
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.Date;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/2 10:31
 * @Description: 拜访记录
 */
@AVClassName("Visit")
public class AVOVisit extends AVObject {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusCusName() {
        try {
            AVOCustomer avoCustomer = getAVObject("CusID", AVOCustomer.class);
            if (avoCustomer != null) {
                return avoCustomer.getCusName();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public AVOCustomer getAVOCustomer() {
        try {
            return getAVObject("CusID", AVOCustomer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setVisitDatetime(Date date) {
        put("VisitDatetime", date);
    }

    public Date getVisitDatetime() {
        return getDate("VisitDatetime");
    }

    public void setMark(String mark) {
        put("Mark", mark);
    }

    public String getMark() {
        return getString("Mark");
    }

    public void setTrueUserName(String trueUserName) {
        put("TrueUserName", trueUserName);
    }

    public String getTrueUserName() {
        return getString("TrueUserName");
    }

    public void setPosition(AVGeoPoint point) {
        put("LatLng", point);
    }

    public AVGeoPoint getPosition() {
        return getAVGeoPoint("LatLng");
    }

    public void setCusID(AVOCustomer avoCustomer) {
        put("CusID", avoCustomer);
    }

    public AVOCustomer getCusID() {
        try {
            return getAVObject("CusID", AVOCustomer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUserID(AVUser avUser) {
        put("UserID", avUser);
    }

    public AVUser getUserID() {
        return getAVUser("UserID");
    }

    public AVOVisit() {
    }

    public AVOVisit(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;
}
