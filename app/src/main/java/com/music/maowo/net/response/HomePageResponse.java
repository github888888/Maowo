package com.music.maowo.net.response;

import java.util.List;

/**
 * Created by Administrator on 2017-10-22 0022.
 */

public class HomePageResponse {

    /**
     * reason : success
     * reasult : 1
     * data : {"set_list":[{"picture":"http://123.59.214.241/static/images/11.jpg","id":51},{"picture":"http://123.59.214.241/static/images/12.jpg","id":53},{"picture":"http://123.59.214.241/static/images/13.jpg","id":54},{"picture":"http://123.59.214.241/static/images/14.jpg","id":55},null],"roll_list":[{"picture":"http://123.59.214.241/static/images/1.jpg","id":62},{"picture":"http://123.59.214.241/static/images/2.jpg","id":64},{"picture":"http://123.59.214.241/static/images/3.jpg","id":65},{"picture":"http://123.59.214.241/static/images/4.jpg","id":66},{"picture":"http://123.59.214.241/static/images/5.jpg","id":67}]}
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
        private List<SetListBean> set_list;
        private List<RollListBean> roll_list;

        public List<SetListBean> getSet_list() {
            return set_list;
        }

        public void setSet_list(List<SetListBean> set_list) {
            this.set_list = set_list;
        }

        public List<RollListBean> getRoll_list() {
            return roll_list;
        }

        public void setRoll_list(List<RollListBean> roll_list) {
            this.roll_list = roll_list;
        }

        public static class SetListBean {
            /**
             * picture : http://123.59.214.241/static/images/11.jpg
             * id : 51
             */

            private String picture;
            private int id;
            private String titile;
            private String description;

            public String getTitle() {
                return titile;
            }

            public void setTitle(String title) {
                this.titile = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class RollListBean {
            /**
             * picture : http://123.59.214.241/static/images/1.jpg
             * id : 62
             */

            private String picture;
            private int id;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
