package com.atto.developers.atto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.userdata.LoginData;
import com.atto.developers.atto.request.RegisterFacebookMakerRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterMakerActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_regsiter_maker);
        ButterKnife.bind(this);

        initToolBar();

        callbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
    }

    AccessTokenTracker mTracker;

    @Override
    protected void onStart() {
        super.onStart();
        if (mTracker == null) {
            mTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                    setButtonLabel();
                }
            };
        } else {
            mTracker.startTracking();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_complete_update)
    public void completeUpdate() {
        if (!isLogin()) {
            loginFacebook();
        } else {
            loginFacebook();

        }
    }

    private boolean isLogin() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token != null;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.activity_regsiter_maker);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loginFacebook() {
        mLoginManager.setDefaultAudience(DefaultAudience.FRIENDS);
        mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(RegisterMakerActivity.this, "login manager...", Toast.LENGTH_SHORT).show();
                checkUser(loginResult);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mLoginManager.logInWithReadPermissions(this, Arrays.asList("email"));
    }

    public void checkUser(LoginResult loginResult) {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null) {
            final String token = loginResult.getAccessToken().getToken();
            String regId = PropertyManager.getInstance().getRegistrationId();
            RegisterFacebookMakerRequest request = new RegisterFacebookMakerRequest(this, token, regId);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<LoginData>() {
                @Override
                public void onSuccess(NetworkRequest<LoginData> request, LoginData result) {

                    Toast.makeText(RegisterMakerActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    String facebookId = accessToken.getUserId();
                    PropertyManager.getInstance().setFacebookId(facebookId);
                    Toast.makeText(RegisterMakerActivity.this, "성공", Toast.LENGTH_SHORT).show();
                    Log.d("LoginActivity", "Token : " + token);
                }

                @Override
                public void onFail(NetworkRequest<LoginData> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(RegisterMakerActivity.this, "login fail", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}
