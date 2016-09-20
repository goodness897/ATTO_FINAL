package com.atto.developers.atto.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atto.developers.atto.R;

/**
 * Created by Tacademy on 2016-08-25.
 */
//실시간 거래 상세 글에서 물건에 등록 된 리사이클 제작자 아이템 뷰 홀더
public class DetailTradeMultiViewHolder extends RecyclerView.ViewHolder {

    ImageView img_maker_profile;
    TextView text_trade_remain_time, text_trade_profile_nickname, offer_pice, limit_date, text_trade_dday;
    RatingBar ratingbar_maker_grade;
    Button btn_move_nego_register;

    public DetailTradeMultiViewHolder(View itemView) {
        super(itemView);

        img_maker_profile = (ImageView)itemView.findViewById(R.id.img_maker_profile);
        text_trade_remain_time = (TextView) itemView.findViewById(R.id.text_trade_remain_time);
//        text_trade_profile_nickname = (TextView)itemView.findViewById(R.id.text_trade_profile_nickname);
        offer_pice = (TextView)itemView.findViewById(R.id.offer_price);
        limit_date = (TextView)itemView.findViewById(R.id.limit_date);
        text_trade_dday = (TextView)itemView.findViewById(R.id.text_trade_dday);
        ratingbar_maker_grade = (RatingBar)itemView.findViewById(R.id.ratingbar_maker_grade);
        btn_move_nego_register = (Button)itemView.findViewById(R.id.btn_move_nego_register);

    }
}
