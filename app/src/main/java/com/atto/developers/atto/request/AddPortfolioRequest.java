package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.portfoliodata.PortfolioListitemData;
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

/*
file_name : 포트 폴리오 제목
        file_key_word_ids : 포트 폴리오 키워드 ID들 (3개 까지)(number)
        portfolio_img : 포트 폴리오 이미지

        예시) /portfolioes
*/

public class AddPortfolioRequest extends AbstractRequest<PortfolioListitemData> {
    Request mRequest;
    MediaType jpeg = MediaType.parse("image/jpeg");
    // 포트폴리오 등록
    private final static String PORTFOLIO = "portfolioes";
    private final static String PORTFOLIO_IMG = "portfolio_img";
    private final static String PORTFOLIO_TITLE = "file_name";

    public AddPortfolioRequest(Context context, String file_name, File portfolio_img) {

        //포트폴리오 등록
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PORTFOLIO)
                .build();
        MultipartBody.Builder body = new MultipartBody.Builder()
                .addFormDataPart(PORTFOLIO_TITLE, file_name);
        if (portfolio_img != null) {
            body.addFormDataPart(PORTFOLIO_IMG, portfolio_img.getName(), RequestBody.create(jpeg, portfolio_img));
        } else {
            body.addFormDataPart(PORTFOLIO_IMG, "");
        }
        MultipartBody requestbody = body.build();

        mRequest = new Request.Builder()
                .url(url)
                .post(requestbody)
                .tag(context)
                .build();

        Log.i("url", body.toString());

    }

    @Override
    protected Type getType() {
        return new TypeToken<PortfolioListitemData>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }


}
