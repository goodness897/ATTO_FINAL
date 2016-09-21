package com.atto.developers.atto.networkdata.chatdata;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-09-21.
 */

public class ChatListItemData implements Serializable {
    private static final long serialVersionUID = 2319219203764986017L;
    private String message;
    private AddChatData data;
    private int code;
    public int getCode() {
        return code;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AddChatData getData() {
        return data;
    }

    public void setData(AddChatData data) {
        this.data = data;
    }
}
