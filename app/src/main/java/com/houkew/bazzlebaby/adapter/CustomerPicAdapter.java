package com.houkew.bazzlebaby.adapter; /**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-12-04
 * Time: 13:52
 * Description:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.utils.VolleyUtils;

import java.util.List;

/**
 * @version V1.0
 * @author SUGUOJUN(cceecc@sina.cn)
 * @date 2015/12/4 13:52
 * @Description: 拜访详情图片
 */

public class CustomerPicAdapter extends BaseAdapter{
    private List<String> picUrls;
    private Context context;

    public CustomerPicAdapter(List<String> picUrls,Context context) {
        super();
        this.picUrls=picUrls;
        this.context=context;
    }

    @Override
    public int getCount() {
        return picUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_network_imageview,null);
            holder.ni_imageview=(NetworkImageView)convertView.findViewById(R.id.ni_imageview);
            convertView.setTag(holder);
        }else {
            holder=(Holder)convertView.getTag();
        }
        holder.ni_imageview.setImageUrl(picUrls.get(position), VolleyUtils.getImageLoader(context));
        return convertView;
    }

    class Holder{
        NetworkImageView ni_imageview;
    }
}
