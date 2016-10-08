package com.atto.developers.atto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.viewholder.DetailMakerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goodn on 2016-08-31.
 */
public class RecyclerPortfolioAdapter extends RecyclerView.Adapter<DetailMakerViewHolder> implements DetailMakerViewHolder.OnPortImageItemClickListener {

    List<PortfolioData> portItems = new ArrayList<>();

    public void clear() {
        portItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<PortfolioData> list) {
        portItems.addAll(list);
        notifyDataSetChanged();
    }

    public void add(PortfolioData portfolioData) {
        portItems.add(portfolioData);
        notifyDataSetChanged();
    }

    public static final int VIEW_TYPE_GROUP = 200;

    @Override
    public DetailMakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_detail_maker_image, parent, false);
        DetailMakerViewHolder holder = new DetailMakerViewHolder(view);
        holder.setOnPortImageItemClickListener(this);
        return holder;

    }


    @Override
    public void onBindViewHolder(DetailMakerViewHolder holder, int position) {

        holder.setImageData(portItems.get(position));
    }


    @Override
    public void onPortImageItemClick(View view, PortfolioData portfolioData, int position) {
        if (listener != null) {
            listener.onAdapterItemClick(view, portfolioData, position);
        }
    }


    public interface OnAdapterItemClickLIstener {
        public void onAdapterItemClick(View view, PortfolioData portfolioData, int position);

    }

    OnAdapterItemClickLIstener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {

        return portItems.size();
    }

}
