package com.atto.developers.atto.networkdata.userdata;


//나 프로필 정보 조회 클래스

import java.io.Serializable;

public class MyProfile  implements Serializable {
    private MyProfileData data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;

    public MyProfileData getData() {
        return this.data;
    }

    public void setData(MyProfileData data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
