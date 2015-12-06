package com.houkew.bazzlebaby.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.activity.customview.WaitView;
import com.houkew.bazzlebaby.activity.system.UserCenterActivity;
import com.houkew.bazzlebaby.adapter.VisitRecordAdapter;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.utils.CallBack;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VisitRecordListActivity extends BaseActivity {

    @Bind(R.id.rc_customer_visit)
    RecyclerView rcCustomerVisit;

    @Bind(R.id.rl_title_right)
    RelativeLayout rlTitleRight;
    private VisitRecordAdapter visitRecordAdapter;
    private WaitView waitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list_record);
        ButterKnife.bind(this);
        RelativeLayout rl_title_back = (RelativeLayout) findViewById(R.id.rl_title_back);
        rl_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitRecordListActivity.this, UserCenterActivity.class));
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title_back);
        tv_title.setText("我的");
        rlTitleRight.setVisibility(View.VISIBLE);
        rlTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitRecordListActivity.this, AddVisitRecordActivity.class));
            }
        });
        waitView=new WaitView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        waitView.show();
        CustomerModel.getVisitRecord(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                waitView.dismiss();
                if (code == 1) {
                    List<AVOVisit> list = (List<AVOVisit>) o;
                    visitRecordAdapter = new VisitRecordAdapter(list);
                    rcCustomerVisit.setAdapter(visitRecordAdapter);
                    rcCustomerVisit.setLayoutManager(new LinearLayoutManager(VisitRecordListActivity.this, LinearLayoutManager.VERTICAL, false));
                    visitRecordAdapter.setOnItemClickListener(new VisitRecordAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, AVOVisit data) {
                            Intent intent = new Intent(VisitRecordListActivity.this, VisitRecordActivity.class);
                            intent.putExtra("AVOVisit", data);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
