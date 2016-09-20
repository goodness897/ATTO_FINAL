package com.atto.developers.atto.networkdata.makerdata;


public class MakerListItemData implements java.io.Serializable {
    private static final long serialVersionUID = 1102352700284634572L;
    private MakerData data;
    private String message;

    public MakerData getData() {
        return this.data;
    }

    public void setData(MakerData data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
