package com.atto.developers.atto.networkdata.notidata;

import com.atto.developers.atto.networkdata.chatdata.AddChatData;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class NotiData  implements java.io.Serializable {
    private static final long serialVersionUID = 2319219203764986017L;
    private String message;
    private AddChatData data;
    private int code;

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
