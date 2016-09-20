package com.atto.developers.atto.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atto.developers.atto.MakeOrderActivity;
import com.atto.developers.atto.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakerOrderDialogFragment extends DialogFragment {


    public MakerOrderDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_maker_order_dialog, container, false);
        ButterKnife.bind(this,view);
        return view;

    }


        @OnClick(R.id.btn_positive)
        public void onPositive(){
            Intent intent = new Intent(getContext(), MakeOrderActivity.class);
            startActivity(intent);
            dismiss();
        }


        @OnClick(R.id.btn_negative)
            public void onNegative(){
                dismiss();
            }

}
