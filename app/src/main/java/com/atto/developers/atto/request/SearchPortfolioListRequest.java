package com.atto.developers.atto.request;

import android.content.Context;

import com.atto.developers.atto.networkdata.portfoliodata.PortfolioData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class SearchPortfolioListRequest extends AbstractRequest<ListData<PortfolioData>> {
    Request mRequest;
    private static final String PAGE_NO = "pageNo";
    private static final String ROWCOUNT = "rowCount";
    private static final String PORTFOLIO = "portfolioes";
    private static final String ACTION = "action";
    private static final String ACTION_VALUE = "keyword";
    // url
    private static final String KEY_WORD_ID = "keyword";

    public SearchPortfolioListRequest(Context context, String key_word_id, String page_no, String row_count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PORTFOLIO)
                .addQueryParameter(ACTION, ACTION_VALUE)
                .addQueryParameter(KEY_WORD_ID, key_word_id)
                .addQueryParameter(PAGE_NO, page_no)
                .addQueryParameter(ROWCOUNT, row_count)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();


    }

    @Override
    protected Type getType() {
        return new TypeToken<ListData<PortfolioData>>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
