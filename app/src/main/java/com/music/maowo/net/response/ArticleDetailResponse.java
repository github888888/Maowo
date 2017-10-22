package com.music.maowo.net.response;

import com.music.maowo.bean.ArticleDetail;

/**
 * Created by zhoushaopei on 2017/10/22.
 */

public class ArticleDetailResponse {

    private String reason;
    private int reasult;
    private ArticleDetail data;

    public int getReasult() {
        return reasult;
    }

    public void setReasult(int reasult) {
        this.reasult = reasult;
    }

    public ArticleDetail getData() {
        return data;
    }

    public void setData(ArticleDetail data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
