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
 * Created by Tacademy on 2016-09-01.
 */


/*
        trade_title : 거래글 제목 (필수)
        trade_product_category_1 : 거래글 제품 카테고리 (필수, 대분류)(number)
        trade_product_category_2 : 거래글 제품 카테고리 (필수, 소분류)(number)
        trade_price : 거래글 가격 (필수)(number)
        trade_dtime : 거래글 거래 마감일 (필수)
        trade_product_contents : 거래글 거래 상세 정보
        trade_key_words : 거래글 키워드들 (3개 까지)(number)
        trade_product_imges : 거래글 이미지들 (제한 없음)

        예시) /trades
*/

public class AddTradeRequest extends AbstractRequest<TradeListItemData> {


    private final static String TITLE = "trade_title";
    private final static String MAIN_CATEGORY = "trade_product_category_1";
    private final static String SUB_CATEGORY = "trade_product_category_2";
    private final static String PRICE = "trade_price";
    private final static String DDATE = "trade_dtime";
    private final static String CONTENTS = "trade_product_contents";
    private final static String IMAGES = "trade_product_imges";
    private final static String TRADE = "trades";
    Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");

    public AddTradeRequest(Context context, String trade_title, String trade_product_category_1, String trade_product_category_2, String trade_price,
                           String trade_dtime, String trade_product_contents, File[] trade_product_imges_info) {

//        거래글 등록

        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(TRADE)
                .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(TITLE, trade_title)
                .addFormDataPart(MAIN_CATEGORY, trade_product_category_1)
                .addFormDataPart(SUB_CATEGORY, trade_product_category_2)
                .addFormDataPart(PRICE, trade_price)
                .addFormDataPart(DDATE, trade_dtime)
                .addFormDataPart(CONTENTS, trade_product_contents);

        if (trade_product_imges_info.length > 0) {
            for (File trade_product_img : trade_product_imges_info) {
                if (trade_product_img != null) {
                    body.addFormDataPart(IMAGES, trade_product_img.getName(),
                            RequestBody.create(jpeg, trade_product_img));
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
        return new TypeToken<TradeListItemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
