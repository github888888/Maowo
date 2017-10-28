package com.music.maowo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-10-28 0028.
 */

public class SetListResponse {

    /**
     * reason : success
     * reasult : 1
     * data : {"file":[{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"},{"author_id":9,"article_id":40,"author_name":"","title":"火锅刚刚川菜小弟"}]}
     */

    private String reason;
    private int reasult;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TopicSummaryInfo> file;

        public List<TopicSummaryInfo> getFile() {
            return file;
        }

        public void setFile(List<TopicSummaryInfo> file) {
            this.file = file;
        }

    }
}
