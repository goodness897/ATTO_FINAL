package com.atto.developers.atto.networkdata.tradedata;


public class TradeListItemData implements java.io.Serializable {
    private static final long serialVersionUID = 3311847018150535221L;

    private TradeData data;
    private String message;

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
