package com.atto.developers.atto.networkdata.chatdata;

public class UpdateChatData implements java.io.Serializable {
    private static final long serialVersionUID = -2091932731265561253L;
    private int read_type;
    private int code;
    private int trade_id;
    private int[] chat_id;

    public int getRead_type() {
        return this.read_type;
    }

    public void setRead_type(int read_type) {
        this.read_type = read_type;
    }

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

    public int[] getChat_id() {
        return this.chat_id;
    }

    public void setChat_id(int[] chat_id) {
        this.chat_id = chat_id;
    }
}
