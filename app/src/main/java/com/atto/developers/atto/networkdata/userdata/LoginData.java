package com.atto.developers.atto.networkdata.userdata;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class LoginData {

    private String message;
    private int code;
    private AuthData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

