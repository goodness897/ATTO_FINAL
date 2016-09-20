package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */

/*
거래글 검색
        /trades/:tid?action=&key_word_id=&pageNo=&rowCount=
        HTTP GET

        tid = 거래글 ID(number)
        action=
        tradeid 는 거래글 상세정보
        productorder 는 제작주문서 작성시 정보제공 기능(거래, 협상 수락시만 가능)


        예시)
        /trades/1?action=trade_id
        /trades/1?action=productorder

 */
public class DeleteTradeRequest extends AbstractRequest<ResultMessage>{
//    거래글 삭제
    Request mRequest;
    private final static String TRADES = "trades";




    public DeleteTradeRequest(Context context, String tid) {

        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .delete()
                .build();
        Log.i("url", mRequest.url().toString());
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
