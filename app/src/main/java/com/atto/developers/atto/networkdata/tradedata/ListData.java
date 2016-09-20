package com.atto.developers.atto.networkdata.tradedata;


import com.atto.developers.atto.networkdata.listdata.PagingData;

public class ListData<T> implements java.io.Serializable {
    private static final long serialVersionUID = 4222295191657793439L;
    private T [] data;
    private PagingData paging;
    private String message;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T [] getData() {
        return this.data;
    }

    public void setData(T [] data) {
        this.data = data;
    }

    public PagingData getPaging() {
        return this.paging;
    }

    public void setPaging(PagingData paging) {
        this.paging = paging;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
