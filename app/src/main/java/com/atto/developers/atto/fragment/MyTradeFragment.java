package com.atto.developers.atto.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyTradeFragment extends Fragment {
    private static final String ARG_TRADE_DATA = "tradeData";

    @BindView(R.id.my_page_text_trade_title)
    TextView titleView;

    @BindView(R.id.my_page_text_trade_price)
    TextView priceView;

    @BindView(R.id.my_page_text_trade_dday)
    TextView dDayView;

    @BindView(R.id.my_page_text_trade_staus)
    TextView statusView;

    @BindView(R.id.my_page_product_img)
    ImageView productImageView;

    public MyTradeFragment() {
        // Required empty public constructor
    }

    public static MyTradeFragment newInstance(TradeData tradeData) {
        MyTradeFragment fragment = new MyTradeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRADE_DATA, tradeData);
        fragment.setArguments(args);
        return fragment;
    }

    TradeData tradeData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tradeData = (TradeData) getArguments().getSerializable(ARG_TRADE_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trade, container, false);
        ButterKnife.bind(this, view);


        Glide.with(getContext()).load(tradeData.getTrade_product_img()).centerCrop().into(productImageView);
        try {
            checkDdaytest(tradeData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        titleView.setText(tradeData.getTrade_title());
        int price = tradeData.getTrade_price();
        String s_price = String.format("%,d", price);
        priceView.setText(s_price + "원");
        statusView.setText(tradeData.getTrade_status() + "");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "id : " + tradeData.getTrade_id(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void checkDdaytest(TradeData tradeData) throws ParseException {
        Calendar toTime = Calendar.getInstance();
        long currentTiem = toTime.getTimeInMillis();
        SimpleDateFormat d = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        String tradeTime = tradeData.getTrade_dtime();
        Date trTime = d.parse(tradeTime);
        long futureTime = trTime.getTime();
        long diff = futureTime - currentTiem;
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        if (day < 0) {
            day = day * -1;
            dDayView.setText("D+" + day);
        } else {
            dDayView.setText("D-" + day);
        }

    }

    public TradeData getTradeData() {
        return tradeData;

    }

}
