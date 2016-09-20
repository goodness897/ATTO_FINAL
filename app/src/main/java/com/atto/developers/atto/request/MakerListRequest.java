package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */

public class MakerListRequest extends AbstractRequest<ListData<MakerData>> {
//제작자 리스트 요청 - makerfragment
    Request mRequest;

    private final static String MAKERS = "makers";
    private final static String PAGE ="pageNo";
    private final static String COUNT = "count";

    public MakerListRequest(Context context,String pageNo, String count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKERS)
                .addQueryParameter(PAGE,COUNT)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();

        Log.i("url", mRequest.url().toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<ListData<MakerData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
