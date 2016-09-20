package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
//
//member_email: 회원 이메일  (필수항목) (아이디로 사용)
//        member_password: 회원 비밀번호  (필수항목)  (해쉬암호화)
//        member_name: 회원 이름 (필수)
//        member_zipcode : 회원 우편번호
//        member_address_1: 회원 주소
//        member_phone: 회원 핸드폰 번호 (필수)(number)
//        member_registration_token: 회원의 GCM 토큰 정보 (필수)
//
//        예시) /members
public class SignUpRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;

    final static String MEMBERS = "members";
    final static String E_MAIL = "member_email";
    final static String PASSWORD = "member_password";
    final static String NAME = "member_name";
    final static String NICKNAME ="member_alias";
    final static String ZIP_CODE = "member_zipcode";
    final static String ADRESS = "member_address";
    final static String PHONE_NUM = "member_phone";
    final static String TOKEN = "member_registration_token";

    public SignUpRequest(Context context, String email, String password, String name, String nickname, String zipcode, String adress_1, String phone, String registration_token) {
        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(MEMBERS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add(E_MAIL, email)
                .add(PASSWORD, password)
                .add(NAME, name)
                .add(NICKNAME, nickname)
                .add(ZIP_CODE, zipcode)
                .add(ADRESS, adress_1)
                .add(PHONE_NUM, phone)
                .add(TOKEN, registration_token)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();

        Log.i("url", mRequest.url().toString());
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

    @Override
    protected ResultMessage parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        ResultMessage temp = gson.fromJson(text, getType());
        Log.i("result", text);
        return temp;
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultMessage>() {
        }.getType();
    }
}
