package com.houkew.bazzlebaby.activity;

import android.os.Bundle;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.customview.CircleView;
import com.houkew.bazzlebaby.activity.customview.CircleView.Dir;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        titleInit("业务助手");
    }


}
