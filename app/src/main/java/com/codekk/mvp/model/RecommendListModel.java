package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/20.
 */

public class RecommendListModel {

    private List<RecommendArrayBean> recommendArray;

    public List<RecommendArrayBean> getRecommendArray() {
        return recommendArray;
    }

    public void setRecommendArray(List<RecommendArrayBean> recommendArray) {
        this.recommendArray = recommendArray;
    }

    public static class RecommendArrayBean {

        private boolean toCodeKKWx;
        private boolean isDelete;
        private String createTime;
        private String url;
        private String title;
        private String desc;
        private String type;
        private String userName;
        private String _id;
        private String codeKKUrl;
        private boolean isFavorite;
        private String encodeUrl;
        private List<ArticleTagsBean> articleTags;
        private List<String> contentTypes;

        public boolean isToCodeKKWx() {
            return toCodeKKWx;
        }

        public void setToCodeKKWx(boolean toCodeKKWx) {
            this.toCodeKKWx = toCodeKKWx;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCodeKKUrl() {
            return codeKKUrl;
        }

        public void setCodeKKUrl(String codeKKUrl) {
            this.codeKKUrl = codeKKUrl;
        }

        public boolean isIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        public String getEncodeUrl() {
            return encodeUrl;
        }

        public void setEncodeUrl(String encodeUrl) {
            this.encodeUrl = encodeUrl;
        }

        public List<ArticleTagsBean> getArticleTags() {
            return articleTags;
        }

        public void setArticleTags(List<ArticleTagsBean> articleTags) {
            this.articleTags = articleTags;
        }

        public List<String> getContentTypes() {
            return contentTypes;
        }

        public void setContentTypes(List<String> contentTypes) {
            this.contentTypes = contentTypes;
        }

        public static class ArticleTagsBean {
            /**
             * createTime : 2017-05-19T17:02:55.753Z
             * name : android
             * userName : 7449
             * type : article
             */

            private String createTime;
            private String name;
            private String userName;
            private String type;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
