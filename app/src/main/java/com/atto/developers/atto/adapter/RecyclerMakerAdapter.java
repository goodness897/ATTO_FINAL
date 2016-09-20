package com.atto.developers.atto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.viewholder.RecyclerMakerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */

public class RecyclerMakerAdapter extends RecyclerView.Adapter<RecyclerMakerViewHolder> implements RecyclerMakerViewHolder.OnMakerItemClickListener {

    List<MakerData> items = new ArrayList<>();


    public void add(MakerData makerData) {
        items.add(makerData);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<MakerData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerMakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_maker, parent, false);
        RecyclerMakerViewHolder holder = new RecyclerMakerViewHolder(view);
        holder.setOnMakerItemClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerMakerViewHolder holder, int position) {
        holder.setMakerData(items.get(position));

    }


    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, MakerData makerData, int position);
    }

    OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onMakerItemClick(View view, MakerData makerData, int position) {

        if (listener != null) {
            listener.onAdapterItemClick(view, makerData, position);
        }

    }

}
