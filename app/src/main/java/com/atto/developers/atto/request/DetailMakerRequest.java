package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.makerdata.MakerListItemData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by jeon on 2016-09-04.
 */
public class DetailMakerRequest extends AbstractRequest<MakerListItemData> {
    Request mRequest;
//제작자 상세

    MediaType jpeg = MediaType.parse("image/jpeg");
    private final static String MAKER = "makers";

    public DetailMakerRequest(Context context, String tid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKER)
                .addPathSegment(tid)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        Log.i("url", mRequest.url().toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<MakerListItemData>(){}.getType();

    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}