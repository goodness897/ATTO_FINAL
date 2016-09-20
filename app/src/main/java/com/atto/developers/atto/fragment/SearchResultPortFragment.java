package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
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
    List<PortfolioData> list = new ArrayList<>();


    public final static String TRADE_ID = "trade_id";


    private final DemoUtils demoUtils = new DemoUtils();

    private static final String PORT_LIST = "portList";

    public SearchResultPortFragment() {

    }

    public static SearchResultPortFragment newInstance(List<PortfolioData> portfolioDataList) {
        SearchResultPortFragment fragment = new SearchResultPortFragment();
        Bundle args = new Bundle();
        args.putSerializable(PORT_LIST, (Serializable) portfolioDataList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DefaultListAdapter(getContext());
        if (getArguments() != null) {
            list = (List<PortfolioData>) getArguments().getSerializable(PORT_LIST);
            if(list != null) adapter.setItems(demoUtils.morePortItems(list.size(), list));

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
        adapter.setItems(items);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        init();

    }

    private void init() {

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

        Intent intent = new Intent(getContext(), DetailPortActivity.class);
        intent.putExtra("portfolioData", list.get(position));
        startActivity(intent);
//        Toast.makeText(getContext(), "tradeId : " + tradeId, Toast.LENGTH_LONG).show();

    }

}
