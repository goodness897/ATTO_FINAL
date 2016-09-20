package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-01.
 */


//member_email: 이메일 (필수)  (이메일)
//        member_password : 비밀번호  (필수)
//        member_registration_token: 회원의 GCM 토큰 정보
//
//        예시) /auth/login

public class LocalLoginRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;
    private final static String AUTH = "auth";
    private final static String LOGIN ="login";
    private final static String E_MAIL="member_email";
    private final static String TOKEN ="member_registration_token";
    private final static String PASSWORD = "member_password";

    public LocalLoginRequest(Context context, String e_mail, String password, String member_registration_token) {
        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(AUTH)
                .addPathSegment(LOGIN)
                .build();

        RequestBody body = new FormBody.Builder()
                .add(E_MAIL, e_mail)
                .add(PASSWORD, password)
                .add(TOKEN,member_registration_token)
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
        return new TypeToken<ResultMessage>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
