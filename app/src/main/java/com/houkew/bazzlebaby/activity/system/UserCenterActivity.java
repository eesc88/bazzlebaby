package com.houkew.bazzlebaby.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.activity.customer.AddCustomerActivity;
import com.houkew.bazzlebaby.activity.customer.AddVisitRecordActivity;
import com.houkew.bazzlebaby.activity.customer.CustomerListActivity;
import com.houkew.bazzlebaby.activity.customer.VisitRecordActivity;
import com.houkew.bazzlebaby.adapter.VisitRecordAdapter;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.models.CustomerModel;
import com.houkew.bazzlebaby.utils.CallBack;
import com.houkew.bazzlebaby.utils.VolleyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserCenterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Bind(R.id.rc_customer_visit)
    RecyclerView rcCustomerVisit;

    private VisitRecordAdapter visitRecordAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserCenterActivity.this, AddVisitRecordActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getHea
        NetworkImageView ni_user_log=(NetworkImageView)navigationView.getHeaderView(0).findViewById(R.id.ni_user_log);
        TextView tv_user_name=(TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        TextView tv_user_phone=(TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_user_phone);
        AVUser avUser=AVUser.getCurrentUser();
        AVFile avFile=avUser.getAVFile("userLogo");
        if(avFile!=null)
        ni_user_log.setImageUrl(avFile.getUrl(), VolleyUtils.getImageLoader(this));
        tv_user_name.setText(avUser.getUsername());
        tv_user_phone.setText(avUser.getMobilePhoneNumber());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(UserCenterActivity.this, AddCustomerActivity.class));
        } else if (id == R.id.nav_custonmer) {
               startActivity(new Intent(UserCenterActivity.this, CustomerListActivity.class));
        } else if (id == R.id.nav_user_out) {
            AVUser.logOut();
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        CustomerModel.getVisitRecord(new CallBack() {
            @Override
            public void callBack(int code, Object o) {
                if (code == 1) {
                    List<AVOVisit> list = (List<AVOVisit>) o;
                    visitRecordAdapter = new VisitRecordAdapter(list);
                    rcCustomerVisit.setAdapter(visitRecordAdapter);
                    rcCustomerVisit.setLayoutManager(new LinearLayoutManager(UserCenterActivity.this, LinearLayoutManager.VERTICAL, false));
                    visitRecordAdapter.setOnItemClickListener(new VisitRecordAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, AVOVisit data) {
                            Intent intent = new Intent(UserCenterActivity.this, VisitRecordActivity.class);
                            intent.putExtra("AVOVisit", data);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


    }
}
