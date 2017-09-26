package com.music.maowo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-9-27 0027.
 */

public class TopicSummaryInfo implements Serializable {
    public String musicUrl;
    public String imgUrl;
    public String title;
    public String description;

    public TopicSummaryInfo(String musicUrl, String imgUrl, String title, String description) {
        this.musicUrl = musicUrl;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
    }
}
