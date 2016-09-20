package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.negodata.NegoData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class MyNegoCardListRequest extends AbstractRequest<ListData<NegoData>> {
//    20. 자신의 협상카드 검색
    Request mRequest;
    private final static String TRADES = "trades";
    private final static String PAGE_NO = "pageNo";
    private final static String ROW_COUNT = "rowCount";
    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "self";
    private final static String NEGOTIATION = "negotiations";
    public MyNegoCardListRequest (Context context, String tid, String nid, String page_no, String row_count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .addPathSegment(NEGOTIATION)
                .addQueryParameter(ACTION, ACTION_VAlUE)
                .addQueryParameter(PAGE_NO, page_no)
                .addQueryParameter(ROW_COUNT, row_count)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        Log.i("url", url.toString());
    }

    @Override
    protected Type getType() {
        return new TypeToken<ListData<NegoData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
