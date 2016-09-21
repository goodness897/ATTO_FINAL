package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

import com.atto.developers.atto.networkdata.chatdata.ChatListItemData;
import com.atto.developers.atto.networkdata.userdata.User;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class AddChatRequest extends AbstractRequest<ChatListItemData> {
        Request mRequest;
        MediaType jpeg = MediaType.parse("image/jpeg");
// 포트폴리오 등록
private final static String TRATES = "trades";
private final static String CHAT = "chat";
private final static String TRADE_ID ="tid";
private final static String CHAT_IMG="chat_img ";
        private final static String NiCKNAME = "alias";
//
//    tid : 거래글 ID(number)
//    alias : 보내는 사람 별명
//    message : 전송할 채팅 메세지
//    chat_img : 전송할 채팅 이미지(1개씩)
//
//    collapseKey : Chat
public AddChatRequest(Context context, String tid, User user, String message, File chat_img) {

        //알림등록
        HttpUrl url = getBaseUrlHttpsBuilder()
        .addPathSegment(TRATES)
        .addPathSegment(tid)
        .addPathSegment(CHAT)
        .build();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .addFormDataPart(TRADE_ID,tid)
                .addFormDataPart(NiCKNAME,user.getUserName())
                .addFormDataPart("message",message);
        if(chat_img!=null){
                body.addFormDataPart(CHAT_IMG,chat_img.getName(),
                        RequestBody.create(jpeg,chat_img));

        } else{
                body.addFormDataPart(CHAT_IMG,"");
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
        return new TypeToken<ChatListItemData>() {
        }.getType();
        }

@Override
public Request getRequest() {
        return mRequest;
        }


        }

