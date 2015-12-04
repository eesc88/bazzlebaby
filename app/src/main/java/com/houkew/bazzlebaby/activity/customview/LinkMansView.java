package com.houkew.bazzlebaby.activity.customview;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.adapter.LinkMansAdapter;
import com.houkew.bazzlebaby.entity.AVOLinkMan;
import com.houkew.bazzlebaby.utils.AppShow;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/3 9:39
 * @Description: 联系人编辑
 */

public class LinkMansView extends AlertDialog.Builder {


    public static AlertDialog alertDialog;
    @Bind(R.id.et_link_name)
    EditText etLinkName;
    @Bind(R.id.et_link_tel)
    EditText etLinkTel;
    private LinkMansAdapter adapter;


    public static  ArrayList<AVOLinkMan> avoLinkMans = new ArrayList<>();

    @Bind(R.id.rc_linkman)
    RecyclerView rcLinkman;


    public LinkMansView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.link_mans_view, null);
        setView(view);
        ButterKnife.bind(this, view);
        adapter = new LinkMansAdapter(avoLinkMans);
        rcLinkman.setAdapter(adapter);
        rcLinkman.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick(R.id.bt_add)
    public void add() {
        String name = etLinkName.getText().toString();
        String tel = etLinkTel.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(tel)) {
            AppShow.showToast("输入有误");
            return;
        }
        AVOLinkMan avoLinkMan = new AVOLinkMan();
        avoLinkMan.setLinkName(name);
        avoLinkMan.setLinkPhone(tel);
        avoLinkMans.add(avoLinkMan);
        adapter.notifyDataSetChanged();
        etLinkName.setText("");
        etLinkTel.setText("");
    }



    @OnClick(R.id.bt_clear)
    public void clear() {
        alertDialog.cancel();
    }


}
