package com.houkew.bazzlebaby.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.entity.AVOVisit;
import com.houkew.bazzlebaby.utils.Time;

import java.util.List;

/**
 * User: SUGUOJUN(cceecc@sina.cn)
 * Version:1.0
 * Date: 2015-11-30
 * Time: 17:56
 * Description:
 */
public class VisitRecordAdapter extends RecyclerView.Adapter<VisitRecordAdapter.ViewHolder> implements View.OnClickListener {
    private List<AVOVisit> avoVisits;

    public VisitRecordAdapter(List<AVOVisit> avoVisits) {
        super();
        this.avoVisits = avoVisits;
    }

    @Override
    public int getItemCount() {
        return avoVisits.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(avoVisits.get(position));
        holder.itemView.setTag(avoVisits.get(position));
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
        void onItemClick(View view, AVOVisit data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v,(AVOVisit)v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCustomerName, tvCustomerAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerAddress = (TextView) itemView.findViewById(R.id.tv_customer_address);
        }

        public void bind(AVOVisit avoVisit) {
            tvCustomerName.setText(avoVisit.getCusCusName());
            tvCustomerAddress.setText(Time.dateToString(avoVisit.getCreatedAt()));
        }
    }
}
