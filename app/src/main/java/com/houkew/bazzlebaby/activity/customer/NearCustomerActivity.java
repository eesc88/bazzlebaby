package com.houkew.bazzlebaby.activity.customer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.adapter.NearCustomerAdapter;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.models.MapModel;
import com.houkew.bazzlebaby.utils.CallBack;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NearCustomerActivity extends BaseActivity {

    @Bind(R.id.rc_customer_near)
    RecyclerView rcCustomerNear;

    private MapModel mapModel;
    private NearCustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_customer);
        ButterKnife.bind(this);
        titleInit("附近小区");

        CustomerModel.getNearCustomer(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                List<AVOCustomer> list=(List<AVOCustomer>)o;
            }
        });

        initCustomer();
    }

    private void initCustomer() {


        mapModel = new MapModel(this);
        mapModel.getNearCommunity(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                ArrayList<PoiItem> poiItems = (ArrayList<PoiItem>) o;
                NearCustomerAdapter adapter = new NearCustomerAdapter(poiItems);
                rcCustomerNear.setAdapter(adapter);
                rcCustomerNear.setLayoutManager(new LinearLayoutManager(NearCustomerActivity.this, LinearLayoutManager.VERTICAL, false));
                adapter.setOnItemClickListener(new NearCustomerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, PoiItem data) {
                        LogUtils.i(data.toString());
                    }
                });
            }
        });
    }

}
