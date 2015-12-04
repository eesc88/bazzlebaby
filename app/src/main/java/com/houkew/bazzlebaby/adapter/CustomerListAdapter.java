package com.houkew.bazzlebaby.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.entity.AVOCustomer;
import com.houkew.bazzlebaby.entity.AVOVisit;

import java.util.List;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/3 17:04
 * @Description: 附近客户列表
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> implements View.OnClickListener {
    private List<AVOCustomer> avoCustomers;

    public CustomerListAdapter(List<AVOCustomer> avoCustomers) {
        super();
        this.avoCustomers = avoCustomers;
    }

    @Override
    public int getItemCount() {
        return avoCustomers.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(avoCustomers.get(position));
        holder.itemView.setTag(avoCustomers.get(position));
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
        void onItemClick(View view, AVOCustomer data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, (AVOCustomer) v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCustomerName, tvCustomerAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tvCustomerAddress = (TextView) itemView.findViewById(R.id.tv_customer_address);
        }

        public void bind(AVOCustomer avoVisit) {
            tvCustomerName.setText(avoVisit.getCusName());
            tvCustomerAddress.setText(avoVisit.getCreatedAt().toString());
        }
    }
}
