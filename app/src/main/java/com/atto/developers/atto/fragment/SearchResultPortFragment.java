package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.atto.developers.atto.request.SearchPortfolioListRequest;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultPortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultPortFragment extends Fragment implements AdapterView.OnItemClickListener {

    AsymmetricGridView listView;
    DemoAdapter adapter;


    public final static String TRADE_ID = "trade_id";


    private final DemoUtils demoUtils = new DemoUtils();

    private static final String PORT_LIST = "portList";
    private String keyword;

    public SearchResultPortFragment() {

    }

    public static SearchResultPortFragment newInstance(String keyword) {
        SearchResultPortFragment fragment = new SearchResultPortFragment();
        Bundle args = new Bundle();
        args.putString(PORT_LIST, keyword);
        Log.d("UnifiedSearchActivity", "Port keyword : " + keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyword = getArguments().getString(PORT_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result_port, container, false);
        ButterKnife.bind(this, view);
        listView = (AsymmetricGridView) view.findViewById(R.id.listView);

        // Choose your own preferred column width

        final List<DemoItem> items = new ArrayList<>();
        // initialize your items array
        adapter = new DefaultListAdapter(getContext());
        adapter.setItems(items);
        return view;

    }

    private void searchPortFolio() {

        SearchPortfolioListRequest request = new SearchPortfolioListRequest(getContext(), keyword, "1", "10");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<PortfolioData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<PortfolioData>> request, ListData<PortfolioData> result) {
                PortfolioData[] portfolioDatas = result.getData();
                if (portfolioDatas != null) {
                    if (portfolioDatas.length > 0) {
                        setPortfolioData(portfolioDatas);
                        adapter.setItems(demoUtils.morePortItems(portfolioDatas.length, Arrays.asList(portfolioDatas)));
                        Log.d("UnifiedSearchActivity", "포트폴리오 제목 : " + portfolioDatas[0].getPortfolio_title());
                    }
                }

            }

            @Override
            public void onFail(NetworkRequest<ListData<PortfolioData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("UnifiedSearchActivity", "실패 : " + errorMessage);

            }
        });
    }

    List<PortfolioData> portfolioDataList = new ArrayList<>();

    private void setPortfolioData(PortfolioData[] portfolioData) {
        this.portfolioDataList = Arrays.asList(portfolioData);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            keyword = getArguments().getString(PORT_LIST);
        }
        init();

    }

    private void init() {

        searchPortFolio();
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

    @Override
    public void onItemClick(@NotNull AdapterView<?> parent, @NotNull View view, int position, long id) {

        PortfolioData portfolioData = portfolioDataList.get(position);
        Intent intent = new Intent(getContext(), DetailPortActivity.class);
        intent.putExtra("portfolioData", portfolioData);
        startActivity(intent);

    }

}
