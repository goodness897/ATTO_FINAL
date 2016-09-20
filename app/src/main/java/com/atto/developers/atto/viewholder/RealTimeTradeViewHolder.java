package com.atto.developers.atto.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Tacademy on 2016-08-23.
 */
public class RealTimeTradeViewHolder extends RecyclerView.ViewHolder {

    private static final int KEYWORD_COUNT = 3;
    TextView[] keywordView = new TextView[3];

    @BindView(R.id.img_realtime_photo)
    ImageView realtime_photo;

    @BindView(R.id.img_maker_profile)
    ImageView trade_profile;

    @BindView(R.id.text_trade_status)
    TextView trade_status;

    @BindView(R.id.text_trade_title)
    TextView trade_title;

    @BindView(R.id.text_trade_price)
    TextView trade_price;

    @BindView(R.id.text_trade_dday)
    TextView trade_dday;

    @BindView(R.id.text_trade_limit_date)
    TextView trade_limit_date;

    TradeData tradeData;

    public interface OnTradeItemClickListener {
        public void onTradeItemClick(View view, TradeData tradeData, int position);
    }


    OnTradeItemClickListener listener;

    public void setOnTradeItemClickListener(OnTradeItemClickListener listener) {
        this.listener = listener;
    }


    public RealTimeTradeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onTradeItemClick(view, tradeData, getAdapterPosition());
                }
            }
        });
        keywordView[0] = (TextView) itemView.findViewById(R.id.text_trade_keyword_a);
        keywordView[1] = (TextView) itemView.findViewById(R.id.text_trade_keyword_b);
        keywordView[2] = (TextView) itemView.findViewById(R.id.text_trade_keyword_c);


    }

    public void setTradeData(TradeData tradeData) {
        this.tradeData = tradeData;

        if (tradeData.getTrade_key_word_info() != null) {
            Integer[] keywordList = tradeData.getTrade_key_word_info();
            checkKeywordList(keywordList);
        }
        String[] status = itemView.getContext().getResources().getStringArray(R.array.status);

        int dDay = 0;
        checkImageData(tradeData);
        try {
            dDay = checkDdaytest(tradeData.getTrade_dtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trade_status.setText(status[tradeData.getTrade_status()-1]);
        trade_title.setText(tradeData.getTrade_title());
        int price = tradeData.getTrade_price();
        String s_price = String.format("%,d", price);
        trade_price.setText(s_price + "원");
        if(dDay < 0) {
            dDay = dDay * -1;
            trade_dday.setText("D+" + dDay);
        } else {
            trade_dday.setText("D-" + dDay);
        }

//        trade_nickname.setText(tradeData.getMember_info().getMember_alias());
        trade_limit_date.setText(tradeData.getTrade_dtime());
        Integer[] keywordList = tradeData.getTrade_key_word_info();
        checkKeywordList(keywordList);

    }

    private void checkImageData(TradeData tradeData) {

        if (tradeData.getTrade_product_img() != null) {

            Glide.with(itemView.getContext()).load(tradeData.getTrade_product_img()).centerCrop().into(realtime_photo);
        } else {
            Glide.with(itemView.getContext()).load(R.drawable.default_image).centerCrop().into(realtime_photo);
        }

        if (tradeData.getMember_info().getMember_profile_img() != null) {

            Glide.with(itemView.getContext()).load(tradeData.getMember_info().getMember_profile_img())
                    .bitmapTransform(new CropCircleTransformation(itemView.getContext())).into(trade_profile);
        } else {
            trade_profile.setImageResource(R.drawable.sample_profile);

        }

    }

    private int checkDdaytest(String trade_dtime) throws ParseException {
        Calendar toTime = Calendar.getInstance();
        long currentTiem = toTime.getTimeInMillis(); //롱탑?
        SimpleDateFormat d = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Date trTime = d.parse(trade_dtime); //스트링 -> date변환X
        long futureTime = trTime.getTime();
        long diff = futureTime - currentTiem;
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        return day;
    }

    private void checkKeywordList(Integer[] keywordList) {
        if (keywordList != null) {
            for (int i = 0; i < keywordList.length; i++) {
                Log.i("realtime", "keywordList : " + keywordList[i]);
                if (keywordList[i] != 0) {
                    keywordView[i].setText(keywordList[i] + "");
                } else {
                    keywordView[i].setVisibility(View.GONE);
                }
            }
        } else {
            for (int i = 0; i < KEYWORD_COUNT; i++) {
                keywordView[i].setVisibility(View.GONE);
            }
        }
    }

}
