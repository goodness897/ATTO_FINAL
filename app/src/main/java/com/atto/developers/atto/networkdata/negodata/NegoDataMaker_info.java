package com.atto.developers.atto.networkdata.negodata;

public class NegoDataMaker_info implements java.io.Serializable {
    private static final long serialVersionUID = -2961397907002658023L;
    private String maker_profile_img;
    private String maker_name;
    private float maker_score;
    private int maker_id;

    public String getMaker_profile_img() {
        return this.maker_profile_img;
    }

    public void setMaker_profile_img(String maker_profile_img) {
        this.maker_profile_img = maker_profile_img;
    }

    public String getMaker_name() {
        return this.maker_name;
    }

    public void setMaker_name(String maker_name) {
        this.maker_name = maker_name;
    }

    public float getMaker_score() {
        return this.maker_score;
    }

    public void setMaker_score(float maker_score) {
        this.maker_score = maker_score;
    }

    public int getMaker_id() {
        return maker_id;
    }

    public void setMaker_id(int maker_id) {
        this.maker_id = maker_id;
    }
}
