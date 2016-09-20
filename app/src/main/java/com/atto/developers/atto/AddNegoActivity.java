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
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.request.AddNegoCardRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNegoActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nego);
        ButterKnife.bind(this);
        initToolBar();

        Intent intent = getIntent();
        tradeId = intent.getIntExtra("tradeId", -1);


    }

    @OnClick(R.id.btn_wish_delevery)
    public void onPickUpDate() {
        FragmentManager fm = getSupportFragmentManager();
        NegoDateFragment dialogFragment = new NegoDateFragment();
        dialogFragment.show(fm, "fragment_pickup_date");
    }

    @OnClick(R.id.btn_nego_register)
    public void onCompleteNegoRegister() {
        String negotiation_price = priceView.getText().toString();
        String negotiation_dtime = dateView.getText().toString();
        String negotiation_product_contents = contentView.getText().toString();
        File imageFile = null;
        if (file_path != null) {
            imageFile = new File(file_path);
        }
        File[] negotiation_product_images = {imageFile};

        Intent intent = new Intent(AddNegoActivity.this, MainActivity.class);
        AddNegoCardRequest request = new AddNegoCardRequest(this, String.valueOf(tradeId), negotiation_price, negotiation_dtime, negotiation_product_contents,
                negotiation_product_images);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NegoData>() {
            @Override
            public void onSuccess(NetworkRequest<NegoData> request, NegoData result) {
                Log.d("AddNegoActivity", "성공 : " + result.getCode());
            }

            @Override
            public void onFail(NetworkRequest<NegoData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("AddNegoActivity", "실패 : " + errorMessage);


            }
        });

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public void onDateSelectValue(String selectedDate) {
        TextView dateView = (TextView) findViewById(R.id.text_pickup_date);
        dateView.setText(selectedDate);
    }


    @OnClick(R.id.img_trade_preview)
    public void onSetPhotoImage() {
        Intent intent = new Intent(AddNegoActivity.this, AddImageActivity.class);
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_add_nego);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                    Glide.with(AddNegoActivity.this)
                            .load(new File(str.toString()))
                            .into(photoView);
                    textPreView.setVisibility(View.GONE);
                    file_path = str.toString();
                }
                break;
        }
    }

}
