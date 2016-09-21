package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.fragment.CompleteDialogFragment;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.request.ReduplicationAliasRequest;
import com.atto.developers.atto.request.ReduplicationEmailRequest;
import com.atto.developers.atto.request.SignUpRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @BindView(R.id.edit_signup_email)
    AppCompatEditText emailView;

    @BindView(R.id.edit_signup_password)
    AppCompatEditText passwordView;

    @BindView(R.id.edit_signup_check_password)
    AppCompatEditText passwordCheckView;

    @BindView(R.id.edit_signup_name)
    AppCompatEditText nameView;

    @BindView(R.id.text_signup_set_nickname)
    AppCompatEditText nicknameView;

    @BindView(R.id.edit_signup_phonenumber)
    AppCompatEditText phoneNumberView;

    @BindView(R.id.text_signup_set_address)
    TextView addressView;

    @BindView(R.id.text_signup_set_postcode)
    TextView postCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initToolBar();

        passwordCheckView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String password = passwordView.getText().toString();
                String passwordCheck = passwordCheckView.getText().toString();

                if (!password.equals(passwordCheck)) {
                    Snackbar.make(view, "비밀번호를 다시 확인하세요", Snackbar.LENGTH_LONG).show();
                    passwordCheckView.setText("");
                }
            }
        });
    }

    boolean isCheckEmail = false;

    @OnClick(R.id.btn_check_email)
    public void checkEmail() {
        String email = emailView.getText().toString();
        ReduplicationEmailRequest request = new ReduplicationEmailRequest(this, email);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
            @Override
            public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                Toast.makeText(SignUpActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                isCheckEmail = true;

            }

            @Override
            public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                isCheckEmail = false;

            }
        });

    }

    boolean isCheckNickName = false;

    @OnClick(R.id.btn_check_nickname)
    public void checkNickName() {
        String nickname = nicknameView.getText().toString();
        ReduplicationAliasRequest request = new ReduplicationAliasRequest(this, nickname);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
            @Override
            public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                Toast.makeText(SignUpActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                isCheckNickName = true;
            }

            @Override
            public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                isCheckNickName = false;

            }
        });
    }


    @OnClick(R.id.btn_complete_signup)
    public void completeSignUp() {

        final String email = emailView.getText().toString();
        final String password = passwordView.getText().toString();
        String name = nameView.getText().toString();
        final String nickName = nicknameView.getText().toString();
        String postCode = postCodeView.getText().toString();
        String address = addressView.getText().toString();
        String phone = phoneNumberView.getText().toString();
        String registration_token = FirebaseInstanceId.getInstance().getToken();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "필수사항을 입력해주세요", Toast.LENGTH_LONG).show();
        } else {
            SignUpRequest request = new SignUpRequest(this, email, password, name, nickName, postCode, address, phone, registration_token);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    Log.i("result", result.getMessage());
                    Toast.makeText(SignUpActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    PropertyManager.getInstance().setEmail(email);
                    PropertyManager.getInstance().setPassword(password);
                    Toast.makeText(SignUpActivity.this, " 성공 :" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    CompleteDialogFragment dialog = new CompleteDialogFragment();
                    dialog.show(getSupportFragmentManager(), "signup");
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {

                    Log.e("error", request + " , " + errorCode + " , " + errorMessage);
                }
            });
        }
    }

    public void moveMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_img_address)
    public void onSearchAddress() {
        Intent i = new Intent(SignUpActivity.this, SearchAddressActivity.class);
        startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_signup);
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
