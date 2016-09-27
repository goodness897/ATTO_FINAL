package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.negodata.NegoListItemData;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.request.DetailNegoRequest;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MakeOrderConsumerActivity extends AppCompatActivity {
    private static final String TAG = MakeOrderConsumerActivity.class.getSimpleName();

    @BindView(R.id.img_maker_profile)
    ImageView makeMakerProfile;
    @BindView(R.id.text_maker_nickname)
    TextView makeMakerName;
    @BindView(R.id.ratingbar_maker_grade)
    RatingBar makeMakerGrade;
    @BindView(R.id.ratingbar_maker_grade_text)
    TextView makeMakerGradeText;

    @BindView(R.id.img_realtime_photo)
    ImageView makeTradePhoto;
    @BindView(R.id.text_trade_limit_date)
    TextView makeTradeIimitData;
    @BindView(R.id.text_trade_content)
    TextView makeTradeContent;
    @BindView(R.id.text_trade_price)
    TextView makeTradePrice;

    @BindView(R.id.text_set_address)
    TextView addressView;
    @BindView(R.id.text_set_postcode)
    TextView postCodeView;

    int negoId = -1;
    int tradeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order_consumer);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        negoId = intent.getIntExtra("negoId", -1);
        tradeId = intent.getIntExtra("tradeId", -1);
        Log.e(TAG, "MakeOrder_consumer_getIntExtrad1 : " + tradeId + " / " + negoId);
        //  addressData = intent.getStringExtra("address");
        //codeData = intent.getStringExtra("postcode");

        initData(tradeId, negoId);
    }

    private void initData(int tradeId, int negoId) {
        makeOrderMakerData(tradeId, negoId);
        Log.e(TAG, "MakeOrder_getIntExtrad2 : " + tradeId + " / " + negoId);
        //  makeOrderTradeData(tradeId, negoId);
    }


    private void makeOrderMakerData(final int tradeId, final int negoId) {
        DetailNegoRequest request = new DetailNegoRequest(this, String.valueOf(tradeId), String.valueOf(negoId));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NegoListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<NegoListItemData> request, NegoListItemData result) {
                Log.e(TAG, "MakeOrder_Maker 성공 : " + result.getData());
                NegoData negoData = result.getData();
                setMakeMaker(negoData);
            }

            @Override
            public void onFail(NetworkRequest<NegoListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.e(TAG, "MakeOrder_Maker 실패 : " + errorMessage);
            }
        });
    }

    private void setMakeMaker(NegoData negoData) {
        if (negoData != null) {
            Log.e(TAG, "setMakeMaker : " + negoData);
            Glide.with(this).load(negoData.getMaker_info().getMaker_profile_img()).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(makeMakerProfile);
            Glide.with(this).load(negoData.getNegotiation_product_imges_info()[0]).centerCrop().into(makeTradePhoto);
            makeMakerName.setText(negoData.getMaker_info().getMaker_name());
            String score = String.valueOf(negoData.getMaker_info().getMaker_score() / 2);
            makeMakerGrade.setRating(negoData.getMaker_info().getMaker_score());
            makeMakerGradeText.setText("(" + score + ")");
            makeTradeIimitData.setText(negoData.getNegotiation_dtime());
            makeTradePrice.setText(String.valueOf(negoData.getNegotiation_price()));
            makeTradeContent.setText(negoData.getNegotiation_product_contents());
        }
    }

    @OnClick(R.id.btn_update_order)
    public void onClick() {

    }
}
