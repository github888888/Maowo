package com.music.maowo.bean;

import com.music.maowo.net.BaseResult;

import java.io.Serializable;

import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by zhoushaopei on 2017/10/28.
 */

public class UserInfo implements Serializable {

    private String avatar;
    private String gender;
    private String nickname;
    private String age;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
