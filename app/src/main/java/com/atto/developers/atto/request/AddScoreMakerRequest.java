package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by jeon on 2016-09-04.
 */
public class AddScoreMakerRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;
//제작자 평점 등록
    MediaType jpeg = MediaType.parse("image/jpeg");
    private final static String MAKER = "makers";
    private final static String ACTION = "action";
    private final static String ACTION_VALUE = "score";
    private final static String SCORE = "score";

    public AddScoreMakerRequest(Context context, String mid, String score) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKER)
                .addPathSegment(mid)
                .build();

        RequestBody body = new FormBody.Builder()
                .add(ACTION, ACTION_VALUE)
                .add(SCORE, score)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .put(body)
                .tag(context)
                .build();
        Log.i("url", mRequest.url().toString());


    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultMessage>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}