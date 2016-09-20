package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by goodn on 2016-09-18.
 */
public class ReduplicationAliasRequest extends AbstractRequest<ResultMessage> {
    Request mRequest;

    final static String MEMBERS = "members";
    final static String ALIAS ="member_alias";
    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "reduplication_alias";


    public ReduplicationAliasRequest(Context context, String member_alias) {
        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(MEMBERS)
                .build();

        RequestBody body = new FormBody.Builder()
                .add(ACTION, ACTION_VAlUE)
                .add(ALIAS, member_alias)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();

        Log.i("url", mRequest.url().toString());
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

    @Override
    protected ResultMessage parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        ResultMessage temp = gson.fromJson(text, getType());
        Log.i("result", text);
        return temp;
    }

    @Override
    protected Type getType() {
        return new TypeToken<ResultMessage>() {
        }.getType();
    }
}
