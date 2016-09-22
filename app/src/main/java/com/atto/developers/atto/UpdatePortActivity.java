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
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.request.UpdatePortfolioRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePortActivity extends AppCompatActivity {

    private static final int RC_GET_IMAGE = 10;

    @BindView(R.id.img_add_port_photo)
    ImageView photoView;

    @BindView(R.id.edit_trade_title)
    AppCompatEditText titleView;

    @BindView(R.id.edit_add_port_keyword)
    AppCompatEditText keywordView;

    @BindView(R.id.text_trade_preview)
    TextView textPreView;

    String file_path;

    PortfolioData portfolioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_port);
        ButterKnife.bind(this);
        initToolBar();

        Intent intent = getIntent();
        portfolioData = (PortfolioData) intent.getSerializableExtra("portfolioData");

        Log.d("UpdatePortActivity", "portId : " + portfolioData.getPortfolio_id());

    }

    @OnClick(R.id.img_add_port_photo)
    public void onAddImage() {
        Intent intent = new Intent(UpdatePortActivity.this, AddImageActivity.class);
        startActivityForResult(intent, RC_GET_IMAGE);

    }

    @OnClick(R.id.btn_add_port_delete_photo)
    public void onDeletePhotoButton() {
        photoView.setImageDrawable(null);
        textPreView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_update_port)
    public void onRegister() {

        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "progress");
        String file_name = titleView.getText().toString();
        String[] file_key_word_ids = {keywordView.getText().toString()};
        File portfolio_img = null;
        if (file_path != null) {
            portfolio_img = new File(file_path);
        }

        UpdatePortfolioRequest request = new UpdatePortfolioRequest(this, String.valueOf(portfolioData.getPortfolio_id()), file_name, file_key_word_ids, portfolio_img);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
            @Override
            public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                Log.d("UpdatePortActivity", "성공 : " + result.getMessage());
                Intent intent = new Intent();
                intent.putExtra("portfolio", result);
                setResult(Activity.RESULT_OK, intent);
                finish();
                progressDialogFragment.dismiss();

            }

            @Override
            public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                Log.d("UpdatePortActivity", "성공 : " + errorMessage);
                progressDialogFragment.dismiss();
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("포트폴리오 수정");
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
                    Glide.with(UpdatePortActivity.this)
                            .load(new File(str.toString()))
                            .into(photoView);
                    textPreView.setVisibility(View.GONE);
                    file_path = str.toString();
                }
                break;
        }
    }
}
