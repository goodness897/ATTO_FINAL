package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.tradedata.TradeData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class MakerOrderInfoRequest  extends AbstractRequest<TradeData>{

    //              게시글 검색인데  제작자가 제작주문서 작성시 정보제공 기능(거래, 협상 수락시만 가능)

    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "productorder";
    private final static String TRADES = "trades";
    private final static String PAGE_NO = "pageNo";
    private final static String ROW_COUNT = "rowCount";
    private final static String KEYWORD = "key_word_id";
    Request mRequest;
    public MakerOrderInfoRequest(Context context, String tid, String key_word_id, String page_no, String row_count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .addQueryParameter(ACTION,ACTION_VAlUE)
                .addQueryParameter(KEYWORD,key_word_id)
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
        return new TypeToken<TradeData>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
