package com.houkew.bazzlebaby.utils;

import android.content.Context;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOGroup;
import com.houkew.bazzlebaby.entity.AVOLinkMan;
import com.houkew.bazzlebaby.entity.AVOVisit;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/11/30 12:44
 * @Description: Leancloud初始化已经相关工具
 */

public class Leancloud {

    public static void init(Context context) {
        AVObject.registerSubclass(AVOCustomer.class);
        AVObject.registerSubclass(AVOVisit.class);
        AVObject.registerSubclass(AVOLinkMan.class);
        AVObject.registerSubclass(AVOGroup.class);

        AVOSCloud.initialize(context, "adVm2UtCx0gYmtQ8vt1kRThC", "pj7ONObW4AJ3UlGLqa23feG5");

        AVCloud.setProductionMode(false); //false调用测试环境云代码
    }

    public static void showError(int eCode) {
        AppShow.showToast(getErrorMessage(eCode));
    }


    public static String getErrorMessage(int eCode) {
        String eMessage;
        switch (eCode) {
            default:
                eMessage = "未知异常"+eCode;
                break;
        }
        return eMessage;
    }


}

