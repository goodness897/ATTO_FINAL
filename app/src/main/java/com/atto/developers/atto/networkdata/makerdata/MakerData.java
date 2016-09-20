package com.atto.developers.atto.networkdata.makerdata;


import com.atto.developers.atto.networkdata.listdata.KeywordList;

public class MakerData implements java.io.Serializable {
    private static final long serialVersionUID = 6984295785682812238L;
    private int maker_id;
    private String maker_representation_img;
    private String maker_line_tag;// null 가능
    private float maker_score;
    private String maker_name;
    private int[] maker_product_category_info;

    public int getMaker_id() {
        return maker_id;
    }

    public void setMaker_id(int maker_id) {
        this.maker_id = maker_id;
    }

    public String getMaker_representation_img() {
        return maker_representation_img;
    }

    public void setMaker_representation_img(String maker_representation_img) {
        this.maker_representation_img = maker_representation_img;
    }

    public String getMaker_line_tag() {
        return maker_line_tag;
    }

    public void setMaker_line_tag(String maker_line_tag) {
        this.maker_line_tag = maker_line_tag;
    }

    public float getMaker_score() {
        return maker_score;
    }

    public void setMaker_score(float maker_score) {
        this.maker_score = maker_score;
    }

    public String getMaker_name() {
        return maker_name;
    }

    public void setMaker_name(String maker_name) {
        this.maker_name = maker_name;
    }

    public int[] getMaker_product_category_info() {
        return maker_product_category_info;
    }

    public void setMaker_product_category_info(int[] maker_product_category_info) {
        this.maker_product_category_info = maker_product_category_info;
    }

    public String getMaker_achievement_count() {
        return maker_achievement_count;
    }

    public void setMaker_achievement_count(String maker_achievement_count) {
        this.maker_achievement_count = maker_achievement_count;
    }

    private String maker_achievement_count;


}
