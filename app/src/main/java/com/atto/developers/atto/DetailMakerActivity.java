package com.atto.developers.atto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.atto.developers.atto.adapter.RecyclerDetailMakerAdapter;
import com.atto.developers.atto.fragment.MakerFragment;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.makerdata.MakerListItemData;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.DetailMakerRequest;
import com.atto.developers.atto.request.MakerPortfolioListRequest;
import com.atto.developers.atto.view.ItemOffsetDecoration;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMakerActivity extends AppCompatActivity {

    private static final int REQUEST_FOR_PORTFOLIO = 1000;

    @BindView(R.id.rv_list)
    RecyclerView listView;

    RecyclerDetailMakerAdapter mAdapter;
    public static final String MAKER_PORT_ID = "maker_port_id";
    private String makerNickName;
    private int maker_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maker);
        ButterKnife.bind(this);
        initToolBar();

        Intent intent = getIntent();
        maker_id = intent.getIntExtra(MakerFragment.MAKER_ID, -1);
        Log.d("DetailMakerActivity", "makerId : " + maker_id);
        makerNickName = PropertyManager.getInstance().getNickName();
        Log.d("DetailMakerActivity", "닉네임 : " + makerNickName);

        mAdapter = new RecyclerDetailMakerAdapter();
        mAdapter.clear();

        listView.setAdapter(mAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        listView.addItemDecoration(itemDecoration);
        listView.setLayoutManager(manager);

        mAdapter.setOnAdapterItemClickListener(new RecyclerDetailMakerAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, PortfolioData portfolioData, int position) {

                Intent intent = new Intent(DetailMakerActivity.this, DetailPortActivity.class);
                intent.putExtra("portfolioData", portfolioData);
//                Toast.makeText(DetailMakerActivity.this, "position : " + position, Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        initData(maker_id);

    }


    @Override
    protected void onResume() {
        super.onResume();
//        dialogFragment.dismiss();
        Log.d("DetailMakerActivity", "onResume 실행");

    }

    ProgressDialogFragment dialogFragment = new ProgressDialogFragment();

    private void initData(int maker_id) {

        dialogFragment.show(getSupportFragmentManager(), "progress");
        detailMakerRequest(maker_id);
        listPortFolioData(maker_id);
    }

    private void detailMakerRequest(int tid) {

        DetailMakerRequest request = new DetailMakerRequest(this, String.valueOf(tid));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<MakerListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<MakerListItemData> request, MakerListItemData result) {
                MakerData data = result.getData();
                if (data != null) {
                    if (data.getMaker_name().equals(makerNickName)) {
                        Log.d("DetailMakerActivity", "제작자 동일 : " + data.getMaker_id());
                        mAdapter.setMakerData(data, true);
                    } else {
                        Log.d("DetailMakerActivity", "제작자 다름 : " + data.getMaker_id());
                        mAdapter.setMakerData(data, false);
                    }
                }
                Log.d("DetailMakerActivity", "성공 result : " + data.getMaker_id());
            }

            @Override
            public void onFail(NetworkRequest<MakerListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("DetailMakerActivity", "실패 : " + errorMessage);

            }
        });

    }

    private void listPortFolioData(int tid) {

        int pageNo = 1;
        int rowCount = 50;
        MakerPortfolioListRequest request = new MakerPortfolioListRequest(this, String.valueOf(tid), String.valueOf(pageNo), String.valueOf(rowCount));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<PortfolioData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<PortfolioData>> request, ListData<PortfolioData> result) {
                PortfolioData[] portfolioDatas = result.getData();
                if (portfolioDatas != null) {
                    mAdapter.addAll(Arrays.asList(portfolioDatas));
                    Log.d("DetailMakerActivity", portfolioDatas[0].getPortfolio_img());
                    dialogFragment.dismiss();

                }
            }

            @Override
            public void onFail(NetworkRequest<ListData<PortfolioData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("DetailMakerActivity", "실패 : " + errorMessage);
                dialogFragment.dismiss();
            }
        });

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_detail_maker);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case REQUEST_FOR_PORTFOLIO:
                if (resultCode == RESULT_OK) {
                    PortfolioData portfolioData = (PortfolioData) intent.getSerializableExtra("portfolio");
                    mAdapter.add(portfolioData);
                    Log.d("DetailMakerActivity", "성공 : " + portfolioData.getPortfolio_title());
                }
                break;
        }
    }


}
