package com.atto.developers.atto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioListitemData;
import com.atto.developers.atto.request.AddPortfolioRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPortActivity extends AppCompatActivity {

    private static final int RC_GET_IMAGE = 10;

    @BindView(R.id.img_add_port_photo)
    ImageView photoView;

    @BindView(R.id.edit_trade_title)
    AppCompatEditText titleView;

    @BindView(R.id.text_trade_preview)
    TextView textPreView;

    String file_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_port);
        ButterKnife.bind(this);
        initToolBar();
        Log.d("DetailMakerActivity", "AddPortActivity onCreate");

    }

    @OnClick(R.id.img_add_port_photo)
    public void onAddImage() {
        Intent intent = new Intent(AddPortActivity.this, AddImageActivity.class);
        startActivityForResult(intent, RC_GET_IMAGE);

    }

    @OnClick(R.id.btn_add_port_delete_photo)
    public void onDeletePhotoButton() {
        photoView.setImageDrawable(null);
        textPreView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_add_port_register)
    public void onRegister() {

        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "progress");
        String file_name = titleView.getText().toString();
        File portfolio_img = null;
        if (file_path != null) {
            portfolio_img = new File(file_path);
        }

        AddPortfolioRequest request = new AddPortfolioRequest(this, file_name, portfolio_img);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<PortfolioListitemData>() {
            @Override
            public void onSuccess(NetworkRequest<PortfolioListitemData> request, PortfolioListitemData result) {
                PortfolioData portfolioData = result.getData();
                Log.d("AddPortActivity", "성공 : " + "포트 폴리오 이미지 url : " + portfolioData.getPortfolio_img());
                Intent intent = new Intent();
                intent.putExtra("portfolio", portfolioData);
                setResult(Activity.RESULT_OK, intent);
                finish();
                progressDialogFragment.dismiss();
            }

            @Override
            public void onFail(NetworkRequest<PortfolioListitemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("AddPortActivity", "실패 : " + errorMessage);
                progressDialogFragment.dismiss();


            }
        });

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_add_port);
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
                    Glide.with(AddPortActivity.this)
                            .load(new File(str.toString()))
                            .into(photoView);
                    textPreView.setVisibility(View.GONE);
                    file_path = str.toString();
                }
                break;
        }
    }


}
