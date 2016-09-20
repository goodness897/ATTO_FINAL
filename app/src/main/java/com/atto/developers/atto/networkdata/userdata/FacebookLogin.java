package com.atto.developers.atto.networkdata.userdata;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class FacebookLogin<T> {
    private int code;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
