package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.ResultMessage;
import com.atto.developers.atto.networkdata.userdata.MyProfileData;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by goodn on 2016-09-18.
 */
public class MessageSendRequest extends AbstractRequest<ResultMessage> {

    //    협상카드 등록 url
    private final static String TRADE = "trades";
    private final static String CHAT = "chat";

    // 매개변수
    private final static String ALIAS = "alias";
    private final static String MESSAGE = "message";
    private final static String COLLAPSEKEY = "collapseKey";
    private final static String COLLAPSEKEY_VALUE = "Chat";
    private final static String IMAGES = "chat_img";

    Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");

    public MessageSendRequest(Context context, String tid, MyProfileData user, String message, File chat_img) {
        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(TRADE)
                .addPathSegment(tid)
                .addPathSegment(CHAT)
                .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ALIAS, user.getMember_alias())
                .addFormDataPart(MESSAGE, message)
                .addFormDataPart(COLLAPSEKEY, COLLAPSEKEY_VALUE);

        if (chat_img != null) {
            body.addFormDataPart(IMAGES, chat_img.getName(),
                    RequestBody.create(jpeg, chat_img));
        } else {
            body.addFormDataPart(IMAGES, "");
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
        return new TypeToken<ResultMessage>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
