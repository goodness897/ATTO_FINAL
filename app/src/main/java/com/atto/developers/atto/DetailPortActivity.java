package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.atto.developers.atto.fragment.AttoFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.DetailTradeRequest;
import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPortActivity extends AppCompatActivity {

    @BindView(R.id.img_detail_port)
    ImageView portView;
    Random r = new Random();

    @BindView(R.id.text_detail_port_title)
    TextView titleView;

    @BindView(R.id.text_detail_port_category)
    TextView categoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_port);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        int tradeId = intent.getIntExtra(AttoFragment.TRADE_ID, 0);
        PortfolioData portfolioData = (PortfolioData) intent.getSerializableExtra("portfolioData");
        if(portfolioData != null) {
            setDetailPortData(portfolioData);
        }
        if(tradeId != 0) {
            getTradeDataRequest(tradeId);
        }
        Log.d("DetailPortActivity", "tradeId : " + tradeId);

    }

    private void getTradeDataRequest(int tradeId) {
        DetailTradeRequest request = new DetailTradeRequest(this, String.valueOf(tradeId), "", "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<TradeListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<TradeListItemData> request, TradeListItemData result) {

                TradeData tradeData = result.getData();
                if(tradeData != null)
                setDetailTradeData(tradeData);
                Log.d("DetailPortActivity", "성공 : " + tradeData.getTrade_product_img());

            }
            @Override
            public void onFail(NetworkRequest<TradeListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("DetailPortActivity", "실패 : " + errorCode);

            }
        });

    }


    private void setDetailPortData(PortfolioData portfolioData) {
        String image = portfolioData.getPortfolio_img();
        if(image != null) {
            Glide.with(this).load(image).into(portView);
        }
        titleView.setText(portfolioData.getPortfolio_title());
        categoryView.setText("# "+portfolioData.getPortfolio_key_word_info()[0]+ "");
    }

    private void setDetailTradeData(TradeData tradeData) {

        String[] image = tradeData.getTrade_product_imges_info();
        if(image != null) {
            Glide.with(this).load(image[0]).into(portView);
        }
        titleView.setText(tradeData.getTrade_title());
        categoryView.setText("# "+tradeData.getTrade_key_word_info()[0] + "");

    }

    @OnClick(R.id.btn_detail_port_move_trade)
    public void onMoveDetailPort() {
        Intent intent = new Intent(DetailPortActivity.this, AddTradeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_detail_port_cancel)
    public void onCancel() {
        finish();
    }
}
