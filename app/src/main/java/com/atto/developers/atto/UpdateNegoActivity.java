package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atto.developers.atto.fragment.NegoDateFragment;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.request.UpdateNegoCardRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNegoActivity extends AppCompatActivity {

    private static final int RC_GET_IMAGE = 2;

    int tradeId;

    @BindView(R.id.edit_nego_content)
    AppCompatEditText contentView;

    @BindView(R.id.text_pickup_date)
    AppCompatEditText dateView;

    @BindView(R.id.text_setting_price)
    AppCompatEditText priceView;

    @BindView(R.id.img_trade_preview)
    ImageView photoView;

    @BindView(R.id.text_trade_preview)
    TextView textPreView;

    private String file_path;

    ProgressDialogFragment dialogFragment;

    NegoData negoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nego);
        ButterKnife.bind(this);
        initToolBar();

        dialogFragment = new ProgressDialogFragment();
        Intent intent = getIntent();
        negoData = (NegoData) intent.getSerializableExtra("negoData");

        setBeforeUpdateData(negoData);
    }

    private void setBeforeUpdateData(NegoData negoData) {

        contentView.setText(negoData.getNegotiation_product_contents());
        dateView.setText(negoData.getNegotiation_dtime());
        priceView.setText(String.valueOf(negoData.getNegotiation_price()));
        if (negoData.getNegotiation_product_imges_info() != null) {
            Glide.with(this).load(negoData.getNegotiation_product_imges_info()[0]).centerCrop().into(photoView);
            textPreView.setVisibility(View.GONE);
        } else {
            textPreView.setVisibility(View.VISIBLE);

        }

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("협상카드 수정");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_wish_delevery)
    public void onPickUpDate() {
        FragmentManager fm = getSupportFragmentManager();
        NegoDateFragment dialogFragment = new NegoDateFragment();
        dialogFragment.show(fm, "fragment_pickup_date");
    }

    @OnClick(R.id.btn_nego_update_complete)
    public void onCompleteNegoRegister() {

        dialogFragment.show(getSupportFragmentManager(), "progress");
        String negotiation_price = priceView.getText().toString();
        String negotiation_dtime = dateView.getText().toString();
        String negotiation_product_contents = contentView.getText().toString();
        File imageFile = null;
        if (file_path != null) {
            imageFile = new File(file_path);
        }
        File[] negotiation_product_images = {imageFile};

        UpdateNegoCardRequest request = new UpdateNegoCardRequest(this, String.valueOf(tradeId), String.valueOf(negoData.getNegotiation_id())
                , negotiation_price, negotiation_dtime, negotiation_product_contents,
                negotiation_product_images);

        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NegoData>() {
            @Override
            public void onSuccess(NetworkRequest<NegoData> request, NegoData result) {
                NegoData negoData = result;
                Log.d("UpdateNegoActivity", "성공 : " + result.getCode());
                Intent intent = new Intent();
                intent.putExtra("negoData", result);
                setResult(RESULT_OK, intent);
                finish();
                dialogFragment.dismiss();
            }

            @Override
            public void onFail(NetworkRequest<NegoData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("AddNegoActivity", "실패 : " + errorMessage);
                dialogFragment.dismiss();
            }
        });

    }

    public void onDateSelectValue(String selectedDate) {
        TextView dateView = (TextView) findViewById(R.id.text_pickup_date);
        dateView.setText(selectedDate);
    }

    @OnClick(R.id.img_trade_preview)
    public void onSetPhotoImage() {
        Intent intent = new Intent(UpdateNegoActivity.this, AddImageActivity.class);
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case RC_GET_IMAGE:
                StringBuilder str = new StringBuilder();
                if (resultCode == RESULT_OK) {
                    String[] files = intent.getStringArrayExtra("files");
                    for (String s : files) {
                        Log.i("ImageFiles", "files : " + s);
                        str.append(s);
                    }
                    Glide.with(UpdateNegoActivity.this)
                            .load(new File(str.toString()))
                            .into(photoView);
                    textPreView.setVisibility(View.GONE);
                    file_path = str.toString();
                }
                break;
        }
    }
}
