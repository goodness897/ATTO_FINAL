package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.DetailTradeRequest;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MakeOrderMakerActivity extends AppCompatActivity {
    private static final String TAG = MakeOrderActivity.class.getSimpleName();

    @BindView(R.id.img_consumer_profile)
    ImageView makeConsumerProfile;
    @BindView(R.id.text_consumer_nickname)
    TextView makeConsumerName;

    @BindView(R.id.img_realtime_photo)
    ImageView makeTradePhoto;
    @BindView(R.id.text_trade_title)
    TextView makeTradeTitle;
    @BindView(R.id.text_trade_limit_date)
    TextView makeTradeIimitData;
    @BindView(R.id.text_trade_price)
    TextView makeTradePrice;
    @BindView(R.id.text_trade_content)
    TextView makeTradeContent;


    @BindView(R.id.text_set_address)
    TextView addressView;
    @BindView(R.id.text_set_postcode)
    TextView postCodeView;


    int negoId = -1;
    int tradeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order_maker);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tradeId = intent.getIntExtra("trade_Id", -1);
        negoId = intent.getIntExtra("nego_Id", -1);
        Log.e(TAG, "MakeOrder_getIntExtrad1 : " + tradeId + " / " + negoId);
        initData(tradeId, negoId);

    }

    private void initData(int tradeId, int negoId) {
        Log.e(TAG, "MakeOrder_getIntExtrad2 : " + tradeId + " / " + negoId);
        makeOrderTradeData(tradeId, negoId);
    }


    private void makeOrderTradeData(int tradeId, int negoId) {
        DetailTradeRequest request = new DetailTradeRequest(this, String.valueOf(tradeId), "11", "10", "10");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<TradeListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<TradeListItemData> request, TradeListItemData result) {
                Log.e(TAG, "MakeOrder_Trade 성공 : " + result.getData());
                TradeData tradeData = result.getData();
                setMakeTrade(tradeData);
            }

            @Override
            public void onFail(NetworkRequest<TradeListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(TAG, "MakeOrder_Trade 실패 : " + errorMessage);
            }
        });

    }

    private void setMakeTrade(TradeData tradeData) {
        if (tradeData != null) {
            Log.e(TAG, "makeOrder_tradeData: " + tradeData);
            Glide.with(this).load(tradeData.getMember_info().getMember_profile_img()).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(makeConsumerProfile);
            makeConsumerName.setText(tradeData.getMember_info().getMember_alias());
            makeOrderImage(tradeData);
            makeTradeIimitData.setText(tradeData.getTrade_dtime());
            makeTradeTitle.setText(tradeData.getTrade_title());
            makeTradeContent.setText(tradeData.getTrade_product_contents());
            makeTradePrice.setText(String.valueOf(tradeData.getTrade_price()));
        }
    }

    private void makeOrderImage(TradeData tradeData) {
        if (tradeData != null) {
            if (tradeData.getTrade_product_img() != null) {
                Glide.with(this).load(tradeData.getTrade_product_img()).centerCrop().into(makeTradePhoto);
            } else if (tradeData.getTrade_product_imges_info() != null) {
                Glide.with(this).load(tradeData.getTrade_product_imges_info()).centerCrop().into(makeTradePhoto);
            }
        }
    }

    @OnClick(R.id.btn_update_order)
    public void onClick() {
    }

}
