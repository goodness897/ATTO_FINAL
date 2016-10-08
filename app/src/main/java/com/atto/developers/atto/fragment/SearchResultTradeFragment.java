package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.DetailTradeActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerRealTimeTradeAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.request.SearchTradeListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;
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
    private String keyword;


    public SearchResultTradeFragment() {
        // Required empty public constructor
    }

    public static SearchResultTradeFragment newInstance(String keyword) {
        SearchResultTradeFragment fragment = new SearchResultTradeFragment();
        Bundle args = new Bundle();
        args.putString(TRADE_LIST, keyword);
        Log.d("UnifiedSearchActivity", "Trade keyword : " + keyword);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            keyword = getArguments().getString(TRADE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result_trade, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new RecyclerRealTimeTradeAdapter();
        listView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        mAdapter.setOnAdapterItemClickListener(new RecyclerRealTimeTradeAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, final TradeData tradeData, int position) {
                Intent intent = new Intent(getContext(), DetailTradeActivity.class);
                intent.putExtra("tradeData", tradeData);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchTrade();
    }

    private void searchTrade() {

        mAdapter.clear();
        SearchTradeListRequest request = new SearchTradeListRequest(getContext(), keyword, "1", "10");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {
                TradeData[] tradeDatas = result.getData();
                if (tradeDatas != null) {
                    Log.d("UnifiedSearchActivity", "trade 성공 : " + tradeDatas[0].getTrade_title());
                    mAdapter.addAll(Arrays.asList(tradeDatas));
                }
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("UnifiedSearchActivity", "실패 : " + errorMessage);

            }
        });
    }

}
