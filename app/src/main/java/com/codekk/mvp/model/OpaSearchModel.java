package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/18.
 */

public class OpaSearchModel {


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
        private String type;
        private String summary;
        private String phase;
        private String tags;
        private String createTime;
        private String updateTime;
        private String codeLang;
        private String source;
        private String title;
        private String projectUrl;
        private String projectName;
        private String demoUrl;
        private String authorUrl;
        private String authorName;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
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

        public String getProjectUrl() {
            return projectUrl;
        }

        public void setProjectUrl(String projectUrl) {
            this.projectUrl = projectUrl;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getDemoUrl() {
            return demoUrl;
        }

        public void setDemoUrl(String demoUrl) {
            this.demoUrl = demoUrl;
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
