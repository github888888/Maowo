package com.music.maowo.bean;

/**
 * Created by Administrator on 2017-10-16 0016.
 */

public class CommentInfo {
    public String avatar;
    public String nickname;
    public String postTime;
    public String comment_body;

    public CommentInfo(String avatar, String nickname, String postTime, String comment_body) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.postTime = postTime;
        this.comment_body = comment_body;
    }
}
