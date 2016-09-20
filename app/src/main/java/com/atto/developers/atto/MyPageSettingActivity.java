package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.atto.developers.atto.fragment.CheckLogoutDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPageSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_setting);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick(R.id.key_maker_info)
    public void onSettingProfile() {
        Intent intent = new Intent(MyPageSettingActivity.this, MakerInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.key_change_password)
    public void onChangePassword() {
        Intent intent = new Intent(MyPageSettingActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.key_about_atto)
    public void onChangeAddress() {
        Intent intent = new Intent(MyPageSettingActivity.this, AboutAttoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.key_alert_setting)
    public void onAlertSetting() {

    }

    @OnClick(R.id.key_register_maker)
    public void onRegisterMaker() {
        Intent intent = new Intent(MyPageSettingActivity.this, RegisterMakerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.key_leave_atto)
    public void onLeaveAtto() {
        Intent intent = new Intent(MyPageSettingActivity.this, AccountLeaveActivity.class);
        startActivity(intent);



    }

    @OnClick(R.id.key_logout)
    public void onLogOut() {

        CheckLogoutDialogFragment dialogFragment = new CheckLogoutDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "logout");

    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_mypage_setting);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void startIntent() {

        Intent intent = new Intent(MyPageSettingActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}
