package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/18.
 */

public class JobListModel {

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
        private String codeLang;
        private String source;
        private String title;
        private String summary;
        private String authorUrl;
        private String authorName;
        private String authorCity;
        private String type;
        private String createTime;
        private String expiredTime;
        private String codeKKUrl;
        private String fullTitle;

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

        public String getCodeLang() {
            return codeLang;
        }

        public void setCodeLang(String codeLang) {
            this.codeLang = codeLang;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getAuthorUrl() {
            return authorUrl;
        }

        public void setAuthorUrl(String authorUrl) {
            this.authorUrl = authorUrl;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorCity() {
            return authorCity;
        }

        public void setAuthorCity(String authorCity) {
            this.authorCity = authorCity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(String expiredTime) {
            this.expiredTime = expiredTime;
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
    }
}
