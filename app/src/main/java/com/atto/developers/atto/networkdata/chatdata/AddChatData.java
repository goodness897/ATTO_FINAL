package com.atto.developers.atto.networkdata.chatdata;

public class AddChatData implements java.io.Serializable {
    private static final long serialVersionUID = 2665871505510904896L;
    private int read_type;
    private int trade_id;
    private String img_path;
    private String ctime;
    private String alias;
    private String message;
    private int sender_id;
    private int chat_id;

    public int getRead_type() {
        return this.read_type;
    }

    public void setRead_type(int read_type) {
        this.read_type = read_type;
    }

    public int getTrade_id() {
        return this.trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public String getImg_path() {
        return this.img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getChat_id() {
        return this.chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }
}
