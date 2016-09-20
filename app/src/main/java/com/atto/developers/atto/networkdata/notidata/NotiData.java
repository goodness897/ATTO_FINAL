package com.atto.developers.atto.networkdata.notidata;


public class NotiData implements java.io.Serializable {
    private static final long serialVersionUID = 6722883179600308245L;
    private String trade_id;
    private String notice_contents;
    private String notice_id;

    public String getTrade_id() {
        return this.trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public String getNotice_contents() {
        return this.notice_contents;
    }

    public void setNotice_contents(String notice_contents) {
        this.notice_contents = notice_contents;
    }

    public String getNotice_id() {
        return this.notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }
}
