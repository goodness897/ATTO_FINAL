package com.atto.developers.atto.request;

import android.content.Context;

import com.atto.developers.atto.networkdata.portfoliodata.PortfolioListitemData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-06.
 */
public class DetailPortfolioRequest extends AbstractRequest<PortfolioListitemData> {

    Request mRequest;

    private final static String PORTFOLIO = "portfolioes";

    public DetailPortfolioRequest(Context context, String pid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PORTFOLIO)
                .addPathSegment(pid)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();

    }

    @Override
    protected Type getType() {
        return new TypeToken<PortfolioListitemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }


}
