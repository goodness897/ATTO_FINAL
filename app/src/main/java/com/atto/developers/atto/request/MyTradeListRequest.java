package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class MyTradeListRequest  extends AbstractRequest<ListData<TradeData>>{
    Request mRequest;
    private final static String TRADES = "trades";
    private final static String PAGE_NO = "pageNo";
    private final static String ROW_COUNT = "rowCount";
    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "self";


    public MyTradeListRequest(Context context , String page_no, String row_count)  {
//14. 자신의 거래글 검색
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
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
        return new TypeToken<ListData<TradeData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
