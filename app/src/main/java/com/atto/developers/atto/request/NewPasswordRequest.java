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
public class NewPasswordRequest extends AbstractRequest<ResultMessage> {

    private final static String MEMBERS = "members";
    private final static String ME ="me";
    private final static String ACTION="action";
    private final static String ACTION_VALUE ="password";
    private final static String PASSWORD = "member_password";
    private final static String NEW_PASSWORD ="member_newpassword";

    Request mRequest;

    public NewPasswordRequest(Context context, String password, String new_password) {
        HttpUrl url  = getBaseUrlHttpsBuilder()
                .addPathSegment(MEMBERS)
                .addPathSegment(ME)
                .build();
        RequestBody body  = new FormBody.Builder()
                .add(ACTION,ACTION_VALUE)
                .add(PASSWORD,password)
                .add(NEW_PASSWORD,new_password)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .put(body)
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
