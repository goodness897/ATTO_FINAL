package com.atto.developers.atto.networkdata.chatdata;

public class SelectChatData implements java.io.Serializable {
    private static final long serialVersionUID = 4366659083719377015L;
    private int read_type;
    private String alias;
    private String ctime;
    private String message;
    private String chat_img;

    public int getRead_type() {
        return this.read_type;
    }

    public void setRead_type(int read_type) {
        this.read_type = read_type;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChat_img() {
        return this.chat_img;
    }

    public void setChat_img(String chat_img) {
        this.chat_img = chat_img;
    }
}
