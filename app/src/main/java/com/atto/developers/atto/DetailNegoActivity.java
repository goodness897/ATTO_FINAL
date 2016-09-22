package com.atto.developers.atto;

import android.content.Intent;
import android.database.Cursor;
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
import com.atto.developers.atto.networkdata.chatdata.ChatContract;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.userdata.User;
import com.atto.developers.atto.request.DeleteNegoCardRequest;
import com.bumptech.glide.Glide;

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

    @BindView(R.id.text_trade_remain_time)
    TextView trade_remain_time;

    @BindView(R.id.ratingbar_maker_grade)
    RatingBar ratingBar;

    @BindView(R.id.img_add_port_photo)
    ImageView img_add_port_photo;

    @BindView(R.id.text_trade_maker_contents)
    TextView trade_maker_contents;

    @BindView(R.id.accept_layout)
    LinearLayout linearLayout;


    NegoData negoData;


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
        negoId = intent.getIntExtra("negoId", -1);
        tradeId = intent.getIntExtra("tradeId", -1);
        Log.d("DetailNegoActivity", "negoId : " + negoData.getNegotiation_id());
        initData(tradeId, negoId);
        checkUser();
    }

    private void checkUser() {
        if (negoData.getMaker_info().getMaker_name().equals(PropertyManager.getInstance().getNickName())) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);

        }
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

        setNegoData(negoData);

        /*DetailNegoRequest request = new DetailNegoRequest(this, String.valueOf(tradeId), String.valueOf(negoId));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NegeListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<NegeListItemData> request, NegeListItemData result) {
                NegoData negoData = result.getData();
                setNegoData(negoData);
                Log.d(this.toString(), "협상카드상세성공 : " + result.getMessage());
            }

            @Override
            public void onFail(NetworkRequest<NegeListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getApplicationContext(), "실패 : " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });*/

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


//    private void calenderDday(NegoData negoData) {
//        String[] data = negoData
//
//        Calendar a = Calendar.getInstance();
//        long currentTiem = a.getTimeInMillis();
//        a.set(Calendar.YEAR, Calendar.MONTH-1, Calendar.DAY_OF_MONTH);
//
//        Calendar b = Calendar.getInstance(TimeZone.getTimeZone(negoData.getNegotiation_dtime()));
//        long futureTime = b.getTimeInMillis();
//        b.set();
//        long diff = futureTime - currentTiem;
//        int day = (int) (diff / (1000 * 60 * 60 * 24));
//       \
//    }


    @OnClick(R.id.btn_chat)
    public void onChatButton() {

        Cursor cursor = (Cursor) negoData;
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_SERVER_ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_EMAIL)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(ChatContract.ChatUser.COLUMN_NAME)));
        Intent intent = new Intent(DetailNegoActivity.this, ChatActivity.class);
        intent.putExtra(ChatActivity.EXTRA_USER, user);
        startActivity(intent);

    }

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