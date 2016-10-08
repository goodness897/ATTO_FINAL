package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.BaseFragment;
import com.atto.developers.atto.DetailPortActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerPortfolioAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.MakerPortfolioListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;
import com.atto.developers.atto.view.ItemOffsetDecoration;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends BaseFragment {

    static final String TAG = "tag.PortfolioFragment";


    RecyclerView listView;
    RecyclerPortfolioAdapter mAdapter;
    private int makerId;


    private final static String PORTFOLIO = "portfolio";
    private final static String MAKER_ID = "makerId";
    ProgressDialogFragment dialogFragment;

    public PortfolioFragment() {
        // Required empty public constructor
    }

    public static PortfolioFragment newInstance(int makerId) {

        PortfolioFragment fragment = new PortfolioFragment();
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
        dialogFragment.show(getFragmentManager(), "portfolio_progress");
        mAdapter.clear();
        int pageNo = 1;
        int rowCount = 12;
        MakerPortfolioListRequest request = new MakerPortfolioListRequest(getContext(), String.valueOf(makerId), String.valueOf(pageNo), String.valueOf(rowCount));
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

    @Override
    public CharSequence getTitle(Resources r) {
        return "PORTFOLIO";
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
