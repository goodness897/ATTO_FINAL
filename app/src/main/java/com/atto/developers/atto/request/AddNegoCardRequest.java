package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.negodata.NegeListItemData;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-02.
 */
public class AddNegoCardRequest extends AbstractRequest<NegeListItemData> {
    //    협상카드 등록 url
    private final static String TRADE = "trades";
    private final static String NEGTIATION = "negotiations";

    // 매개변수
    private final static String PRICE = "negotiation_price";
    private final static String DTIME = "negotiation_dtime";
    private final static String CONTENT = "negotiation_product_contents";
    private final static String IMAGES = "negotiation_product_imges";


    Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");

    public AddNegoCardRequest(Context context, String tid, String negotiation_price,
                              String negotiation_dtime, String negotiation_product_contents, File[] negotiation_product_imges_info) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADE)
                .addPathSegment(tid)
                .addPathSegment(NEGTIATION)
                .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(PRICE, negotiation_price)
                .addFormDataPart(DTIME, negotiation_dtime)
                .addFormDataPart(CONTENT, negotiation_product_contents);


        if (negotiation_product_imges_info.length > 0) {
            for (File product_img : negotiation_product_imges_info) {
                if (product_img != null) {
                    body.addFormDataPart(IMAGES, product_img.getName(),
                            RequestBody.create(jpeg, product_img));
                }
            }
        } else {
            body.addFormDataPart(IMAGES, "");
        }
        MultipartBody requestbody = body.build();


        mRequest = new Request.Builder()
                .url(url)
                .post(requestbody)
                .tag(context)
                .build();

        Log.i("url", url.toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<NegeListItemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

}
