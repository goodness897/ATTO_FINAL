package com.atto.developers.atto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.request.NewPasswordRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edit_present_password)
    AppCompatEditText presentPasswordView;

    @BindView(R.id.edit_new_password)
    AppCompatEditText newPasswordView;

    @BindView(R.id.edit_check_new_password)
    AppCompatEditText newPasswordCheckView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick(R.id.btn_change_password)
    public void onChangePassword() {

        String password = presentPasswordView.getText().toString();
        String new_password = newPasswordView.getText().toString();
        String new_password_check = newPasswordCheckView.getText().toString();

        if (!new_password.equals(new_password_check)) {
            Toast.makeText(ChangePasswordActivity.this, "비밀 번호를 다시 확인해주세요", Toast.LENGTH_LONG).show();
            newPasswordCheckView.setText("");
        } else {
            NewPasswordRequest request = new NewPasswordRequest(this, password, new_password);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    Log.d(this.toString(), "성공 : " + result.getMessage());
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                    Log.d(this.toString(), "실패 : " + errorCode);

                }
            });
        }

    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_change_password);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
