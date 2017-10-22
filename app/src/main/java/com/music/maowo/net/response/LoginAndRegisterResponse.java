package com.music.maowo.net.response;

import android.widget.ScrollView;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-9-17 0017.
 */

public class LoginAndRegisterResponse implements Serializable{
    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int token;
}
