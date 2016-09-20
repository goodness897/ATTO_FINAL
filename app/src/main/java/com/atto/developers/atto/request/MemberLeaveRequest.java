package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class MemberLeaveRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;

    private final static String MEMBERS = "members";
    private final static String ME ="me";

    public MemberLeaveRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MEMBERS)
                .addPathSegment(ME)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .delete()
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
