package com.atto.developers.atto.networkdata.userdata;


public class Member_info implements java.io.Serializable {
    private static final long serialVersionUID = -1187373761882327994L;
    private String member_alias;
    private String member_profile_img;

    public String getMember_alias() {
        return this.member_alias;
    }

    public void setMember_alias(String member_alias) {
        this.member_alias = member_alias;
    }

    public String getMember_profile_img() {
        return this.member_profile_img;
    }

    public void setMember_profile_img(String member_profile_img) {
        this.member_profile_img = member_profile_img;
    }
}
