package com.atto.developers.atto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.atto.developers.atto.fragment.SearchResultMakerFragment;
import com.atto.developers.atto.fragment.SearchResultPortFragment;
import com.atto.developers.atto.fragment.SearchResultTradeFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.request.PortfolioListRequest;
import com.atto.developers.atto.request.SearchMakerListRequest;
import com.atto.developers.atto.request.SearchTradeListRequest;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnifiedSearchActivity extends AppCompatActivity {

    @BindView(R.id.edit_input_search)
    AppCompatEditText inputSearchView;

    @BindView(R.id.pager)
    ViewPager pager;

    SearchResultPagerAdapter mAdapter;
    private NavigationTabStrip mNavigationTabStrip;

    private List<PortfolioData> portList = new ArrayList<>();
    private List<MakerData> makerList = new ArrayList<>();
    private List<TradeData> tradeList = new ArrayList<>();

    private final static String TAG = "UnifiedSearchActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unified_search);
        ButterKnife.bind(this);
        initToolBar();

    }
    private void setUI() {
        pager.setAdapter(mAdapter);
        mNavigationTabStrip.setTabIndex(0, true);
        mNavigationTabStrip.setViewPager(pager, 0);
        mNavigationTabStrip.setAnimationDuration(300);
        portList.clear();
        makerList.clear();
        tradeList.clear();
}

    @OnClick(R.id.btn_search)
    public void onSearch() {

        mAdapter = new SearchResultPagerAdapter(getSupportFragmentManager());
        mNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);

        setUI();
        searchMaker();
        searchPortFolio();
        searchTrade();
    }

    private void searchMaker() {
        String input = inputSearchView.getText().toString();
        SearchMakerListRequest request = new SearchMakerListRequest(this, input, "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<MakerData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<MakerData>> request, ListData<MakerData> result) {
                MakerData[] makerData = result.getData();
                Log.d(TAG, "maker 성공 : " + makerData[0].getMaker_name());
                makerList = Arrays.asList(makerData);
            }
            @Override
            public void onFail(NetworkRequest<ListData<MakerData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(TAG, "실패 : " + errorMessage);

            }
        });
    }
    private void searchPortFolio() {
        String input = inputSearchView.getText().toString();
        PortfolioListRequest request = new PortfolioListRequest(this, input, "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<PortfolioData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<PortfolioData>> request, ListData<PortfolioData> result) {
                PortfolioData[] portfolioDatas = result.getData();
                Log.d(TAG, "port 성공 : " + portfolioDatas[0].getPortfolio_id());
                portList = Arrays.asList(portfolioDatas);

            }

            @Override
            public void onFail(NetworkRequest<ListData<PortfolioData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(TAG, "실패 : " + errorMessage);

            }
        });
    }

    private void searchTrade() {
        String input = inputSearchView.getText().toString();
        SearchTradeListRequest request = new SearchTradeListRequest(this, input, "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {
                TradeData[] tradeDatas = result.getData();
                Log.d(TAG, "trade 성공 : " + tradeDatas[0].getTrade_product_contents());
                tradeList = Arrays.asList(tradeDatas);
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(TAG, "실패 : " + errorMessage);

            }
        });
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_unified_search);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class SearchResultPagerAdapter extends FragmentPagerAdapter {

        public static final int PAGE_COUNT = 3;

        public SearchResultPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SearchResultPortFragment.newInstance(portList);
                case 1:
                    return SearchResultTradeFragment.newInstance(tradeList);
                case 2:
                    return SearchResultMakerFragment.newInstance(makerList);
                default:
                    return SearchResultPortFragment.newInstance(portList);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "tab" + position;
        }
    }
}
