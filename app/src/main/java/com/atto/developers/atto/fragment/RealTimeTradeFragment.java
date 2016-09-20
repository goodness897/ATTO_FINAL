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

import com.atto.developers.atto.AddTradeActivity;
import com.atto.developers.atto.DetailTradeActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerRealTimeTradeAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.TradeListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealTimeTradeFragment extends Fragment {

    @BindView(R.id.rv_list)
    RecyclerView listView;
    RecyclerRealTimeTradeAdapter mAdapter;

    public RealTimeTradeFragment() {
        // Required empty public constructor
    }

    public static RealTimeTradeFragment newInstance() {

        RealTimeTradeFragment fragment = new RealTimeTradeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_time_trade, container, false);
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

    @OnClick(R.id.btn_trade_add)
    void onAddTrade() {
        Intent intent = new Intent(getContext(), AddTradeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        final ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        dialogFragment.show(getFragmentManager(), "progress");
        mAdapter.clear();
        TradeListRequest request = new TradeListRequest(getContext(), "10", "10");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {
                TradeData[] data = result.getData();
                mAdapter.addAll(Arrays.asList(data));
                Log.d("RealTimeTradeFragment", "성공 : " + data[0].getTrade_product_img());
                Log.d("RealTimeTradeFragment", "성공 : " + data[0].getMember_info().getMember_profile_img());
                Log.d("RealTimeTradeFragment", "성공 : " + data[0].getTrade_product_contents());
                dialogFragment.dismiss();
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("RealTimeTradeFragment", "실패 : " + errorCode);
                dialogFragment.dismiss();
            }
        });
    }
}
