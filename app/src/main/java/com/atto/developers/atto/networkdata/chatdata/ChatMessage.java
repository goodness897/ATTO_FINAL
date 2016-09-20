package com.atto.developers.atto.networkdata.chatdata;

import com.atto.developers.atto.networkdata.userdata.Member_info;

public class ChatMessage {
    private String date;
    private Member_info alias;
    private long groupId;
    private String message;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Member_info getSender() {
        return this.alias;
    }

    public void setSender(Member_info alias) {
        this.alias = alias;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
