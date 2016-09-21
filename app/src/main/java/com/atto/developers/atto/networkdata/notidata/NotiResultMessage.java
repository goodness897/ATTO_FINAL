package com.atto.developers.atto.networkdata.notidata;

public class NotiResultMessage implements java.io.Serializable {
    private static final long serialVersionUID = 6904536067394368828L;
    private int code;
    private String message2;
    private String message1;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage2() {
        return this.message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getMessage1() {
        return this.message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }
}
