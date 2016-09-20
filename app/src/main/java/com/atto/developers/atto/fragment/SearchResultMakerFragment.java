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

import com.atto.developers.atto.DetailMakerActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerMakerAdapter;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultMakerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultMakerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MAKER_LIST = "makerList";

    RecyclerView listView;
    RecyclerMakerAdapter mAdapter;
    List<MakerData> list;


    public SearchResultMakerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchResultMakerFragment newInstance(List<MakerData> makerDataList) {
        SearchResultMakerFragment fragment = new SearchResultMakerFragment();
        Bundle args = new Bundle();
        args.putSerializable(MAKER_LIST, (Serializable) makerDataList);
        Log.d("UnifiedSearchActivity", "확인 : " + makerDataList.get(0).getMaker_name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerMakerAdapter();

        if (getArguments() != null) {
            list = (List<MakerData>) getArguments().getSerializable(MAKER_LIST);
            Log.d("UnifiedSearchActivity", "확인 : " + list.get(0).getMaker_name());
            mAdapter.addAll(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result_maker, container, false);
        listView = (RecyclerView) view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        mAdapter.setOnAdapterItemClickListener(new RecyclerMakerAdapter.OnAdapterItemClickLIstener() {

            @Override
            public void onAdapterItemClick(View view, MakerData makerItemData, int position) {
                Intent intent = new Intent(getContext(), DetailMakerActivity.class);
                intent.putExtra("MAKER_ID", makerItemData.getMaker_id());
                Log.d("UnifiedSearchActivity", "MAKER_ID : " + makerItemData.getMaker_id());
                startActivity(intent);
            }
        });

        return view;
    }

}
