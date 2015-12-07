package com.houkew.bazzlebaby.activity.customview;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by  SUGUOJUN(cceecc@sina.cn) on 2015/12/6.
 * version  V1.0
 * Description:
 */
public class WaitView extends ProgressDialog {
    public WaitView(Context context) {
        super(context);
        setMessage("正在努力处理...");
    }



}
