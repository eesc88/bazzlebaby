package com.houkew.bazzlebaby.entity; /**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-12-02
 * Time: 17:43
 * Description:
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/2 17:43
 * @Description: 联系人
 */
@AVClassName("LinkMan")
public class AVOLinkMan extends AVObject {

    private int position;

    public void setCreatUserID(AVUser avUser) {
        put("CreatUserID", avUser);
    }

    public String getCreatUserID() {
        return getString("CreatUserID");
    }

    public void setCreatTrueUserName(String creatTrueUserName) {
        put("CreatTrueUserName", creatTrueUserName);
    }

    public String getCreatTrueUserName() {
        return getString("CreatTrueUserName");
    }

    public void setLinkPhone(String linkPhone) {
        put("LinkPhone", linkPhone);
    }

    public String getLinkPhone() {
        return getString("LinkPhone");
    }

    public void setLinkSex(int linkSex) {
        put("LinkSex", linkSex);
    }

    public int getLinkSex() {
        Number linkSex = getNumber("LinkSex");
        if (linkSex == null) {
            return 0;
        } else {
            return linkSex.intValue();
        }
    }

    public void setLinkName(String linkName) {
        put("LinkName", linkName);
    }

    public String getLinkName() {
        return getString("LinkName");
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public AVOLinkMan() {
    }

    public AVOLinkMan(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;
}
