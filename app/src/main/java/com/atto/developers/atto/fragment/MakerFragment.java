
package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atto.developers.atto.DetailMakerTwoActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.adapter.RecyclerMakerAdapter;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.atto.developers.atto.request.MakerListRequest;
import com.atto.developers.atto.view.DividerItemDecoration;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */

public class MakerFragment extends Fragment {

    RecyclerView listView;
    RecyclerMakerAdapter mAdapter;


    public final static String MAKER = "maker";
    ProgressDialogFragment dialogFragment;

    public MakerFragment() {
        // Required empty public constructor
    }

    public static MakerFragment newInstance() {

        MakerFragment fragment = new MakerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maker, container, false);

        listView = (RecyclerView) view.findViewById(R.id.rv_list);
        mAdapter = new RecyclerMakerAdapter();
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        listView.setLayoutManager(manager);
        listView.addItemDecoration(
                new DividerItemDecoration(getContext(), R.drawable.divider));
        listView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnAdapterItemClickListener(new RecyclerMakerAdapter.OnAdapterItemClickLIstener() {

            @Override
            public void onAdapterItemClick(View view, MakerData makerItemData, int position) {
                Intent intent = new Intent(getContext(), DetailMakerTwoActivity.class);
                intent.putExtra(MAKER, makerItemData);
                startActivity(intent);
            }
        });

        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }



    private void initData() {


        MakerListRequest request = new MakerListRequest(getContext(), "1", "10");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ListData<MakerData>>() {
            @Override
            public void onSuccess(NetworkRequest<ListData<MakerData>> request, ListData<MakerData> result) {
                MakerData[] data = result.getData();
                if(data != null) {
                    Log.d("MakerFragment", String.valueOf(data[0].getMaker_score()));
                    Log.d("MakerFragment", String.valueOf(data[0].getMaker_id()));
                    mAdapter.clear();
                    mAdapter.addAll(Arrays.asList(data));
                }

            }

            @Override
            public void onFail(NetworkRequest<ListData<MakerData>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "실패 : " + errorCode, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
