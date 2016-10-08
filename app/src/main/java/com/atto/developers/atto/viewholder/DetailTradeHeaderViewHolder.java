package com.atto.developers.atto.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atto.developers.atto.R;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
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
 * Created by Tacademy on 2016-09-01.
 */
public class DetailTradeHeaderViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = DetailTradeHeaderViewHolder.class.getSimpleName();
    private static final int KEYWORD_COUNT = 3;


    @BindView(R.id.img_realtime_photo)
    ImageView mIvPhoto;

    @BindView(R.id.img_trade_profile)
    ImageView mIvProfile;

    @BindView(R.id.text_trade_status)
    TextView mTvStatus;

    @BindView(R.id.text_trade_title)
    TextView mTvTitle;

    @BindView(R.id.text_trade_content)
    TextView mTvContent;

    @BindView(R.id.text_trade_price)
    TextView mTvPrice;

    @BindView(R.id.text_trade_dday)
    TextView mTvDDay;

    @BindView(R.id.text_trade_nickname)
    TextView mTvNickName;

    @BindView(R.id.text_trade_limit_date)
    TextView mTvLimitDate;

    TradeListItemData tradeListItemData;
    TradeData tradeData;
    private Context mContext;


    public DetailTradeHeaderViewHolder(View itemView) {
        super(itemView);
        //this.mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }


    public void setTradeData(TradeData tradeData) {
        Log.e(TAG, "Trade Header ViewHolder: " + tradeData);

        String[] status = itemView.getContext().getResources().getStringArray(R.array.status);
        try {
            if (tradeData != null) {

                checkImageData(tradeData);
                checkDdaytest(tradeData);
                mTvStatus.setText(status[tradeData.getTrade_status() - 1]);
                mTvTitle.setText(tradeData.getTrade_title());
                mTvContent.setText(tradeData.getTrade_product_contents());
                int price = tradeData.getTrade_price();
                String sPrice = String.format("%,d", price);
                mTvPrice.setText(sPrice + "원");
                mTvNickName.setText(tradeData.getMember_info().getMember_alias());
                mTvLimitDate.setText(tradeData.getTrade_dtime());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkImageData(TradeData tradeData) {
        if (tradeData.getTrade_product_img() != null) {
            if (!TextUtils.isEmpty(tradeData.getTrade_product_img())) {
                Glide.with(itemView.getContext()).load(tradeData.getTrade_product_img()).centerCrop().into(mIvPhoto);
            } else {
                mIvPhoto.setImageResource(R.drawable.default_image);
            }
        }
        if (tradeData.getTrade_product_imges_info() != null) {
            if (!TextUtils.isEmpty(tradeData.getTrade_product_imges_info()[0])) {
                Glide.with(itemView.getContext()).load(tradeData.getTrade_product_imges_info()[0]).centerCrop().into(mIvPhoto);
            } else {
                mIvPhoto.setImageResource(R.drawable.default_image);
            }
        }
        if (tradeData.getMember_info().getMember_profile_img() != null) {
            if (!TextUtils.isEmpty(tradeData.getMember_info().getMember_profile_img())) {
                Glide.with(itemView.getContext()).load(tradeData.getMember_info().getMember_profile_img())
                        .bitmapTransform(new CropCircleTransformation(itemView.getContext())).into(mIvProfile);
            }
        }
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
            mTvDDay.setText("D+" + day);
        } else {
            mTvDDay.setText("D-" + day);
        }

    }
}