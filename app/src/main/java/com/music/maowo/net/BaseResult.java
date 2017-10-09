package com.music.maowo.net;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public class BaseResult<T> implements Serializable {
    public int code;
    public String message;
    public String reason;
    public int reasult;
    public T data;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

