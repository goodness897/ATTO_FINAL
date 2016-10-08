package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.BaseFragment;
import com.atto.developers.atto.DetailPortActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerPortfolioAdapter;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.view.DividerItemDecoration;
import com.atto.developers.atto.view.ItemOffsetDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteTradeFragment extends BaseFragment {

    static final String TAG = "tag.PortfolioFragment";


    RecyclerView listView;
    RecyclerPortfolioAdapter mAdapter;
    private int makerId;


    private final static String PORTFOLIO = "portfolio";
    private final static String MAKER_ID = "makerId";
    ProgressDialogFragment dialogFragment;

    public CompleteTradeFragment() {
        // Required empty public constructor
    }

    public static CompleteTradeFragment newInstance(int makerId) {

        CompleteTradeFragment fragment = new CompleteTradeFragment();
        Bundle args = new Bundle();
        args.putInt(MAKER_ID, makerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            makerId = getArguments().getInt(MAKER_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        listView = (RecyclerView) view.findViewById(R.id.rv_list);
        mAdapter = new RecyclerPortfolioAdapter();
        listView.setAdapter(mAdapter);

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        listView.addItemDecoration(itemDecoration);
        listView.setLayoutManager(manager);
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        listView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnAdapterItemClickListener(new RecyclerPortfolioAdapter.OnAdapterItemClickLIstener() {
            @Override
            public void onAdapterItemClick(View view, PortfolioData portfolioData, int position) {

                Intent intent = new Intent(getContext(), DetailPortActivity.class);
                intent.putExtra("portfolioData", portfolioData);
//                Toast.makeText(DetailMakerActivity.this, "position : " + position, Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listPortFolioData(makerId);
    }



    private void listPortFolioData(int makerId) {

        dialogFragment = new ProgressDialogFragment();
        mAdapter.clear();
        int pageNo = 1;
        int rowCount = 12;

    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "판매완료";
    }

    @Override
    public String getSelfTag() {
        return TAG;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return false;
    }
}
