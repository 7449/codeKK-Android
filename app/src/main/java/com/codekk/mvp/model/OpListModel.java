package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/16
 */

public class OpListModel {


    private List<ProjectArrayBean> projectArray;

    public List<ProjectArrayBean> getProjectArray() {
        return projectArray;
    }

    public void setProjectArray(List<ProjectArrayBean> projectArray) {
        this.projectArray = projectArray;
    }

    public static class ProjectArrayBean {

        private String projectName;
        private String createTime;
        private String updateTime;
        private int expiredTimes;
        private int usedTimes;
        private int voteUp;
        private boolean recommend;
        private boolean hide;
        private String projectUrl;
        private String demoUrl;
        private String committer;
        private String source;
        private String lang;
        private String authorName;
        private String authorUrl;
        private String codeKKUrl;
        private String _id;
        private String desc;
        private Object officialUrl;
        private List<TagsBean> tags;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
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

        public int getExpiredTimes() {
            return expiredTimes;
        }

        public void setExpiredTimes(int expiredTimes) {
            this.expiredTimes = expiredTimes;
        }

        public int getUsedTimes() {
            return usedTimes;
        }

        public void setUsedTimes(int usedTimes) {
            this.usedTimes = usedTimes;
        }

        public int getVoteUp() {
            return voteUp;
        }

        public void setVoteUp(int voteUp) {
            this.voteUp = voteUp;
        }

        public boolean isRecommend() {
            return recommend;
        }

        public void setRecommend(boolean recommend) {
            this.recommend = recommend;
        }

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }

        public String getProjectUrl() {
            return projectUrl;
        }

        public void setProjectUrl(String projectUrl) {
            this.projectUrl = projectUrl;
        }

        public String getDemoUrl() {
            return demoUrl;
        }

        public void setDemoUrl(String demoUrl) {
            this.demoUrl = demoUrl;
        }

        public String getCommitter() {
            return committer;
        }

        public void setCommitter(String committer) {
            this.committer = committer;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
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

        public String getCodeKKUrl() {
            return codeKKUrl;
        }

        public void setCodeKKUrl(String codeKKUrl) {
            this.codeKKUrl = codeKKUrl;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Object getOfficialUrl() {
            return officialUrl;
        }

        public void setOfficialUrl(Object officialUrl) {
            this.officialUrl = officialUrl;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * createTime : 2017-05-02T03:22:25.982Z
             * name : JavaScriptBridge
             * userName : luffyjet
             * type : open-source-project
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
