package com.atto.developers.atto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.viewholder.DetailTradeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */

public class RecyclerMyNegoAdapter extends RecyclerView.Adapter<DetailTradeViewHolder> implements DetailTradeViewHolder.OnNegoItemClickListener{

    List<NegoData> items = new ArrayList<>();

    public void add(NegoData negoData) {
        items.add(negoData);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NegoData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public DetailTradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_img_detail_trade, parent, false);
        DetailTradeViewHolder holder = new DetailTradeViewHolder(view);
        holder.setOnNegoItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailTradeViewHolder holder, int position) {
        holder.setNegoData(items.get(position));
    }


    @Override
    public void onNegoItemClick(View view, NegoData negoData, int position) {
        if (listener != null) {
            listener.onAdapterItemClick(view, negoData, position);
        }
    }

    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, NegoData negoData, int position);
    }

    RecyclerDetailTradeAdapter.OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(RecyclerDetailTradeAdapter.OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


}

