package com.houkew.bazzlebaby.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.houkew.bazzlebaby.R;

import java.util.ArrayList;

/**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-11-30
 * Time: 17:56
 * Description:
 */
public class NearCustomerAdapter extends RecyclerView.Adapter<NearCustomerAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<PoiItem> poiItems;

    public NearCustomerAdapter(ArrayList<PoiItem> poiItems) {
        super();
        this.poiItems = poiItems;
    }

    @Override
    public int getItemCount() {
        return poiItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(poiItems.get(position));
        holder.itemView.setTag(poiItems.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = View.inflate(viewGroup.getContext(), R.layout.item_costomer_near, null);
        view.setOnClickListener(this);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(View view, PoiItem data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v,(PoiItem)v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCustomerName, tvCustomerAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerAddress = (TextView) itemView.findViewById(R.id.tv_customer_address);
        }

        public void bind(PoiItem poiItem) {
            tvCustomerName.setText(poiItem.getTitle());
            tvCustomerAddress.setText(poiItem.getAdName());
        }
    }
}
