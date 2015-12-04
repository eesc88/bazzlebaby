package com.houkew.bazzlebaby.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * @version V1.0
 * @author SUGUOJUN(cceecc@sina.cn)
 * @date 2015/12/2 18:31
 * @Description: 文件对象
 */
@AVClassName("_File")
public class AVOFile extends AVObject {

    public AVOFile() {
    }
    public AVOFile(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;
}
