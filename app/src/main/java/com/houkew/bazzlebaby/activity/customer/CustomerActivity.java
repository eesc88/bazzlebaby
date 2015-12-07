package com.houkew.bazzlebaby.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.activity.customview.RoundNetworkImageView;
import com.houkew.bazzlebaby.activity.customview.WaitView;
import com.houkew.bazzlebaby.adapter.VisitRecordAdapter;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.utils.AppShow;
import com.houkew.bazzlebaby.utils.CallBack;
import com.houkew.bazzlebaby.utils.Leancloud;
import com.houkew.bazzlebaby.utils.VolleyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomerActivity extends BaseActivity {

    @Bind(R.id.visit_pic)
    NetworkImageView visitPic;
    @Bind(R.id.visit_user_pic)
    RoundNetworkImageView visitUserPic;
    @Bind(R.id.tv_customer_name)
    TextView tvCustomerName;
    @Bind(R.id.tv_customer_tel)
    TextView tvCustomerTel;
    @Bind(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    @Bind(R.id.tv_visit_info)
    TextView tvVisitInfo;
    @Bind(R.id.rl_visit_record)
    RecyclerView rlVisitRecord;


    private AVOCustomer avoCustomer;

    private VisitRecordAdapter visitRecordAdapter;
    private WaitView waitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        titleInit("客户详情");
        waitView=new WaitView(this);
        avoCustomer = getIntent().getParcelableExtra("avoCustomer");
        if (avoCustomer != null) {
            tvCustomerName.setText("名称：" + avoCustomer.getCusName());
            tvCustomerTel.setText("电话：" + avoCustomer.getTel());
            tvCustomerAddress.setText("地址：" + avoCustomer.getAddress());
            tvVisitInfo.setText(avoCustomer.getInstruction());
            AVRelation<AVObject> relation = avoCustomer.getRelation("Pics");
            relation.getQuery().findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> results, AVException e) {
                    if (e == null && !results.isEmpty()) {
                        visitPic.setImageUrl(results.get(0).getString("url"), VolleyUtils.getImageLoader(CustomerActivity.this));
                    } else if (e != null) {
                        e.printStackTrace();
                        Leancloud.showError(e.getCode());
                    }
                }
            });

            waitView.show();
            CustomerModel.getVisitRecord(avoCustomer, new CallBack() {
                @Override
                public void callBack(int code, Object o) {
                    waitView.dismiss();
                    if (code == 1) {
                        List<AVOVisit> list = (List<AVOVisit>) o;
                        visitRecordAdapter = new VisitRecordAdapter(list);
                        rlVisitRecord.setAdapter(visitRecordAdapter);
                        rlVisitRecord.setLayoutManager(new LinearLayoutManager(CustomerActivity.this, LinearLayoutManager.VERTICAL, false));
                        visitRecordAdapter.setOnItemClickListener(new VisitRecordAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, AVOVisit data) {
                                Intent intent = new Intent(CustomerActivity.this, VisitRecordActivity.class);
                                intent.putExtra("AVOVisit", data);
                                startActivity(intent);
                            }
                        });
                    }
                }
            });
        } else {
            AppShow.showToast("数据异常...");
            finish();
        }

    }

}
