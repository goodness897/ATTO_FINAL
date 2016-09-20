package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.makerdata.MakerListItemData;

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
/*
    line_tag : 한줄 소개
    representation_img : 대표 이미지


    예시) /makers
*/
 // 안쓰기로...
public class AddMakerRequest extends AbstractRequest<MakerListItemData> {
    Request mRequest;
//제작자 등록

    MediaType jpeg = MediaType.parse("image/jpeg");
    private final static String MAKER ="makers";
    private final static String LINE_TAG="line_tag";
    private final static String REPRESETATION_IMG="representation_img";

    public AddMakerRequest(Context context ,String line_tag, File representation_img) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(MAKER)
                .build();
        MultipartBody.Builder body = new MultipartBody.Builder()
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
                .post(requestBody)
                .tag(context)
                .build();
        Log.i("url", mRequest.url().toString());
    }

    @Override
    protected Type getType() {
        return null;
    }

    @Override
    public Request getRequest() {
        return null;
    }
}
