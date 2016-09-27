package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.negodata.NegoListItemData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class DetailNegoRequest extends AbstractRequest<NegoListItemData> {
    Request mRequest;
    private final static String TRADES = "trades";
    private final static String NEGTIATION = "negotiations";

    public DetailNegoRequest(Context context, String tid, String nid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .addPathSegment(NEGTIATION)
                .addPathSegment(nid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        Log.i("url", mRequest.url().toString());
    }

    @Override
    protected Type getType() {
        return new TypeToken<NegoListItemData>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
