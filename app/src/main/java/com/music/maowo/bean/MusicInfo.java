package com.music.maowo.bean;

/**
 * Created by Administrator on 2017-8-25 0025.
 * 
 */

public class MusicInfo {
    // 音乐标题
    public String title;
    // 歌手
    public String singer;
    // 音乐背景图片
    public String imgurl;
    // 音乐下载地址
    public String downloadUrl;
    // 音乐本地存放地址
    public String localurl;
    // 音乐歌词地址
    public String lrcurl;

    public transient boolean isPlaying = false;
}
