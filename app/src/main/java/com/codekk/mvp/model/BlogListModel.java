package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/19
 */

public class BlogListModel {
    private List<SummaryArrayBean> summaryArray;

    public List<SummaryArrayBean> getSummaryArray() {
        return summaryArray;
    }

    public void setSummaryArray(List<SummaryArrayBean> summaryArray) {
        this.summaryArray = summaryArray;
    }

    public static class SummaryArrayBean {

        private String _id;
        private int status;
        private String path;
        private String title;
        private String summary;
        private String authorName;
        private String authorUrl;
        private String type;
        private String tags;
        private String createTime;
        private String updateTime;
        private String codeKKUrl;
        private String fullTitle;
        private List<String> tagList;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorUrl() {
            return authorUrl;
        }

        public void setAuthorUrl(String authorUrl) {
            this.authorUrl = authorUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCodeKKUrl() {
            return codeKKUrl;
        }

        public void setCodeKKUrl(String codeKKUrl) {
            this.codeKKUrl = codeKKUrl;
        }

        public String getFullTitle() {
            return fullTitle;
        }

        public void setFullTitle(String fullTitle) {
            this.fullTitle = fullTitle;
        }

        public List<String> getTagList() {
            return tagList;
        }

        public void setTagList(List<String> tagList) {
            this.tagList = tagList;
        }
    }
}
