package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.fragment.MakerOrderDialogFragment;
import com.atto.developers.atto.fragment.ReportDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.negodata.NegoListItemData;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.DeleteNegoCardRequest;
import com.atto.developers.atto.request.DetailNegoRequest;
import com.atto.developers.atto.request.DetailTradeRequest;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailNegoActivity extends AppCompatActivity {

    private static final int REQUEST_NEGO_UPDATE = 1000;
    @BindView(R.id.img_maker_profile)
    ImageView maker_profile;

    @BindView(R.id.text_maker_profile_nickname)
    TextView maker_nickname;

    @BindView(R.id.offer_price)
    TextView offer_pice;

    @BindView(R.id.text_trade_dday)
    TextView trade_dday;

    @BindView(R.id.limit_date)
    TextView limit_date;

    @BindView(R.id.ratingbar_maker_grade)
    RatingBar ratingBar;

    @BindView(R.id.img_add_port_photo)
    ImageView img_add_port_photo;

    @BindView(R.id.text_trade_maker_contents)
    TextView trade_maker_contents;

    @BindView(R.id.accept_layout)
    LinearLayout linearLayout;


    NegoData negoData;
    TradeData tradeData;

    int negoId = -1;
    int tradeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nego);
        ButterKnife.bind(this);
        initToolBar();
        Intent intent = getIntent();

        negoData = (NegoData) intent.getSerializableExtra("negoData");
        tradeData = (TradeData) intent.getSerializableExtra("tradeData");
        negoId = negoData.getNegotiation_id();
        tradeId = negoData.getTrade_id();
        Log.d("DetailNegoActivity", "negoId : " + negoData.getNegotiation_id());
        initData(tradeId, negoId);
        checkUser();
    }

    private void checkUser() {

        DetailTradeRequest request = new DetailTradeRequest(this, String.valueOf(tradeId), "", "", "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<TradeListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<TradeListItemData> request, TradeListItemData result) {
                TradeData tradeData = result.getData();
                if (tradeData.getMember_info().getMember_alias().equals(PropertyManager.getInstance().getNickName())) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFail(NetworkRequest<TradeListItemData> request, int errorCode, String errorMessage, Throwable e) {

            }
        });






    }

    public NegoData getNegoData() {
        return negoData;
    }

    public TradeData getTradeData() {
        return tradeData;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_detail_nego);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.img_btn_Report)
    public void onReport() {
        ReportDialogFragment dialogFragment = new ReportDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "report");
    }

    @OnClick(R.id.btn_check_complete)
    public void onCustomDialog() {
        MakerOrderDialogFragment dialogFragment = new MakerOrderDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "custom");
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel() {
        Intent intent = new Intent(DetailNegoActivity.this, DetailTradeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void initData(int tradeId, int negoId) {

        DetailNegoRequest request = new DetailNegoRequest(this, String.valueOf(tradeId), String.valueOf(negoId));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NegoListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<NegoListItemData> request, NegoListItemData result) {
                NegoData negoData = result.getData();
                setNegoData(negoData);
                Log.d(this.toString(), "협상카드상세성공 : " + result.getMessage());
            }

            @Override
            public void onFail(NetworkRequest<NegoListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getApplicationContext(), "실패 : " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (negoData != null) {
            if (negoData.getMaker_info().getMaker_name().equals(PropertyManager.getInstance().getNickName())) {
                getMenuInflater().inflate(R.menu.activity_ud_menu, menu);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intent = new Intent(DetailNegoActivity.this, UpdateNegoActivity.class);
            if (negoData != null) {
                intent.putExtra("negoData", negoData);
                startActivityForResult(intent, REQUEST_NEGO_UPDATE);
            }

        } else if (id == R.id.action_delete) {
            DeleteNegoCardRequest request = new DeleteNegoCardRequest(this, "1", String.valueOf(negoData.getNegotiation_id()));
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    Toast.makeText(DetailNegoActivity.this, "성공 : " + result.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(DetailNegoActivity.this, "실패 : " + errorMessage, Toast.LENGTH_LONG).show();

                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNegoData(NegoData negoData) {

        checkImageData(negoData);
        maker_nickname.setText(negoData.getMaker_info().getMaker_name());
        ratingBar.setRating(negoData.getMaker_info().getMaker_score());
        offer_pice.setText(String.format("%,d", negoData.getNegotiation_price()) + "원");
        limit_date.setText(negoData.getNegotiation_dtime()); //yyyy-mm-dd까지
        trade_maker_contents.setText(negoData.getNegotiation_product_contents());
        try {
            checkDdayData(negoData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void checkImageData(NegoData data) {
        if (data.getMaker_info().getMaker_profile_img() != null) {
            Glide.with(this).load(data.getMaker_info().getMaker_profile_img()).bitmapTransform(new CropCircleTransformation(this)).into(maker_profile);
        } else {
            img_add_port_photo.setImageResource(R.drawable.default_image);
        }
        if (data.getNegotiation_product_imges_info() != null) {
            Glide.with(this).load(data.getNegotiation_product_imges_info()[0]).centerCrop().into(img_add_port_photo);
        } else {
            maker_profile.setImageResource(R.drawable.default_image);
        }
    }

    private void checkDdayData(NegoData negoData) throws ParseException {
        Calendar toTime = Calendar.getInstance();
        long currentTiem = toTime.getTimeInMillis();
        SimpleDateFormat d = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        String negoTime = negoData.getNegotiation_dtime();
        Date trTime = d.parse(negoTime);
        long futureTime = trTime.getTime();
        long diff = futureTime - currentTiem;
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        if (day < 0) {
            day = day * -1;
            trade_dday.setText("D+" + day);
        } else {
            trade_dday.setText("D-" + day);
        }
    }
/*

    @OnClick(R.id.btn_chat)
    public void onChatButton() {
        User user = new User();
        user.setId(negoData.getMaker_info().getMaker_id());
        user.setEmail(negoData.getMaker_info().getMaker_name());
        user.setUserName(negoData.getMaker_info().getMaker_name());


        Intent intent = new Intent(DetailNegoActivity.this, ChatActivity.class);
        intent.putExtra(ChatActivity.EXTRA_USER, user);
        intent.putExtra("tid", tradeId);
        startActivity(intent);

    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_NEGO_UPDATE:
                if (resultCode == RESULT_OK) {
                    negoData = (NegoData) intent.getSerializableExtra("negoData");
                    setNegoData(negoData);

                }
                break;
        }
    }

}