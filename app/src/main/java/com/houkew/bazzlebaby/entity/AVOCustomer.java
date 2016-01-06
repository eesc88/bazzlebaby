package com.houkew.bazzlebaby.entity;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/1 17:58
 * @Description: 客户
 */
@AVClassName("Customer")
public class AVOCustomer extends AVObject {

    public String getInstruction() {
        return getString("Instruction");
    }

    public void setInstruction(String instruction) {
        put("Instruction", instruction);
    }

    public String getCusName() {
        return getString("CusName");
    }

    public void setCusName(String cusName) {
        put("CusName", cusName);
    }

    public void setCreatUserID(String creatUserID) {
        put("CreatUserID", creatUserID);
    }

    public String getCreatUserID() {
        return getString("CreatUserID");
    }

    public void setCreaTruerName(String creaTruerName) {
        put("CreatTrueUserName", creaTruerName);
    }

    public String getCreaTruerName() {
        return getString("CreatTrueUserName");
    }

    public void setCusType(String cusType) {
        put("CusType", cusType);
    }

    public String getCusType() {
        return getString("CusType");
    }

    public void setAddress(String address) {
        put("Address", address);
    }

    public String getAddress() {
        return getString("Address");
    }

    public void setPosition(AVGeoPoint position) {
        put("LatLng", position);
    }

    public AVGeoPoint getPosition() {
        return getAVGeoPoint("LatLng");
    }

    public void setTel(String tel) {
        put("Tel", tel);
    }

    public String getTel() {
        return getString("Tel");
    }

    public void setUserID(AVUser avUser) {
        put("UserID", avUser);
    }

    public void setGroupID(AVOGroup avoGroup) {
        put("groupID", avoGroup);
    }

    public AVOGroup getGroupID() {
        try {
            return getAVObject("groupID", AVOGroup.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public AVUser getUserID() {
        return getAVUser("UserID", AVUser.class);
    }


    public AVOCustomer() {
    }

    public AVOCustomer(Parcel in) {
        super(in);
    }

    public static final Creator CREATOR = AVObjectCreator.instance;
}
