package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.negodata.NegoListItemData;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.DetailNegoRequest;
import com.atto.developers.atto.request.DetailTradeRequest;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MakeOrderActivity extends AppCompatActivity {
    private static final String TAG = MakeOrderActivity.class.getSimpleName();
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

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
    @BindView(R.id.text_trade_title)
    TextView makeTradeTitle;
    @BindView(R.id.text_trade_content)
    TextView makeTradeContent;
    @BindView(R.id.text_trade_price)
    TextView makeTradePrice;

    //@BindView(R.id.edit_detail_address)
    //EditText detailaddress;
    @BindView(R.id.text_set_address)
    TextView addressView;
    @BindView(R.id.text_set_postcode)
    TextView postCodeView;
    @BindView(R.id.text_make_price)
    TextView makeMakerPrice;

    NegoData negoData;
    TradeData tradeData;

    int negoId = -1;
    int tradeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        initToolBar();
        ButterKnife.bind(this);
        Intent intent = getIntent();

        negoData = (NegoData) intent.getSerializableExtra("negoData");
        tradeData = (TradeData) intent.getSerializableExtra("tradeData");
        negoId = negoData.getNegotiation_id();
        tradeId = negoData.getTrade_id();
        Log.e(TAG, "MakerOrderDialogFragment : " + tradeId + " / " + negoId);
        initData(tradeId, negoId);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("주문제작서");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData(int tradeId, int negoId) {
        makeOrderMakerData(tradeId, negoId);
        makeOrderTradeData(tradeId, negoId);
        //checkAd
    }


    private void makeOrderMakerData(int tradeId, int negoId) {
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
            Glide.with(this).load(negoData.getMaker_info().getMaker_profile_img()).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(makeMakerProfile);
            makeMakerName.setText(negoData.getMaker_info().getMaker_name());
            String score = String.valueOf(negoData.getMaker_info().getMaker_score() / 2);
            makeMakerGrade.setRating(negoData.getMaker_info().getMaker_score());
            makeMakerGradeText.setText("(" + score + ")");
            makeMakerPrice.setText(String.format("%,d", negoData.getNegotiation_price()) + "원");

        }
    }


    private void makeOrderTradeData(int tradeId, int negoId) {
        DetailTradeRequest request = new DetailTradeRequest(this, String.valueOf(tradeId), "", "", "");
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

            makeOrderImage(tradeData);
            makeTradeIimitData.setText(tradeData.getTrade_dtime());
            makeTradeTitle.setText(tradeData.getTrade_title());
            makeTradeContent.setText(tradeData.getTrade_product_contents());
            makeTradePrice.setText(String.format("%,d", tradeData.getTrade_price()) + "원");

        }
    }

    private void makeOrderImage(TradeData tradeData) {
        if (tradeData != null) {
            Glide.with(this).load(tradeData.getTrade_product_img()).centerCrop().into(makeTradePhoto);
        }
    }

    @OnClick(R.id.btn_update_order)
    public void onClick() {

        String postCode = postCodeView.getText().toString();
        String address = addressView.getText().toString();
        Intent intent = new Intent(MakeOrderActivity.this, MakeOrderNextActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btn_make_address)
    public void onSearchAddress() {
        Intent i = new Intent(MakeOrderActivity.this, SearchAddressActivity.class);
        startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        String[] strList;
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        strList = new String(data).split(",");
                        postCodeView.setText(strList[0]);
                        addressView.setText(strList[1]);
                    }
                }
                break;
        }
    }
}
