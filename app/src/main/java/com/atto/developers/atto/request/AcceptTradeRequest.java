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
 * Created by Tacademy on 2016-09-02.
 */
public class AcceptTradeRequest extends AbstractRequest<ResultMessage> {

    Request mRequest;

    private final static String TRADES = "trades";

    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "t_accept";
    private final static String NID = "nid";

    public AcceptTradeRequest(Context context, String tid, String nid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)

                .build();
        RequestBody body = new FormBody.Builder()
                .add(ACTION, ACTION_VAlUE)
                .add(NID, nid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .put(body)
                .build();
        Log.i("url", url.toString());

    }


    @Override
    protected Type getType() {
        return  new TypeToken<ResultMessage>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
