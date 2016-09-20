package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.atto.developers.atto.DetailPortActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.asymmetricgridview.DefaultListAdapter;
import com.atto.developers.atto.asymmetricgridview.DemoAdapter;
import com.atto.developers.atto.asymmetricgridview.DemoItem;
import com.atto.developers.atto.asymmetricgridview.DemoUtils;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.request.PortfolioListRequest;
import com.atto.developers.atto.request.TradeListRequest;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttoFragment extends Fragment implements AdapterView.OnItemClickListener {

    AsymmetricGridView listView;
    DemoAdapter adapter;

    public final static String TRADE_ID = "trade_id";


    private final DemoUtils demoUtils = new DemoUtils();

    public final static String PORTFOLIO= "portfolio";


    public AttoFragment() {
        // Required empty public constructor
    }

    public static AttoFragment newInstance() {

        AttoFragment fragment = new AttoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atto, container, false);

        listView = (AsymmetricGridView) view.findViewById(R.id.listView);

        // Choose your own preferred column width

        final List<DemoItem> items = new ArrayList<>();
        // initialize your items array
        adapter = new DefaultListAdapter(getContext());
        adapter.setItems(items);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();

    }

    private void init() {

//        getDataRequest();
        getPortDataRequest();
        demoUtils.currentOffset = 0;
        listView.setAllowReordering(true);
        listView.setRequestedHorizontalSpacing(Utils.dpToPx(getContext(), 3));
        listView.setDebugging(true);
        listView.setOnItemClickListener(this);
        setNumColumns(3);

    }

    private AsymmetricGridViewAdapter getNewAdapter() {
        return new AsymmetricGridViewAdapter(getContext(), listView, adapter);
    }

    private void setNumColumns(int numColumns) {
        listView.setRequestedColumnCount(numColumns);
        listView.determineColumns();
        listView.setAdapter(getNewAdapter());
    }

    List<TradeData> tradeDataList = new ArrayList<>();
    List<PortfolioData> portfolioDataList = new ArrayList<>();


    @Override
    public void onItemClick(@NotNull AdapterView<?> parent, @NotNull View view, int position, long id) {

        PortfolioData portfolioData = portfolioDataList.get(position);
        Intent intent = new Intent(getContext(), DetailPortActivity.class);
        intent.putExtra("portfolioData", portfolioData);
        startActivity(intent);
//        int tradeId = tradeDataList.get(position).getTrade_id();
//        Intent intent = new Intent(getContext(), DetailPortActivity.class);
//        intent.putExtra(TRADE_ID, tradeId);
//        startActivity(intent);
//        Toast.makeText(getContext(), "tradeId : " + tradeId, Toast.LENGTH_LONG).show();

    }
    public void getDataRequest() {
        TradeListRequest request = new TradeListRequest(getContext(), "1", "30");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<TradeData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<TradeData>> request, ListData<TradeData> result) {
                TradeData[] tradeDatas = result.getData();
                if (tradeDatas != null) {
                    if (tradeDatas.length > 0) {
                        setTradeData(tradeDatas);
                        adapter.setItems(demoUtils.moreItems(tradeDatas.length, Arrays.asList(tradeDatas)));
                    }
                }
            }

            @Override
            public void onFail(NetworkRequest<ListData<TradeData>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    public void getPortDataRequest() {
        String page_no = "1";
        String row_count = "30";
        PortfolioListRequest request = new PortfolioListRequest(getContext(), page_no, row_count);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<PortfolioData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<PortfolioData>> request, ListData<PortfolioData> result) {
                PortfolioData[] portfolioDatas = result.getData();
                if (portfolioDatas != null) {
                    if(portfolioDatas.length >0) {
                        setPortfolioData(portfolioDatas);
                        adapter.setItems(demoUtils.morePortItems(portfolioDatas.length, Arrays.asList(portfolioDatas)));
                        Log.d("AttoFragment", "포트폴리오 제목 : " + portfolioDatas[0].getPortfolio_title());
                    }
                }
            }
            @Override
            public void onFail(NetworkRequest<ListData<PortfolioData>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "실패 : " + errorMessage, Toast.LENGTH_LONG).show();

            }
        });

    }
    private void setTradeData(TradeData[] tradeDatas) {
        this.tradeDataList = Arrays.asList(tradeDatas);
    }

    private void setPortfolioData(PortfolioData[] portfolioData) {
        this.portfolioDataList = Arrays.asList(portfolioData);
    }

}
