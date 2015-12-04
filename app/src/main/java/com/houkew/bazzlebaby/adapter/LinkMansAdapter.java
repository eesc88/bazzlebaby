package com.houkew.bazzlebaby.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.houkew.bazzlebaby.R;
import com.houkew.bazzlebaby.entity.AVOLinkMan;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/3 10:53
 * @Description: 联系人Adapter
 */

public class LinkMansAdapter extends RecyclerView.Adapter<LinkMansAdapter.ViewHolder> implements View.OnClickListener {

    private List<AVOLinkMan> avoLinkMans;

    public LinkMansAdapter(List<AVOLinkMan> avoLinkMans) {
        super();
        this.avoLinkMans = avoLinkMans;
    }

    @Override
    public int getItemCount() {
        return avoLinkMans.size();
    }

    @Override
    public void onBindViewHolder(LinkMansAdapter.ViewHolder holder, int position) {
        AVOLinkMan avoLinkMan = avoLinkMans.get(position);
        avoLinkMan.setPosition(position);
        holder.bind(avoLinkMan);
        holder.bt_delete.setTag(avoLinkMan);
    }

    @Override
    public LinkMansAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = View.inflate(viewGroup.getContext(), R.layout.item_link_man, null);
        ViewHolder holder = new ViewHolder(view);
        holder.bt_delete.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onClick(View v) {
        avoLinkMans.remove(((AVOLinkMan) v.getTag()).getPosition());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLinkName, tvLinkTel;
        Button bt_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLinkName = (TextView) itemView.findViewById(R.id.tv_link_name);
            tvLinkTel = (TextView) itemView.findViewById(R.id.tv_link_tel);
            bt_delete = (Button) itemView.findViewById(R.id.bt_delete);
        }

        public void bind(AVOLinkMan avoLinkMan) {
            tvLinkName.setText(avoLinkMan.getLinkName());
            tvLinkTel.setText(avoLinkMan.getLinkPhone());
        }
    }
}
