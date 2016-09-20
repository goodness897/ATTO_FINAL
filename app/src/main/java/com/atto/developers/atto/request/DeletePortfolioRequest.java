package com.atto.developers.atto.request;

import android.content.Context;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-06.
 */
public class DeletePortfolioRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;
private final static String PORTFOLIO = "portfolioes";

    public DeletePortfolioRequest(Context context, String tid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PORTFOLIO)
                .addPathSegment(tid)
                .build();
        mRequest = new Request.Builder()
                .tag(context)
                .delete()
                .build();

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
