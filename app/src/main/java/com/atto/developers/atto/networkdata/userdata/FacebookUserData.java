package com.atto.developers.atto.networkdata.userdata;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-09-12.
 */
public class FacebookUserData implements Serializable {
    private String name;
    private String id;
    private String email;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
