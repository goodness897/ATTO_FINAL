package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class UpdateNotiRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;

    private final static String TRADES = "trades";
    private final static String NOTICES ="notices";


    public UpdateNotiRequest(Context context, String tid, String nid) {
        //거래글 리스트
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .addPathSegment(NOTICES)
                .addPathSegment(nid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        Log.i("url", url.toString());
    }


    @Override
    protected Type getType() {
        return new TypeToken<ResultMessage>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
