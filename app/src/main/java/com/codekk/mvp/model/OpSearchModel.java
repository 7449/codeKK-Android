package com.codekk.mvp.model;

import java.util.List;

/**
 * by y on 2017/5/17
 */

public class OpSearchModel {

    private boolean isFullSearch;
    private int totalCount;
    private int pageSize;
    private List<ProjectArrayBean> projectArray;

    public boolean isIsFullSearch() {
        return isFullSearch;
    }

    public void setIsFullSearch(boolean isFullSearch) {
        this.isFullSearch = isFullSearch;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ProjectArrayBean> getProjectArray() {
        return projectArray;
    }

    public void setProjectArray(List<ProjectArrayBean> projectArray) {
        this.projectArray = projectArray;
    }

    public static class ProjectArrayBean {
        private String lang;
        private String committer;
        private String updateTime;
        private boolean hide;
        private String authorUrl;
        private String projectName;
        private int expiredTimes;
        private String authorName;
        private int usedTimes;
        private String source;
        private String projectUrl;
        private Object officialUrl;
        private boolean recommend;
        private String desc;
        private String createTime;
        private int voteUp;
        private String _id;
        private String codeKKUrl;
        private List<TagsBean> tags;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getCommitter() {
            return committer;
        }

        public void setCommitter(String committer) {
            this.committer = committer;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }

        public String getAuthorUrl() {
            return authorUrl;
        }

        public void setAuthorUrl(String authorUrl) {
            this.authorUrl = authorUrl;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public int getExpiredTimes() {
            return expiredTimes;
        }

        public void setExpiredTimes(int expiredTimes) {
            this.expiredTimes = expiredTimes;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public int getUsedTimes() {
            return usedTimes;
        }

        public void setUsedTimes(int usedTimes) {
            this.usedTimes = usedTimes;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getProjectUrl() {
            return projectUrl;
        }

        public void setProjectUrl(String projectUrl) {
            this.projectUrl = projectUrl;
        }

        public Object getOfficialUrl() {
            return officialUrl;
        }

        public void setOfficialUrl(Object officialUrl) {
            this.officialUrl = officialUrl;
        }

        public boolean isRecommend() {
            return recommend;
        }

        public void setRecommend(boolean recommend) {
            this.recommend = recommend;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getVoteUp() {
            return voteUp;
        }

        public void setVoteUp(int voteUp) {
            this.voteUp = voteUp;
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

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * userName : SmartDengg
             * contentId : 564b562cfbbec781c2f755dc
             * type : open-source-project
             * createTime : 2015-11-23T04:59:58.342000
             * name : RxJava
             */

            private String userName;
            private String contentId;
            private String type;
            private String createTime;
            private String name;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
