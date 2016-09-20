package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.makerdata.MakerListItemData;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by jeon on 2016-09-04.
 */
public class UpdateMakerRequest extends AbstractRequest<MakerListItemData>{
   Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");
    private final static String MAKER ="makers";
    private final static String ACTION="action";
    private final static String ACTION_VALUE="modify";
    private final static String LINE_TAG="line_tag";
    private final static String REPRESETATION_IMG="representation_img";


    public UpdateMakerRequest(Context context, String mid, String line_tag, File representation_img) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKER)
                .addPathSegment(mid)
                .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .addFormDataPart(ACTION,ACTION_VALUE)
                .addFormDataPart(LINE_TAG,line_tag);
        if(representation_img!=null){
            body.addFormDataPart(REPRESETATION_IMG,representation_img.getName(),
                    RequestBody.create(jpeg,representation_img));

        } else{
            body.addFormDataPart(REPRESETATION_IMG,"");
        }
        RequestBody requestBody = body.build();



        mRequest = new Request.Builder()
                .url(url)
                .put(requestBody)
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
