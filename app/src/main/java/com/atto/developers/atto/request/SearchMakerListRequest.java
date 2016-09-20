package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.makerdata.MakerData;
import com.atto.developers.atto.networkdata.tradedata.ListData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-02.
 */

public class SearchMakerListRequest extends AbstractRequest<ListData<MakerData>> {
    //제작자 리스트 요청 - makerfragment
    Request mRequest;

    private final static String MAKERS = "makers";
    private final static String PAGE_NO = "pageNo";
    private final static String ROW_COUNT = "count";
    private final static String ACTION = "action";
    private final static String ACTION_VALUE = "keyword";
    private final static String KEY_WORD_ID = "key_word_id";


    public SearchMakerListRequest(Context context, String key_word_id, String pageNo, String count) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKERS)
                .addQueryParameter(ACTION, ACTION_VALUE)
                .addQueryParameter(KEY_WORD_ID, key_word_id)
                .addQueryParameter(PAGE_NO, pageNo)
                .addQueryParameter(ROW_COUNT, count)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();

        Log.i("url", mRequest.url().toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<ListData<MakerData>>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
