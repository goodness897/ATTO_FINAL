package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.tradedata.TradeListItemData;
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
public class UpdateTradeRequest  extends AbstractRequest<TradeListItemData> {

    Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");

    private final static String TRADES = "trades";


//    private final static String ZIP_CODE = "member_zipcode_1";

    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "modify";
    private final static String MAIN_CATEGORY = "trade_product_category_1";
    private final static String SUB_CATEGORY = "trade_product_category_2";
    private final static String PRICE = "trade_price";
    private final static String DDATE = "trade_dtime";
    private final static String CONTENTS = "trade_product_contents";
    private final static String IMAGES = "trade_product_imges";

    public UpdateTradeRequest(Context context,String tid, String trade_product_category_1, String trade_product_category_2, String trade_price,
                                  String trade_dtime, String trade_product_contents, File[] trade_product_imges) {

//        거래글 수정

        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(TRADES)
                .addPathSegment(tid)
                .build();
        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ACTION,ACTION_VAlUE)
                .addFormDataPart(MAIN_CATEGORY, trade_product_category_1)
                .addFormDataPart(SUB_CATEGORY, trade_product_category_2)
                .addFormDataPart(PRICE, trade_price)
                .addFormDataPart(DDATE, trade_dtime)
                .addFormDataPart(CONTENTS, trade_product_contents);


        if (trade_product_imges.length >0) {
            for (File trade_product_img : trade_product_imges) {
                if (trade_product_img != null) {
                    body.addFormDataPart(IMAGES, trade_product_img.getName(),
                            RequestBody.create(jpeg, trade_product_img));
                }
            }
        }else{
            body.addFormDataPart(IMAGES,"");
        }
        MultipartBody requestbody = body.build();


        mRequest = new Request.Builder()
                .url(url)
                .put(requestbody)
                .tag(context)
                .build();
        Log.i("url", mRequest.url().toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<TradeListItemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
