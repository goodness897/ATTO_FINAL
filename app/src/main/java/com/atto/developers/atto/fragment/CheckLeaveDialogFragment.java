package com.atto.developers.atto.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.AccountLeaveActivity;
import com.atto.developers.atto.R;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.request.MemberLeaveRequest;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckLeaveDialogFragment extends DialogFragment {


    public CheckLeaveDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_leave_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @OnClick(R.id.btn_check_complete)
    public void onCheckComplete() {

        AccountLeaveActivity callActivity = (AccountLeaveActivity) getActivity();
        MemberLeaveRequest request = new MemberLeaveRequest(getContext());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
            @Override
            public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                Log.d("CheckDialogFragment", "성공 : " + result.getMessage());
                PropertyManager.getInstance().setEmail("");
                PropertyManager.getInstance().setPassword("");
            }
            @Override
            public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("CheckDialogFragment", "실패 : " + errorCode);
            }
        });
        dismiss();
        callActivity.startIntent();

    }

    @OnClick(R.id.btn_cancel)
    public void onCancel() {
        dismiss();

    }
}
