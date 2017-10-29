package com.music.maowo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-10-29 0029.
 */

public class CategoryResponse {

    /**
     * reason : success
     * reasult : 1
     * data : {"roll_list":[{"picture":"http://123.59.214.241:8000/static/images/1.jpg","picture_id":62},{"picture":"http://123.59.214.241:8000/static/images/2.jpg","picture_id":64},{"picture":"http://123.59.214.241:8000/static/images/3.jpg","picture_id":65},{"picture":"http://123.59.214.241:8000/static/images/4.jpg","picture_id":66},{"picture":"http://123.59.214.241:8000/static/images/5.jpg","picture_id":67}],"cate_list":[{"picture":"http://123.59.214.241:8000/static/images/cate111.jpg","cate_id":1,"description":null,"titile":"123"},{"picture":"http://123.59.214.241:8000/static/images/cate112.jpg","cate_id":2,"description":null,"titile":"\"xiaoqingxin\""},{"picture":"http://123.59.214.241:8000/static/images/cate113.jpg","cate_id":5,"description":null,"titile":"中华世纪情"},{"picture":"http://123.59.214.241:8000/static/images/cate114.jpg","cate_id":6,"description":null,"titile":"爱情人世间"},{"picture":"http://123.59.214.241:8000/static/images/cate115.jpg","cate_id":7,"description":null,"titile":"1"}]}
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
        private List<RollListBean> roll_list;
        private List<CateListBean> cate_list;

        public List<RollListBean> getRoll_list() {
            return roll_list;
        }

        public void setRoll_list(List<RollListBean> roll_list) {
            this.roll_list = roll_list;
        }

        public List<CateListBean> getCate_list() {
            return cate_list;
        }

        public void setCate_list(List<CateListBean> cate_list) {
            this.cate_list = cate_list;
        }

        public static class RollListBean {
            /**
             * picture : http://123.59.214.241:8000/static/images/1.jpg
             * picture_id : 62
             */

            private String picture;
            private int picture_id;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getPicture_id() {
                return picture_id;
            }

            public void setPicture_id(int picture_id) {
                this.picture_id = picture_id;
            }
        }

        public static class CateListBean {
            /**
             * picture : http://123.59.214.241:8000/static/images/cate111.jpg
             * cate_id : 1
             * description : null
             * titile : 123
             */

            private String picture;
            private int cate_id;
            private String description;
            private String titile;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getCate_id() {
                return cate_id;
            }

            public void setCate_id(int cate_id) {
                this.cate_id = cate_id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getTitile() {
                return titile;
            }

            public void setTitile(String titile) {
                this.titile = titile;
            }
        }
    }
}
