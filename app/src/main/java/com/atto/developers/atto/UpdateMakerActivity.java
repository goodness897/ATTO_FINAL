package com.atto.developers.atto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.atto.developers.atto.fragment.ProgressDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.makerdata.MakerListItemData;
import com.atto.developers.atto.request.UpdateMakerRequest;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UpdateMakerActivity extends AppCompatActivity {

    private static final int RC_GET_IMAGE = 1;
    private static final int RC_CAMERA = 2;

    private static final String TAG = UpdateMakerActivity.class.getSimpleName();

    ProgressDialogFragment progressDialogFragment;


    @BindView(R.id.img_detail_maker_profile)
    ImageView profileView;

    @BindView(R.id.edit_detail_maker_nickname)
    EditText inputNickNameView;

    @BindView(R.id.edit_detail_maker_intro)
    EditText inputMakerIntroView;

    private String img_file_path = null;
    File mSavedFile, mContentFile;

    MakerData makerData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_maker);
        ButterKnife.bind(this);
        initToolBar();
        progressDialogFragment = new ProgressDialogFragment();

        Intent intent = getIntent();
        makerData = (MakerData) intent.getSerializableExtra("makerData");
        setMakerData(makerData);

    }

    private void setMakerData(MakerData makerData) {
        if(makerData != null) {
            inputNickNameView.setText(makerData.getMaker_name());
            inputMakerIntroView.setText(makerData.getMaker_line_tag());
            Glide.with(this).load(makerData.getMaker_representation_img())
                    .bitmapTransform(new CropCircleTransformation(this)).into(profileView);
        }


    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_update_maker);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_complete_update)
    public void onCompleteUpdate() {
        progressDialogFragment.show(getSupportFragmentManager(), "progress");
        String mid = String.valueOf(makerData.getMaker_id());
        String line_tag = inputMakerIntroView.getText().toString();
        File representation_img = null;
        if(img_file_path != null) {
            representation_img = new File(img_file_path);
        }

        UpdateMakerRequest request = new UpdateMakerRequest(this, mid, line_tag, representation_img);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<MakerListItemData>() {
            @Override
            public void onSuccess(NetworkRequest<MakerListItemData> request, MakerListItemData result) {
                MakerData makerData = result.getData();
                if(makerData != null) {
                    Log.d(TAG, "성공 : " + makerData.getMaker_name());
                    Intent intent = new Intent();
                    intent.putExtra("makerData", makerData);
                    setResult(RESULT_OK, intent);
                    finish();
                    progressDialogFragment.dismiss();
                }


            }

            @Override
            public void onFail(NetworkRequest<MakerListItemData> request, int errorCode, String errorMessage, Throwable e) {
                Log.d(TAG, "실패 : " + errorMessage);
                progressDialogFragment.dismiss();

            }
        });
    }

    @OnClick(R.id.img_detail_maker_profile)
    public void onSetProfileImage() {
        Intent intent = new Intent(UpdateMakerActivity.this, AddImageActivity.class);
        startActivityForResult(intent, RC_GET_IMAGE);

    }

    @OnClick(R.id.btn_camera)
    public void onCaptureProfile() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = getSaveFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, RC_CAMERA);

    }

    private Uri getSaveFile() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        ), "my_image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        mSavedFile = new File(dir, "my_picture_" + System.currentTimeMillis() + ".jpg");
        return Uri.fromFile(mSavedFile);
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
                    Glide.with(this)
                            .load(new File(str.toString())).bitmapTransform(new CropCircleTransformation(this))
                            .into(profileView);
                    img_file_path = str.toString();
                }
                break;

            case RC_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    mContentFile = mSavedFile;
                    Glide.with(this)
                            .load(new File(mContentFile.getAbsolutePath())).bitmapTransform(new CropCircleTransformation(this))
                            .into(profileView);
                    img_file_path = mContentFile.getAbsolutePath();
                }
                break;
        }
    }
}
