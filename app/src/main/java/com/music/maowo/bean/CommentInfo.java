package com.music.maowo.bean;

/**
 * Created by Administrator on 2017-10-16 0016.
 */

public class CommentInfo {
    public String authorUrl;
    public String authorName;
    public String postTime;
    public String postContent;

    public CommentInfo(String authorUrl, String authorName, String postTime, String postContent) {
        this.authorUrl = authorUrl;
        this.authorName = authorName;
        this.postTime = postTime;
        this.postContent = postContent;
    }
}
