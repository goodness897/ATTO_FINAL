package com.atto.developers.atto.networkdata.listdata;


public class PagingData implements java.io.Serializable {
    private static final long serialVersionUID = -5008020232738356514L;
    private String next;
    private String prev;

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return this.prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }
}
