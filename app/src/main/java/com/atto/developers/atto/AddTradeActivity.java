package com.atto.developers.atto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.fragment.PickupDateFragment;
import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.atto.developers.atto.request.AddTradeRequest;
import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTradeActivity extends AppCompatActivity {

    private static final int RC_GET_IMAGE = 2;
    private static final int IMAGE_HEIGHT_SIZE = 140;
    @BindView(R.id.img_trade_preview)
    ImageView imagePreView;

    @BindView(R.id.text_trade_preview)
    TextView textPreView;

    @BindView(R.id.edit_trade_title)
    AppCompatEditText inputTitleView;

    @BindView(R.id.edit_trade_content)
    AppCompatEditText inputContentView;

    @BindView(R.id.text_pickup_date)
    AppCompatEditText pickUpDateView;

    @BindView(R.id.text_setting_price)
    AppCompatEditText priceView;

    private int mainCategory = -1;
    private int subCategory = -1;

    private String file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);
        ButterKnife.bind(this);
        initToolBar();

        MaterialSpinner main_spinner = (MaterialSpinner) findViewById(R.id.spinner_main_category);
        main_spinner.setBackgroundColor(getResources().getColor(R.color.color_edit_layout));
        main_spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        main_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Position " + position, Snackbar.LENGTH_LONG).show();
                mainCategory = position;
            }
        });
        MaterialSpinner sub_spinner = (MaterialSpinner) findViewById(R.id.spinner_sub_category);
        sub_spinner.setBackgroundColor(getResources().getColor(R.color.color_edit_layout));
        sub_spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        sub_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Position " + position, Snackbar.LENGTH_LONG).show();
                subCategory = position;
            }
        });
        checkPermission();


    }

    @OnClick(R.id.text_add_trade_register_trade)
    public void onTradeRegister() {
        addData();

    }


    @OnClick(R.id.btn_wish_delevery)
    public void onPickUpDate() {
        FragmentManager fm = getSupportFragmentManager();
        PickupDateFragment dialogFragment = new PickupDateFragment();
        dialogFragment.show(fm, "fragment_pickup_date");
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_addtrade);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onDateSelectValue(String selectedDate) {
        TextView dateView = (TextView) findViewById(R.id.text_pickup_date);
        dateView.setText(selectedDate);
    }

    ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();

    public void addData() {


        String trade_title = inputTitleView.getText().toString();
        int trade_product_category_1 = mainCategory;
        int trade_product_category_2 = subCategory;
        String trade_price = priceView.getText().toString();
        String trade_dtime = pickUpDateView.getText().toString();
        String trade_product_contents = inputContentView.getText().toString();

        File imageFile = null;
        if (file_path != null) {
            imageFile = new File(file_path);
        }
        File[] trade_product_images_info = {imageFile};


        Log.d("AddTradeActivity", "data : " + trade_title + " " + trade_product_category_1 + " " + trade_product_category_2
                + " " + trade_price + " " + trade_dtime + " " + trade_product_contents);
        if (TextUtils.isEmpty(trade_title) || trade_product_category_1 == -1 || trade_product_category_2 == -1 || TextUtils.isEmpty(trade_price)
                || TextUtils.isEmpty(trade_dtime) || TextUtils.isEmpty(trade_product_contents)) {
            Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_LONG).show();
        } else {

            progressDialogFragment.show(getSupportFragmentManager(), "progress");
            getAddTradeRequest(trade_title, String.valueOf(trade_product_category_1), String.valueOf(trade_product_category_2),
                    trade_price, trade_dtime, trade_product_contents, trade_product_images_info);
        }

    }
    private void getAddTradeRequest(String trade_title, String trade_product_category_1, String trade_product_category_2, String trade_price,
                                    String trade_dtime, String trade_product_contents, File[] trade_product_images_info) {

        AddTradeRequest request = new AddTradeRequest(this, trade_title, String.valueOf(trade_product_category_1), String.valueOf(trade_product_category_2),
                trade_price, trade_dtime, trade_product_contents, trade_product_images_info);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<TradeListItemData>() {

            @Override
            public void onSuccess(NetworkRequest<TradeListItemData> request, TradeListItemData result) {
                TradeData tradeData = result.getData();
                if (tradeData != null) {
                    Intent intent = new Intent(AddTradeActivity.this, DetailTradeActivity.class);
                    intent.putExtra("tradeData", tradeData);
                    Log.d("AddTradeActivity", "성공 : " + tradeData.getTrade_id() + ", 이미지 : " + tradeData.getTrade_product_imges_info());
                    startActivity(intent);
                    finish();
                    progressDialogFragment.dismiss();
                }
            }

            @Override
            public void onFail(NetworkRequest<TradeListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("AddTradeActivity", "실패 : " + errorCode);
                Toast.makeText(AddTradeActivity.this, "실패 : " + errorMessage, Toast.LENGTH_LONG).show();
                progressDialogFragment.dismiss();


            }
        });

    }

    @OnClick(R.id.img_trade_preview)
    public void onSetPhotoImage() {
        Intent intent = new Intent(AddTradeActivity.this, AddImageActivity.class);
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
                    Glide.with(AddTradeActivity.this)
                            .load(new File(str.toString()))
                            .into(imagePreView);
                    textPreView.setVisibility(View.GONE);
                    file_path = str.toString();
                }
                break;
        }
    }

    private static final int RC_PERMISSION = 100;

    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissions.size() > 0) {
            boolean isShowUI = false;
            for (String perm : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                    isShowUI = true;
                    break;
                }
            }

            final String[] perms = permissions.toArray(new String[permissions.size()]);

            if (isShowUI) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission");
                builder.setMessage("External Storage...");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(AddTradeActivity.this, perms, RC_PERMISSION);
                    }
                });
                builder.create().show();
                return;
            }

            ActivityCompat.requestPermissions(this, perms, RC_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION) {
            if (permissions != null) {
                boolean granted = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                    }
                }
                if (!granted) {
                    Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
