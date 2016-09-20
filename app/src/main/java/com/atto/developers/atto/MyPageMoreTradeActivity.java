package com.atto.developers.atto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.atto.developers.atto.adapter.RecyclerRealTimeTradeAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.MyTradeListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;

public class MyPageMoreTradeActivity extends AppCompatActivity {

    RecyclerRealTimeTradeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_more_trade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        initToolBar();
        initData();

        RecyclerView listView = (RecyclerView) findViewById(R.id.rv_list);
        mAdapter = new RecyclerRealTimeTradeAdapter();
        listView.setAdapter(mAdapter);
        listView.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView.setLayoutManager(manager);


    }

    private void initData() {

        MyTradeListRequest request = new MyTradeListRequest(this, "1", "50");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {

            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {
                TradeData[] tradeData = result.getData();
                mAdapter.add((TradeData) Arrays.asList(tradeData));

                Log.d(this.toString(), "성공 : " + tradeData[0].getTrade_id());
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(this.toString(), "실패 errorCode : " + errorCode);

            }
        });

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_mypage_more_trade);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
