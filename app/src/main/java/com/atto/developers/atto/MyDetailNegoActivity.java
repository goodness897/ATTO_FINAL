package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.atto.developers.atto.adapter.RecyclerDetailTradeAdapter;
import com.atto.developers.atto.adapter.RecyclerMyNegoAdapter;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.MyNegoCardListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.atto.developers.atto.MyApplication.getContext;

public class MyDetailNegoActivity extends AppCompatActivity {

    private static final String TAG = MyDetailNegoActivity.class.getSimpleName();

    @BindView(R.id.rv_list)
    RecyclerView listView;

    RecyclerMyNegoAdapter mAdapter;
    ProgressDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail_nego);
        ButterKnife.bind(this);
        initToolBar();
        mAdapter = new RecyclerMyNegoAdapter();
        dialogFragment = new ProgressDialogFragment();
        listView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));


        mAdapter.setOnAdapterItemClickListener(new RecyclerDetailTradeAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, NegoData negoData, int position) {

                Intent intent = new Intent(MyDetailNegoActivity.this, DetailNegoActivity.class);
                intent.putExtra("negoData", negoData);
                startActivity(intent);
                Toast.makeText(MyDetailNegoActivity.this, "position : " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        checkNegoData(1);

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("나의 협상카드");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void checkNegoData(int trade_id) {

        dialogFragment.show(getSupportFragmentManager(), "progress");
        MyNegoCardListRequest request = new MyNegoCardListRequest(this, String.valueOf(trade_id), "", "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<NegoData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<NegoData>> request, ListData<NegoData> result) {
                Log.e(TAG, "Nego onSuccess 성공 : " + result.getCode());
                NegoData[] data = result.getData();
                mAdapter.addAll(Arrays.asList(data));
                dialogFragment.dismiss();
            }

            @Override
            public void onFail(NetworkRequest<ListData<NegoData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(TAG, "Nego onFail 실패: " + errorMessage);
                dialogFragment.dismiss();
            }
        });
    }
}
