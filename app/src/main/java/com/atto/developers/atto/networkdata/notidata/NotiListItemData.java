package com.atto.developers.atto.networkdata.notidata;

public class NotiListItemData implements java.io.Serializable{
    private String read_type;
    private String trade_id;
    private String notice_contents;
    private int notice_id;

    public String getRead_type() {
        return this.read_type;
    }

    public void setRead_type(String read_type) {
        this.read_type = read_type;
    }

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

    public int getNotice_id() {
        return this.notice_id;
    }

    public void setNotice_id(int notice_id) {
        this.notice_id = notice_id;
    }
}
