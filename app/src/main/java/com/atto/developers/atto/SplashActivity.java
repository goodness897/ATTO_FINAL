package com.atto.developers.atto;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.atto.developers.atto.manager.FontManager;
import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.userdata.LoginData;
import com.atto.developers.atto.networkdata.userdata.MyProfile;
import com.atto.developers.atto.request.FacebookLoginRequest;
import com.atto.developers.atto.request.LocalLoginRequest;
import com.atto.developers.atto.request.MyProfileRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class SplashActivity extends AppCompatActivity {

    HTextView hTextView;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/nanumgothic.ttf"));
// be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("atto");

    /*    textView = (TextView)findViewById(R.id.textView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_scale);
        textView.startAnimation(animation);*/
        mHandler = new Handler(Looper.getMainLooper());
        loginSharedPreference();
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                doRealStart();
            }
        };
        setUpIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationId();
            if (!regId.equals("")) {
                doRealStart();
            } else {
//                Intent intent = new Intent(this, RegistrationIntentService.class);
//                startService(intent);
            }
        }
    }

    private void doRealStart() {
// 여기 무슨리퀘스트지 ?

        MyProfileRequest request = new MyProfileRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<MyProfile>() {
            @Override
            public void onSuccess(NetworkRequest<MyProfile> request, MyProfile result) {
                moveMainActivity();
            }
            @Override
            public void onFail(NetworkRequest<MyProfile> request, int errorCode, String errorMessage, Throwable e) {
                if (errorCode == -1) {
                    if (errorMessage.equals("not login")) {
                        loginSharedPreference();
                        return;
                    }
                }
                moveLoginActivity();
            }
        });

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void loginSharedPreference() {
        if (isFacebookLogin()) {
            processFacebookLogin();
        } else if (isAutoLogin()) {
            processAutoLogin();
        } else {
            moveLoginActivity();
        }
    }

    LoginManager loginManager;
    CallbackManager callbackManager;

    private void processFacebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (!accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())) {
            resetFacebookAndMoveLoginActivity();
            return;
        }
        if (accessToken != null) {
            String token = accessToken.getToken();
            String regId = PropertyManager.getInstance().getRegistrationId();
            FacebookLoginRequest request = new FacebookLoginRequest(this, token, regId );
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<LoginData>() {
                @Override
                public void onSuccess(NetworkRequest<LoginData> request, LoginData result) {
                    if (result.getCode() == 1) {
                        moveMainActivity();
                    } else {
                        resetFacebookAndMoveLoginActivity();
                    }
                }

                @Override
                public void onFail(NetworkRequest<LoginData> request, int errorCode, String errorMessage, Throwable e) {
                    loginManager.logOut();
                    facebookLogin();
                }
            });
        } else {
            facebookLogin();
        }
    }

    private void facebookLogin() {
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (!accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())) {
                    resetFacebookAndMoveLoginActivity();
                    return;
                }
                FacebookLoginRequest request = new FacebookLoginRequest(SplashActivity.this, accessToken.getToken(),
                        PropertyManager.getInstance().getRegistrationId());

                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<LoginData>() {
                    @Override
                    public void onSuccess(NetworkRequest<LoginData> request, LoginData result) {
                        if (result.getCode() == 1) {
                            moveMainActivity();
                        } else {
                            resetFacebookAndMoveLoginActivity();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<LoginData> request, int errorCode, String errorMessage, Throwable e) {
                        resetFacebookAndMoveLoginActivity();
                    }
                });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        loginManager.logInWithReadPermissions(this, null);
    }

    private void resetFacebookAndMoveLoginActivity() {
        loginManager.logOut();
        PropertyManager.getInstance().setFacebookId("");
        moveLoginActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isFacebookLogin() {
        if (!TextUtils.isEmpty(PropertyManager.getInstance().getFacebookId())) {
            return true;
        }
        return false;
    }

    private boolean isAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        return !TextUtils.isEmpty(email);
    }
    private void processAutoLogin() {
        String email = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(email)) {
            String password = PropertyManager.getInstance().getPassword();
            String regid = PropertyManager.getInstance().getRegistrationId();
            LocalLoginRequest request = new LocalLoginRequest(this, email, password, regid);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    moveMainActivity();
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                    moveLoginActivity();
                }
            });
        }
    }

    private void moveMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void moveLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    Handler mHandler = new Handler(Looper.getMainLooper());
}