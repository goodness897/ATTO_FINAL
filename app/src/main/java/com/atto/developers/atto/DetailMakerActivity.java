package com.atto.developers.atto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.atto.developers.atto.adapter.RecyclerDetailMakerAdapter;
import com.atto.developers.atto.fragment.MakerFragment;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.MakerPortfolioListRequest;
import com.atto.developers.atto.view.ItemOffsetDecoration;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMakerActivity extends AppCompatActivity {

    private static final int REQUEST_FOR_PORTFOLIO = 1000;
    private static final int REQUEST_MAKER_UPDATE = 2000;

    private static final String TAG = DetailMakerActivity.class.getSimpleName();


    @BindView(R.id.rv_list)
    RecyclerView listView;

    RecyclerDetailMakerAdapter mAdapter;
    public static final String MAKER_PORT_ID = "maker_port_id";
    private String makerNickName;
    private int maker_id;
    private MakerData makerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maker);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        makerData = (MakerData)intent.getSerializableExtra(MakerFragment.MAKER);
        makerNickName = PropertyManager.getInstance().getNickName();
        Log.d("DetailMakerActivity", "저장 닉네임 : " + makerNickName);
        Log.d("DetailMakerActivity", "메이커 닉네임 : " + makerData.getMaker_name());
        maker_id = makerData.getMaker_id();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
//        dialogFragment.dismiss();
        initData(maker_id);
        Log.d("DetailMakerActivity", "onResume 실행");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (makerData != null) {
            if (makerData.getMaker_name().equals(PropertyManager.getInstance().getNickName())) {
                getMenuInflater().inflate(R.menu.activity_maker_ud_menu, menu);
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
            Intent intent = new Intent(DetailMakerActivity.this, UpdateMakerActivity.class);
            if (makerData != null) {
                intent.putExtra("makerData", makerData);
                startActivityForResult(intent, REQUEST_MAKER_UPDATE);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    ProgressDialogFragment dialogFragment = new ProgressDialogFragment();

    private void initData(int maker_id) {
        initToolBar();

        dialogFragment.show(getSupportFragmentManager(), "progress");

        if (makerData != null) {
            if (makerData.getMaker_name().equals(makerNickName)) {
                Log.d("DetailMakerActivity", "제작자 동일 : " + makerData.getMaker_id());
                mAdapter.setMakerData(makerData, true);
            } else {
                Log.d("DetailMakerActivity", "제작자 다름 : " + makerData.getMaker_id());
                mAdapter.setMakerData(makerData, false);
            }
        }
        listPortFolioData(maker_id);
    }

    private void listPortFolioData(int maker_id) {

        mAdapter.clear();
        int pageNo = 1;
        int rowCount = 12;
        MakerPortfolioListRequest request = new MakerPortfolioListRequest(this, String.valueOf(maker_id), String.valueOf(pageNo), String.valueOf(rowCount));
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
            case REQUEST_MAKER_UPDATE:
                if (resultCode == RESULT_OK) {
                    makerData = (MakerData) intent.getSerializableExtra("makerData");
                    Log.d(TAG, "content : " + makerData.getMaker_line_tag());
                    Log.d(TAG, "image[1] : " + makerData.getMaker_representation_img());
                    mAdapter.setMakerData(makerData, true);
                    dialogFragment.dismiss();

                }
                break;
        }
    }

}
