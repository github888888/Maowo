package com.music.maowo.net.response;

import com.music.maowo.bean.UserInfo;

/**
 * Created by zhoushaopei on 2017/10/28.
 */

public class UserInfoResponse {

    private String reason;
    private int reasult;
    private UserInfo data;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReasult() {
        return reasult;
    }

    public void setReasult(int reasult) {
        this.reasult = reasult;
    }

    public UserInfo getInfo() {
        return data;
    }

    public void setInfo(UserInfo info) {
        this.data = info;
    }

}
