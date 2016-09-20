package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.userdata.MyProfile;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class MyProfileRequest extends AbstractRequest<MyProfile> {
//    내 프로필 조회
    private final static String MEMBERS = "members";
    private final static String ME = "me";

    Request mRequest;


    public MyProfileRequest(Context context) { //내 프로필 조회

        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MEMBERS)
                .addPathSegment(ME)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();


        Log.i("url", mRequest.url().toString());

    }

    @Override
    public Request getRequest() {
        return mRequest;
    }


    @Override
    protected Type getType() {
        return new TypeToken<MyProfile>() {
        }.getType();
    }
}
