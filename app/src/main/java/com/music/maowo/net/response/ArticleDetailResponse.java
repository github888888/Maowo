package com.music.maowo.net.response;

import com.music.maowo.bean.ArticleDetail;

/**
 * Created by zhoushaopei on 2017/10/22.
 */

public class ArticleDetailResponse {

    private String reason;
    private int result;
    private ArticleDetail detail;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArticleDetail getDetail() {
        return detail;
    }

    public void setDetail(ArticleDetail detail) {
        this.detail = detail;
    }
}
