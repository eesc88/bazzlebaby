package com.houkew.bazzlebaby.activity.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.BaseActivity;
import com.houkew.bazzlebaby.activity.customview.LinkMansView;
import com.houkew.bazzlebaby.activity.customview.WaitView;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOGroup;
import com.houkew.bazzlebaby.entity.AVOLinkMan;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.utils.AppShow;
import com.houkew.bazzlebaby.utils.Bimp;
import com.houkew.bazzlebaby.utils.BitmapTools;
import com.houkew.bazzlebaby.utils.CallBack;
import com.houkew.bazzlebaby.utils.FileUtils;
import com.houkew.bazzlebaby.utils.ImageItem;
import com.houkew.bazzlebaby.utils.Leancloud;
import com.houkew.bazzlebaby.utils.MapLocationManager;
import com.houkew.bazzlebaby.utils.PublicWay;
import com.houkew.bazzlebaby.utils.Res;
import com.lidroid.xutils.util.LogUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddVisitRecordActivity extends BaseActivity {

    private GridView noScrollgridview;
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public static Bitmap bimap;

    @Bind(R.id.tv_customer_name)
    TextView tvCustomerName;
    @Bind(R.id.rl_change_customer)
    RelativeLayout rlChangeCustomer;
    @Bind(R.id.et_summary)
    EditText etSummary;

    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;

    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;

    @Bind(R.id.rl_title_right)
    RelativeLayout rlTitleRight;

    public static AVOCustomer avoCustomer;

    private BitmapTools bitmapTools;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_add_visit_record, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        initTitle();
        Init();
        bitmapTools = new BitmapTools();
        getNearCustomer();
    }

    private void initTitle() {
        titleInit("新增访问记录");
        rlTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.GONE);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("保存");
        rlTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updata();
            }
        });
    }

    private void updata() {
        if (avoCustomer == null) {
            AppShow.showToast("请选择拜访客户");
            return;
        }
        final AVOVisit avoVisit = new AVOVisit();
        AMapLocation location = MapLocationManager.getInstance().getLocation();
        if (location == null) {
            AppShow.showToast("未能获取到定位位置信息");
            return;
        }
        AVUser avUser = AVUser.getCurrentUser();
        avoVisit.setUserID(avUser);
        avoVisit.setCusID(avoCustomer);
        avoVisit.setMark(etSummary.getText().toString().trim());
        avoVisit.setVisitDatetime(new Date(System.currentTimeMillis()));
        AVGeoPoint point = new AVGeoPoint(location.getLatitude(), location.getLongitude());
        avoVisit.setPosition(point);
        avoVisit.setTrueUserName(avUser.getString("TrueUserName"));
        try {
            avoVisit.setGroupID(avUser.getAVObject("GroupId", AVOGroup.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final WaitView waitView = new WaitView(this);
        waitView.show();
        new AsyncTask<Void, Void, AVException>() {
            @Override
            protected AVException doInBackground(Void... params) {
                try {
                    List<AVFile> fileList = new LinkedList<>();
                    for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                        AVFile avFile = bitmapTools.Bitmap2AVFile(Bimp.tempSelectBitmap.get(i).getBitmap());
                        avFile.save();
                        fileList.add(avFile);
                        LogUtils.i("保存图片:" + i);
                    }
                    avoVisit.addAll("Pics", fileList);
                    avoVisit.save();
                    Bimp.tempSelectBitmap.clear();
                    return null;
                } catch (AVException e) {
                    e.printStackTrace();
                    return e;
                }

            }

            @Override
            protected void onPostExecute(AVException e) {
                super.onPostExecute(e);
                waitView.dismiss();
                if (e == null) {
                    AppShow.showToast("保存成功");
                    finish();
                } else {
                    Leancloud.showError(e.getCode());
                }
            }
        }.execute();
    }

    @OnClick(R.id.rl_change_customer)
    public void changeCustomer() {
        CustomerListActivity.RUN_STATIC = 0;
        startActivity(new Intent(AddVisitRecordActivity.this, CustomerListActivity.class));
    }


    private void getNearCustomer() {
        CustomerModel.getNearCustomer(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                if (code == 1 && o != null) {
                    List<AVOCustomer> list = (List<AVOCustomer>) o;
                    if (!list.isEmpty()) {
                        avoCustomer = list.get(0);
                        setCustomerName(list.get(0).getCusName());
                    }
                }
            }
        });
    }

    private void setCustomerName(String name) {
        tvCustomerName.setText(name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (avoCustomer != null) {
            setCustomerName(avoCustomer.getCusName());
        }else{
            LogUtils.w("avoCustomer  is null..");
        }
    }

    public void Init() {

        pop = new PopupWindow(AddVisitRecordActivity.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button camera = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button photo = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button cancel = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomerListActivity.RUN_STATIC = 0;
                Intent intent = new Intent(AddVisitRecordActivity.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                LogUtils.i("OnItemClickListener....");
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(AddVisitRecordActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(AddVisitRecordActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        LogUtils.i("======================");
                        if (Bimp.max >= Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }
}
