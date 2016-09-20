package com.atto.developers.atto.networkdata.userdata;
//나의 프로필 데이터 객체

import java.io.Serializable;
//‘member_id’ : member_id
//        'member_alias' : alias,
//        'member_profile_img' : member_profile_img_url,
//        'member_phone' : phone,(number)
//        'member_zipcode_1' : zipcode_1
//        'member_address_1' : address_1
//        'member_auth' :auth(number) 0 - 소비자 / 1 - 제작자

public class MyProfileData implements Serializable {
    private String member_alias;
    private String member_phone;
    private String member_profile_img;
    private String member_zipcode_1;
    private String member_address_1;
    private int member_auth;
    private long member_id;

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public int getMember_auth() {
        return member_auth;
    }

    public void setMember_auth(int member_auth) {
        this.member_auth = member_auth;
    }


    public String getMember_zipcode_1() {
        return member_zipcode_1;
    }

    public void setMember_zipcode_1(String member_zipcode_1) {
        this.member_zipcode_1 = member_zipcode_1;
    }

    public String getMember_address_1() {
        return member_address_1;
    }

    public void setMember_address_1(String member_address_1) {
        this.member_address_1 = member_address_1;
    }


    public String getMember_alias() {
        return this.member_alias;
    }

    public void setMember_alias(String member_alias) {
        this.member_alias = member_alias;
    }

    public String getMember_phone() {
        return this.member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }


    public String getMember_profile_img() {
        return this.member_profile_img;
    }

    public void setMember_profile_img(String member_profile_img) {
        this.member_profile_img = member_profile_img;
    }

}
