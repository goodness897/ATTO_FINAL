package com.atto.developers.atto.networkdata.notidata;


public class Noti implements java.io.Serializable {
    private static final long serialVersionUID = 8245994929608580040L;
    private NotiData data;
    private String message;

    public NotiData getData() {
        return this.data;
    }

    public void setData(NotiData data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
