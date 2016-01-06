package com.houkew.bazzlebaby.application; /**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-11-30
 * Time: 12:36
 * Description:
 */

import android.app.Application;
import android.content.Context;

import com.houkew.bazzlebaby.utils.Leancloud;
import com.houkew.bazzlebaby.utils.MapLocationManager;

/**
 * @version V1.0
 * @author SUGUOJUN(cceecc@sina.cn)
 * @date 2015/11/30 12:36
 * @Description: application核心文件(用一句话描述该文件做什么)
 */

public class App extends Application{

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        Leancloud.init(this);
        MapLocationManager.init(this);
    }





}
