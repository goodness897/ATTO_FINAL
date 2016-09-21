package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.atto.developers.atto.adapter.RecyclerDetailTradeAdapter;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.DeleteTradeRequest;
import com.atto.developers.atto.request.DetailTradeRequest;
import com.atto.developers.atto.request.NegoCardListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailTradeActivity extends AppCompatActivity {

    private static final int REQUEST_UPDATE = 1000;
    private Unbinder mUnbinder;
    private static final String TAG = DetailTradeActivity.class.getSimpleName();

    @BindView(R.id.re_list)
    RecyclerView mListView;
    RecyclerDetailTradeAdapter mAdapter;
    @BindView(R.id.btn_move_nego_register)
    Button registerButton;

    int tradeId;
    TradeData tradeData;
    ProgressDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_trade);
        mUnbinder = ButterKnife.bind(this);
        mAdapter = new RecyclerDetailTradeAdapter();
        mDialogFragment = new ProgressDialogFragment();

        Intent intent = getIntent();
        tradeData = (TradeData) intent.getSerializableExtra("tradeData");
        tradeId = tradeData.getTrade_id();
        init(tradeData);

        int auth = PropertyManager.getInstance().getKeyAuth();
        checkAuth(auth);
/*
        if (trade_id != -1) {
            init(trade_id);
            setTradeId(trade_id);
        } else if (tradeData != null) {
            init(tradeData);
            setTradeId(tradeData.getTrade_id());
        }
        */
        Log.d(TAG, "닉네임 1 : " + tradeData.getMember_info().getMember_alias());
        Log.d(TAG, "닉네임 2 : " + PropertyManager.getInstance().getNickName());
        Log.d(TAG, "아이디 : " + tradeData.getTrade_id());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume 실행");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (tradeData != null) {
            if (tradeData.getMember_info().getMember_alias().equals(PropertyManager.getInstance().getNickName())) {
                getMenuInflater().inflate(R.menu.activity_ud_menu, menu);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intent = new Intent(DetailTradeActivity.this, UpdateTradeActivity.class);
            if (tradeData != null) {
                intent.putExtra("tradeData", tradeData);
                startActivityForResult(intent, REQUEST_UPDATE);
            }

        } else if (id == R.id.action_delete) {
            DeleteTradeRequest request = new DeleteTradeRequest(this, String.valueOf(tradeData.getTrade_id()));
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    Toast.makeText(DetailTradeActivity.this, "성공 : " + result.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(DetailTradeActivity.this, "실패 : " + errorMessage, Toast.LENGTH_LONG).show();

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }


    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    private void checkAuth(int auth) {

        if (auth == 0) { // 소비자 일때
            registerButton.setVisibility(View.GONE);
        } else {
            registerButton.setVisibility(View.VISIBLE);
        }
    }

    private void init(int trade_id) {
        initToolBar();
        mDialogFragment.show(getSupportFragmentManager(), "detail_trade");
        mAdapter.setOnAdapterItemClickListener(new RecyclerDetailTradeAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, NegoData negoData, int position) {
                Intent intent = new Intent(DetailTradeActivity.this, DetailNegoActivity.class);
                intent.putExtra("tradeId", tradeId);
                intent.putExtra("negoId", negoData.getNegotiation_id());
                startActivity(intent);

            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        initData(trade_id);
    }

    private void init(TradeData tradeData) {
        initToolBar();

        mDialogFragment.show(getSupportFragmentManager(), "detail_trade");
        mAdapter.setOnAdapterItemClickListener(new RecyclerDetailTradeAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, NegoData negoData, int position) {
                Intent intent = new Intent(DetailTradeActivity.this, DetailNegoActivity.class);
                intent.putExtra("tradeId", tradeId);
                intent.putExtra("negoData", negoData);
                startActivity(intent);
            }
        });

        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        initData(tradeData);
    }

    private void initData(TradeData tradeData) {
        mAdapter.setTradeData(tradeData);
        checkNegoData(tradeData.getTrade_id());

    }

    private void initData(int trade_id) {
        mAdapter.setTradeData(tradeData);
//        checkTradeData(trade_id);
        checkNegoData(trade_id);
    }

    @OnClick(R.id.btn_move_nego_register)
    public void onMoveAddNego() {
        Intent intent = new Intent(DetailTradeActivity.this, AddNegoActivity.class);
        intent.putExtra("tradeId", tradeId);
        startActivity(intent);
        finish();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_detail_trade);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkTradeData(final int trade_Id) {

        DetailTradeRequest request = new DetailTradeRequest(this, String.valueOf(trade_Id), "", "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<TradeListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<TradeListItemData> request, TradeListItemData result) {
                Log.e(TAG, " Trade onSuccess 성공 : " + result.getData().getTrade_product_imges_info());
                TradeData tradeData = result.getData();
                mAdapter.setTradeData(tradeData);

            }

            @Override
            public void onFail(NetworkRequest<TradeListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(TAG, "Trade onFail 실패: " + errorCode);
                mDialogFragment.dismiss();
            }
        });
    }

    private void checkNegoData(int trade_id) {

        NegoCardListRequest request = new NegoCardListRequest(this, String.valueOf(trade_id), "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<NegoData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<NegoData>> request, ListData<NegoData> result) {
                Log.e(TAG, "Nego onSuccess 성공 : " + result.getCode());
                NegoData[] data = result.getData();
                mAdapter.addAll(Arrays.asList(data));
                mDialogFragment.dismiss();
            }

            @Override
            public void onFail(NetworkRequest<ListData<NegoData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(TAG, "Nego onFail 실패: " + errorMessage);
                mDialogFragment.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_UPDATE:
                if (resultCode == RESULT_OK) {
                    tradeData = (TradeData) intent.getSerializableExtra("tradeData");
                    Log.d(TAG, "content : " + tradeData.getTrade_product_contents());
                    Log.d(TAG, "image[1] : " + tradeData.getTrade_product_imges_info()[0]);
                    mAdapter.setTradeData(tradeData);

                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause 실행");

    }
}