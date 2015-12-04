package com.houkew.bazzlebaby.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.adapter.CustomerListAdapter;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.utils.CallBack;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户列表
 */
public class CustomerListActivity extends BaseActivity {

    @Bind(R.id.rc_customer_near)
    RecyclerView rcCustomerNear;

    CustomerListAdapter adapter;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.rl_title_right)
    RelativeLayout rlTitleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        titleInit("附近的客户");
        ivTitleRight.setVisibility(View.VISIBLE);
        rlTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setVisibility(View.GONE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        getNearCustomer();
    }

    private void getNearCustomer() {
        CustomerModel.getNearCustomer(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                if (code == 1) {
                    List<AVOCustomer> data = (List<AVOCustomer>) o;
                    initList(data);
                }
            }
        });
    }

    private void initList(List<AVOCustomer> data) {
        adapter = new CustomerListAdapter(data);
        rcCustomerNear.setAdapter(adapter);
        rcCustomerNear.setLayoutManager(new LinearLayoutManager(CustomerListActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClickListener(new CustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, AVOCustomer data) {
                AddVisitRecordActivity.avoCustomer = data;
                finish();
            }
        });
    }

    @OnClick(R.id.rl_title_right)
    public void right() {
        startActivity(new Intent(CustomerListActivity.this, AddCustomerActivity.class));
    }

}
