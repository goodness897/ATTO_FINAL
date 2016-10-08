package com.atto.developers.atto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.atto.developers.atto.manager.NetworkManager;
import com.atto.developers.atto.manager.NetworkRequest;
import com.atto.developers.atto.manager.PropertyManager;
import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.userdata.FacebookUserData;
import com.atto.developers.atto.networkdata.userdata.LoginData;
import com.atto.developers.atto.request.FacebookLoginRequest;
import com.atto.developers.atto.request.LocalLoginRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text_login_signup)
    TextView signUpView;
    @BindView(R.id.text_login_guest)
    TextView loginGuestView;
    @BindView(R.id.text_search_password)
    TextView searchPasswordView;

    @BindView(R.id.edit_login_email)
    AppCompatEditText emailView;

    @BindView(R.id.edit_login_password)
    AppCompatEditText passwordView;
//    @BindView(R.id.login_button)


    LoginButton loginButton;
    CallbackManager callbackManager;
    LoginManager mLoginManager;

    @BindView(R.id.btn_login)
    TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        loginGuestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMainActivity();

            }
        });
        searchPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);

            }
        });
        callbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
        PropertyManager.getInstance().setFacebookId("");

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loginFacebook();
            }
        });


    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClicked() {
        if (isLogin()) {
            logoutFacebook();
        } else {
            loginFacebook();
        }
    }

    public void checkUser(LoginResult loginResult) {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            final String token = loginResult.getAccessToken().getToken();
            String regId = PropertyManager.getInstance().getRegistrationId();
            FacebookLoginRequest request = new FacebookLoginRequest(this, token, regId);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<LoginData>() {
                @Override
                public void onSuccess(NetworkRequest<LoginData> request, LoginData result) {

                    Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    String facebookId = accessToken.getUserId();
                    PropertyManager.getInstance().setFacebookId(facebookId);
                    Log.d("LoginActivity", "Token : " + token);

                    moveMainActivity();

                }

                @Override
                public void onFail(NetworkRequest<LoginData> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void facebooklogin() {

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "facebook login success : " + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                Log.i("token", loginResult.getAccessToken().getToken());
                checkUser(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void writePost() throws JSONException {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            if (accessToken.getPermissions().contains("publish_actions")) {
                GraphRequest request = GraphRequest.newPostRequest(
                        accessToken,
                        "/me/feed",
                        new JSONObject("{\"message\":\"This is Test Message\"}"),
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                // Insert your code here
                            }
                        });
                request.executeAsync();
                return;
            }
        }
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    writePost();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mLoginManager.logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
    }

    private void readPost() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            if (accessToken.getPermissions().contains("user_posts")) {
                GraphRequest request = GraphRequest.newGraphPathRequest(
                        accessToken,
                        "/me/feed",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                if (response.getJSONObject() != null) {
                                    Toast.makeText(LoginActivity.this, response.getRawResponse(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                request.executeAsync();
                return;
            }
        }
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                readPost();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mLoginManager.logInWithReadPermissions(this, Arrays.asList("user_posts"));
    }

    private void getInfo() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            if (accessToken.getPermissions().contains("email")) {
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (object != null) {
                                    String id = object.optString("id");
                                    String name = object.optString("name");
                                    String email = object.optString("email");
                                    Toast.makeText(LoginActivity.this, id + "," + name + "," + email, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "error : " + response.getError().getErrorMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
                return;
            }
        }
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getInfo();
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
//
//    private void setButtonLabel() {
//        if (isLogin()) {
//            facebookButton.setText("logout");
//        } else {
//            facebookButton.setText("login");
//        }
//    }

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

    private void logoutFacebook() {
        mLoginManager.logOut();
    }

    Button facebookButton;

    private void loginFacebook() {
        mLoginManager.setDefaultAudience(DefaultAudience.FRIENDS);
        mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        mLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "login manager...", Toast.LENGTH_SHORT).show();
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

    private boolean isLogin() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick(R.id.btn_local_login)
    public void onLocalLogin() {

        final String e_mail = emailView.getText().toString();
        final String password = passwordView.getText().toString();
        String member_registration_token = "1";

        if (e_mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_LONG).show();

        } else {
            LocalLoginRequest request = new LocalLoginRequest(this, e_mail, password, member_registration_token);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<ResultMessage>() {
                @Override
                public void onSuccess(NetworkRequest<ResultMessage> request, ResultMessage result) {
                    if (result.getCode() == 0) {
                        Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    PropertyManager.getInstance().setEmail(e_mail);
                    PropertyManager.getInstance().setPassword(password);
                    Toast.makeText(LoginActivity.this, "성공 : " + result.getMessage(), Toast.LENGTH_LONG).show();
                    moveMainActivity();
                }

                @Override
                public void onFail(NetworkRequest<ResultMessage> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(LoginActivity.this, "서버가 불안정하니 잠시후 로그인 해주세요.. 이런일이 종종 있습니다.", Toast.LENGTH_LONG).show();

                }
            });

        }


    }
//
//    @OnClick(R.id.btn_kakao_login)
//    public void onKaKaoLogin() {
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public void moveMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void changeFacebookSignup(FacebookUserData user) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("FacebookUser", user);
        startActivity(intent);
        finish();
    }

}
