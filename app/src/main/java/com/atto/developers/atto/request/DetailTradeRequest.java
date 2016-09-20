package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class DetailTradeRequest extends AbstractRequest<TradeListItemData> {

    //    거래글 검색
    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "tradeid";
    private final static String TRADES = "trades";
    private final static String PAGE_NO = "pageNo";
    private final static String ROW_COUNT = "rowCount";
    private final static String KEYWORD = "key_word_id";
    Request mRequest;

    public DetailTradeRequest(Context context, String tid, String key_word_id, String page_no, String row_count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .addQueryParameter(ACTION, ACTION_VAlUE)
                .addQueryParameter(KEYWORD, key_word_id)
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
        return new TypeToken<TradeListItemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
