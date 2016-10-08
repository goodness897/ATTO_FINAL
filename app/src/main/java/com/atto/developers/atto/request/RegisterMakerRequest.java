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
 * Created by goodn on 2016-09-28.
 */

public class RegisterMakerRequest extends AbstractRequest<MakerListItemData> {

    Request mRequest;
    MediaType jpeg = MediaType.parse("image/jpeg");


    private String MAKERS = "makers";
    private String MAKER_PRODUCT_CATEGORIES = "maker_product_categores";
    private String MAKER_LINE_TAG = "maker_line_tag";
    private String MAKER_REPRESENT_IMAGE = "maker_representation_img";


    public RegisterMakerRequest(Context context, String maker_product_categores, String maker_line_tag, File maker_representation_img) {

        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(MAKERS)
                .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .addFormDataPart(MAKER_PRODUCT_CATEGORIES, maker_product_categores)
                .addFormDataPart(MAKER_LINE_TAG, maker_line_tag);
        if (maker_representation_img != null) {
            body.addFormDataPart(MAKER_REPRESENT_IMAGE, maker_representation_img.getName(),
                    RequestBody.create(jpeg, maker_representation_img));

        } else {
            body.addFormDataPart(MAKER_REPRESENT_IMAGE, "");
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
        return new TypeToken<MakerListItemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
