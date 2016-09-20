package com.atto.developers.atto.networkdata.notidata;

public class Params<T> implements java.io.Serializable{
    private T data;
    private String collapseKey;
    private boolean delayWhileIdle;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCollapseKey() {
        return this.collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public boolean getDelayWhileIdle() {
        return this.delayWhileIdle;
    }

    public void setDelayWhileIdle(boolean delayWhileIdle) {
        this.delayWhileIdle = delayWhileIdle;
    }
}
