package com.atto.developers.atto.networkdata.notidata;

public class NotiAddNegoData implements java.io.Serializable{
    private int code;
    private int trade_id;
    private NotiAddNegoDataMaker_info maker_info;
    private String message;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTrade_id() {
        return this.trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public NotiAddNegoDataMaker_info getMaker_info() {
        return this.maker_info;
    }

    public void setMaker_info(NotiAddNegoDataMaker_info maker_info) {
        this.maker_info = maker_info;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
