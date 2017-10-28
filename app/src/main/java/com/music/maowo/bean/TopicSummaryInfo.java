package com.music.maowo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-9-27 0027.
 */

public class TopicSummaryInfo implements Serializable {
    public int article_id; // 文章id
    public String musicUrl; // 音乐url
    public String imgUrl;   // 图片url
    public String title;    // 文章标题
    public String description;// 文章描述
    public int praise_count;// 点赞数量
    public int reply_count; // 回复数量

    public String author; // 作者
    public String authorHeaderUrl; // 作者头像
    public boolean isPraiseByMe = false;

    public TopicSummaryInfo(String musicUrl, String imgUrl, String title, String description, int praise_count, int reply_count, String author, String authorHeaderUrl, boolean isPraiseByMe) {
        this.musicUrl = musicUrl;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.praise_count = praise_count;
        this.reply_count = reply_count;
        this.author = author;
        this.authorHeaderUrl = authorHeaderUrl;
        this.isPraiseByMe = isPraiseByMe;
    }

    public TopicSummaryInfo(String musicUrl, String imgUrl, String title, String description) {
        this.musicUrl = musicUrl;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
    }

    public TopicSummaryInfo(String musicUrl, String imgUrl, String title, String description, int praise_count, int reply_count, boolean isPraiseByMe) {
        this.musicUrl = musicUrl;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.praise_count = praise_count;
        this.reply_count = reply_count;
        this.isPraiseByMe = isPraiseByMe;
    }
}
