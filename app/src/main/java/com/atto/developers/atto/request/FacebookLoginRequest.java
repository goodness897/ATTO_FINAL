package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.userdata.LoginData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-01.
 */

//access_token: 페이스북에서 넘겨 받은 토큰 (필수)
//        /auth/facebook/token

public class FacebookLoginRequest extends AbstractRequest<LoginData> {
    Request mRequest;
    private final static String AUTH = "auth";
    private final static String FACEBOOK ="facebook";
    private final static String TOKEN = "token";
    private final static String ACCESS_TOKEN = "access_token";

    public FacebookLoginRequest(Context context, String access_token, String member_registration_token) {

        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(AUTH)
                .addPathSegment(FACEBOOK)
                .addPathSegment(TOKEN)
                .build();

        RequestBody body = new FormBody.Builder()
                .add(ACCESS_TOKEN, access_token)
                .add("member_registration_token","1234")
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();

        Log.i("url", mRequest.url().toString());
    }

    @Override
    protected Type getType() {
        return new TypeToken<LoginData>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
