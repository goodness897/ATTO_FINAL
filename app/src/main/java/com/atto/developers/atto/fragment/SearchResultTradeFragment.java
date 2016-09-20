package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.DetailTradeActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerRealTimeTradeAdapter;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultTradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultTradeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TRADE_LIST = "tradeList";

    @BindView(R.id.rv_list)
    RecyclerView listView;
    RecyclerRealTimeTradeAdapter mAdapter;
    private List<TradeData> tradeDataList;


    public SearchResultTradeFragment() {
        // Required empty public constructor
    }

    public static SearchResultTradeFragment newInstance(List<TradeData> tradeDataList) {
        SearchResultTradeFragment fragment = new SearchResultTradeFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRADE_LIST, (Serializable) tradeDataList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerRealTimeTradeAdapter();

        if (getArguments() != null) {
            tradeDataList = (List<TradeData>) getArguments().getSerializable(TRADE_LIST);
            if(tradeDataList != null) mAdapter.addAll(tradeDataList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_result_trade, container, false);
        ButterKnife.bind(this, view);
        listView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        mAdapter.setOnAdapterItemClickListener(new RecyclerRealTimeTradeAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, final TradeData tradeData, int position) {
                Intent intent = new Intent(getContext(), DetailTradeActivity.class);
                intent.putExtra("trade_id", tradeData.getTrade_id());
                startActivity(intent);
            }
        });
        return view;
    }

}
