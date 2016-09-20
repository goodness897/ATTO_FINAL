package com.atto.developers.atto.request;

import android.content.Context;
import android.util.Log;

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
 * Created by Tacademy on 2016-09-01.
 */

//  프로필 수정
//        member_phone: 회원 핸드폰 번호(number)
//        member_zipcode_1 : 회원 우편번호(number)
//                member_address_1: 회원 주소
//        member_alias: 회원 닉네임
//        member_gender: 회원 성별(number/malel - 1 female -0)
//        member_profile_img: 회원 프로필 사진

public class UpdateMyProfileRequest extends AbstractRequest<ResultMessage> {

    Request mRequest;

    MediaType jpeg = MediaType.parse("image/jpeg");

    private final static String MEMBERS = "members";
    private final static String ME = "me";

    private final static String ZIP_CODE = "member_zipcode_1";
    private final static String ACTION = "action";
    private final static String ACTION_VAlUE = "modify";
    private final static String PHONE = "member_phone";
    private final static String ADRESS = "member_address_1";
    private final static String PROFILE_IMG = "member_profile_img";
    private final static String NICKNAME = "member_alias";

    public UpdateMyProfileRequest(Context context, String member_zipcode_1, String member_phone, String member_address_1,
                                  String member_alias, File member_profile_img) {

        HttpUrl url = getBaseUrlHttpsBuilder()
                .addPathSegment(MEMBERS)
                .addPathSegment(ME)
                .build();
        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ACTION, ACTION_VAlUE)
                .addFormDataPart(ZIP_CODE, member_zipcode_1)
                .addFormDataPart(PHONE, member_phone)
                .addFormDataPart(ADRESS, member_address_1)
                .addFormDataPart(NICKNAME, member_alias);

        if (member_profile_img != null) {
            body.addFormDataPart(PROFILE_IMG, member_profile_img.getName(),
                    RequestBody.create(jpeg, member_profile_img));
        } else {
            body.addFormDataPart(PROFILE_IMG, "");
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
        return new TypeToken<ResultMessage>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
