package com.atto.developers.atto.networkdata.notidata;

public class NotiAddTradeData implements java.io.Serializable{
    private int code;
    private int trade_id;
    private String trade_title;
    private int trade_product_category_2;
    private String message;
    private int trade_product_category_1;

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

    public String getTrade_title() {
        return this.trade_title;
    }

    public void setTrade_title(String trade_title) {
        this.trade_title = trade_title;
    }

    public int getTrade_product_category_2() {
        return this.trade_product_category_2;
    }

    public void setTrade_product_category_2(int trade_product_category_2) {
        this.trade_product_category_2 = trade_product_category_2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTrade_product_category_1() {
        return this.trade_product_category_1;
    }

    public void setTrade_product_category_1(int trade_product_category_1) {
        this.trade_product_category_1 = trade_product_category_1;
    }
}
