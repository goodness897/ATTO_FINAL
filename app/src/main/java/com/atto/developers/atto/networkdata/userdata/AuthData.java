package com.atto.developers.atto.networkdata.userdata;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class AuthData {
    private int auth;
    private int id;
    private String alias;
    private String facebook;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }


}
