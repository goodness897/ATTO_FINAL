package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.DetailMakerTwoActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerMakerAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.SearchMakerListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultMakerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultMakerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MAKER_LIST = "makerList";
    public final static String MAKER = "maker";

    RecyclerView listView;
    RecyclerMakerAdapter mAdapter;
    String keyword;


    public SearchResultMakerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchResultMakerFragment newInstance(String keyword) {
        SearchResultMakerFragment fragment = new SearchResultMakerFragment();
        Bundle args = new Bundle();
        args.putString(MAKER_LIST, keyword);
        Log.d("UnifiedSearchActivity", "Maker keyword : " + keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            keyword = getArguments().getString(MAKER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result_maker, container, false);
        listView = (RecyclerView) view.findViewById(R.id.rv_list);
        mAdapter = new RecyclerMakerAdapter();
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        mAdapter.setOnAdapterItemClickListener(new RecyclerMakerAdapter.OnAdapterItemClickLIstener() {

            @Override
            public void onAdapterItemClick(View view, MakerData makerItemData, int position) {
                Intent intent = new Intent(getContext(), DetailMakerTwoActivity.class);
                intent.putExtra(MAKER, makerItemData);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchMaker(keyword);

    }

    private void searchMaker(String keyword) {

        mAdapter.clear();
        SearchMakerListRequest request = new SearchMakerListRequest(getContext(), keyword);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<MakerData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<MakerData>> request, ListData<MakerData> result) {
                MakerData[] makerData = result.getData();
                if (makerData != null) {
                    Log.d("UnifiedSearchActivity", "maker 성공 : " + makerData[0].getMaker_name());
                    mAdapter.addAll(Arrays.asList(makerData));
                }

            }

            @Override
            public void onFail(NetworkRequest<ListData<MakerData>> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("UnifiedSearchActivity", "실패 : " + errorMessage);

            }
        });
    }

}
