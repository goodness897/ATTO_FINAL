package com.atto.developers.atto.networkdata.portfoliodata;

/**
 * Created by goodn on 2016-09-08.
 */
public class PortfolioListitemData implements java.io.Serializable {

    private PortfolioData data;
    private String message;
    private int code;
    public PortfolioData getData() {
        return data;
    }

    public void setData(PortfolioData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



}
