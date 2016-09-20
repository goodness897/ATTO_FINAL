package com.atto.developers.atto.request;

import android.content.Context;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-06.
 */
public class UpdatePortfolioRequest  extends AbstractRequest<ResultMessage> {
    Request mRequest;
    MediaType jpeg = MediaType.parse("image/jpeg");
    // 포트폴리오 등록
    private final static String PORTFOLIO = "portfolioes";
    private final static String FILE_KEY_WORD_IDS = "file_key_word_ids";
    private final static String PORTFOLIO_IMG = "portfolio_img";
    private final static String PORTFOLIO_TITLE = "file_name";

    public UpdatePortfolioRequest(Context context, String tid, String file_name,String [] file_key_word_ids, File portfolio_img ) {

        //포트폴리오 등록
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment(PORTFOLIO)
                .addPathSegment(tid)
                .build();
        MultipartBody.Builder body=  new MultipartBody.Builder()
                .addFormDataPart(PORTFOLIO_TITLE,file_name);
        if(file_key_word_ids.length>0){
            for(String file_key_word_id:file_key_word_ids){
                body.addFormDataPart(FILE_KEY_WORD_IDS,file_key_word_id);
            }

        }else{
            body.addFormDataPart(FILE_KEY_WORD_IDS,"");
        }
        if(portfolio_img!=null){
            body.addFormDataPart(PORTFOLIO_IMG,portfolio_img.getName(), RequestBody.create(jpeg,portfolio_img));
        }
        else{
            body.addFormDataPart(PORTFOLIO_IMG,"");
        }
        MultipartBody requestbody = body.build();

        mRequest = new Request.Builder()
                .url(url)
                .put(requestbody)
                .tag(context)
                .build();

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
