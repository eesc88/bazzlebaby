package com.houkew.bazzlebaby.utils; /**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-11-30
 * Time: 15:42
 * Description:
 */

import android.widget.Toast;

import com.houkew.bazzlebaby.application.App;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/11/30 15:42
 * @Description: Tost统一样式
 */

public class AppShow {

    public static void showToast(String text) {
        Toast.makeText(App.context, text, Toast.LENGTH_LONG).show();
    }


    public static void showMapError(int rCode) {
        showToast("rCode:" + rCode);
    }
}
