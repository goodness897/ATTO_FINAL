package com.atto.developers.atto.networkdata.testdata;


import java.io.Serializable;

/**
 * Created by Tacademy on 2016-08-24.
 */
abstract class  AbstractListData<T> implements Serializable {

    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
