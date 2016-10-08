package com.atto.developers.atto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.viewholder.RealTimeTradeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */

public class RecyclerRealTimeTradeAdapter extends RecyclerView.Adapter<RealTimeTradeViewHolder> implements RealTimeTradeViewHolder.OnTradeItemClickListener {

    List<TradeData> items = new ArrayList<>();

    public void add(TradeData tradeData) {
        items.add(tradeData);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<TradeData> list) {
        if(!items.isEmpty()) items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RealTimeTradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_realtime_trade, parent, false);
        RealTimeTradeViewHolder holder = new RealTimeTradeViewHolder(view);
        holder.setOnTradeItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RealTimeTradeViewHolder holder, int position) {
        holder.setTradeData(items.get(position));

    }

    @Override
    public void onTradeItemClick(View view, TradeData tradeData, int position) {

        if (listener != null) {
            listener.onAdapterItemClick(view, tradeData, position);
        }
    }

    public interface OnAdapterItemClickListener {

        public void onAdapterItemClick(View view, TradeData tradeData, int position);
    }

    OnAdapterItemClickListener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}

