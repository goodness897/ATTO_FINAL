package com.atto.developers.atto.networkdata.tradedata;


public class TradeListItemData implements java.io.Serializable {
    private static final long serialVersionUID = 3311847018150535221L;
    private int code;
    private TradeData data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TradeData getData() {
        return this.data;
    }

    public void setData(TradeData data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
