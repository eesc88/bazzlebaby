package com.houkew.bazzlebaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogUtil;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.customer.VisitRecordListActivity;
import com.houkew.bazzlebaby.activity.system.UserCenterActivity;

public class LaunchActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.log.i("handleMessage");
            AVUser avUser = AVUser.getCurrentUser();
            Intent intent = new Intent();
            if (avUser == null) {
                intent.setClass(LaunchActivity.this, LoginActivity.class);
            } else {
                //intent.setClass(LaunchActivity.this, MainActivity.class);
               //intent.setClass(LaunchActivity.this, NearCustomerActivity.class);
                //intent.setClass(LaunchActivity.this, PoiKeywordSearchActivity.class);
                intent.setClass(LaunchActivity.this, UserCenterActivity.class);
            }
            startActivity(intent);
            finish();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        LogUtil.log.i("onCreate");
        handler.sendEmptyMessageDelayed(0, 1000);
    }

}
