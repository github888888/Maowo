package com.music.maowo.net.response;

import java.util.List;

/**
 * Created by zhoushaopei on 2017/10/22.
 */

public class SubmitArticleResponse {

    private String reason;
    private int reasult;
    private ArticleInfo data;

    public static class ArticleInfo {
        private List<Articles> article;

        public List<Articles> getArticle() {
            return article;
        }

        public void setArticle(List<Articles> article) {
            this.article = article;
        }

        public static class Articles {

            private String art_state;
            private String art_titile;
            private String art_cate;

            public String getArt_state() {
                return art_state;
            }

            public void setArt_state(String art_state) {
                this.art_state = art_state;
            }

            public String getArt_titile() {
                return art_titile;
            }

            public void setArt_titile(String art_titile) {
                this.art_titile = art_titile;
            }

            public String getArt_cate() {
                return art_cate;
            }

            public void setArt_cate(String art_cate) {
                this.art_cate = art_cate;
            }
        }
    }

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

    public ArticleInfo getData() {
        return data;
    }

    public void setData(ArticleInfo data) {
        this.data = data;
    }

}
