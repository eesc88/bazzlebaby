package com.houkew.bazzlebaby.activity.customer;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.activity.customview.RoundNetworkImageView;
import com.houkew.bazzlebaby.adapter.CustomerPicAdapter;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.utils.Leancloud;
import com.houkew.bazzlebaby.utils.Time;
import com.houkew.bazzlebaby.utils.VolleyUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VisitRecordActivity extends BaseActivity {

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
    @Bind(R.id.tv_visit_time)
    TextView tvVisitTime;
    @Bind(R.id.tv_visit_info)
    TextView tvVisitInfo;
    @Bind(R.id.gc_customer_pic)
    GridView gcCustomerPic;

    private AVOVisit avoVisit;
    private AVOCustomer avoCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_record);
        ButterKnife.bind(this);
        titleInit("拜访详情");
        avoVisit = getIntent().getParcelableExtra("AVOVisit");
        tvVisitTime.setText("访问时间：" + Time.dateToString(avoVisit.getCreatedAt()));
        tvVisitInfo.setText(avoVisit.getMark());


        AVRelation<AVObject> rl = avoVisit.getRelation("Pics");
        rl.getQuery().findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> results, AVException e) {
                if (e == null && !results.isEmpty()) {
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < results.size(); i++) {
                        list.add(results.get(i).getString("url"));
                    }
                    gcCustomerPic.setAdapter(new CustomerPicAdapter(list, VisitRecordActivity.this));
                } else if (e != null) {
                    e.printStackTrace();
                    Leancloud.showError(e.getCode());
                } else {
                    LogUtils.i("查询成功,但是类表为空.");
                }
            }
        });


        avoCustomer = avoVisit.getAVOCustomer();
        if (avoCustomer != null) {
            tvCustomerName.setText("名称：" + avoCustomer.getCusName());
            tvCustomerTel.setText("电话：" + avoCustomer.getTel());
            tvCustomerAddress.setText("地址：" + avoCustomer.getAddress());
            AVRelation<AVObject> relation = avoCustomer.getRelation("Pics");
            relation.getQuery().findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> results, AVException e) {
                    if (e == null && !results.isEmpty()) {
                        visitPic.setImageUrl(results.get(0).getString("url"), VolleyUtils.getImageLoader(VisitRecordActivity.this));
                    } else if (e != null) {
                        e.printStackTrace();
                        Leancloud.showError(e.getCode());
                    }
                }
            });
        }

        AVUser avUser = avoVisit.getUserID();
        if (avUser != null) {
            AVFile avFile = avUser.getAVFile("userLogo");
            if (avFile != null)
                visitUserPic.setImageUrl(avFile.getUrl(), VolleyUtils.getImageLoader(this));
        }

    }

}
