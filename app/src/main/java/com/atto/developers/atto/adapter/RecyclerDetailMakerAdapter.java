package com.atto.developers.atto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.viewholder.DetailMakerHeaderViewHolder;
import com.atto.developers.atto.viewholder.DetailMakerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goodn on 2016-08-31.
 */
public class RecyclerDetailMakerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DetailMakerViewHolder.OnPortImageItemClickListener {

    private MakerData mMakerData;
    private boolean isMakerMe;

    List<PortfolioData> portItems = new ArrayList<>();

    public boolean isHeader(int position) {
        return position == 0;
    }

    public void setMakerData(MakerData mMakerData, boolean isMakerMe) {
        this.mMakerData = mMakerData;
        this.isMakerMe = isMakerMe;
    }

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

    public static final int VIEW_TYPE_HEADER = 100;
    public static final int VIEW_TYPE_GROUP = 200;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_GROUP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_detail_maker, parent, false);
                DetailMakerHeaderViewHolder holder = new DetailMakerHeaderViewHolder(view);
                return holder;
            }
            case VIEW_TYPE_GROUP: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_detail_maker_image, parent, false);
                DetailMakerViewHolder holder = new DetailMakerViewHolder(view);
                holder.setOnPortImageItemClickListener(this);
                return holder;
            }
        }
        throw new IllegalArgumentException("invalid viewtype");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (portItems.size() > 0) {
            if (position == 0) {
                DetailMakerHeaderViewHolder hvh = (DetailMakerHeaderViewHolder) holder;
                hvh.setHeaderData(mMakerData, isMakerMe);
                return;
            }
            position--;
            for (int i = 0; i < portItems.size(); i++) {
                if (position == 0) {
                    if (holder.getItemViewType() != VIEW_TYPE_GROUP) {
                        throw new IllegalArgumentException("invalid view holder");
                    }
                    DetailMakerViewHolder gvh = (DetailMakerViewHolder) holder;
                    gvh.setImageData(portItems.get(i));
                    return;
                }
                position--;
            }
            throw new IllegalArgumentException("invalid position");
        }
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
        int count = 0;
        count++;
        for (int i = 0; i < portItems.size(); i++) {
            count++;
        }
        return count;
    }

}
