## 进度

BASE_URL = `http://api.codekk.com`

结构体：

    {
        "code": 0, // 状态码， 0 正常，其他异常
        "message": "", //提示信息
        "data": {} // 数据体
    }

## 开源项目

 - ~~获取开源项目列表 ~~ `/op/page/:page` ------------  `get` 
 
  可选参数: `type`,取值范围为：array、map、mix
  
  array、map 分别表示返回结果中 data 字段仅包含 projectArray 或 projectDateMap
  
  mix 表示都包含，默认为 array

 - ~~获取开源项目详情~~ `/op/detail/:id/readme` ------------  `get` 

 - ~~搜索开源项目~~ `/op/search` ------------  `get` 
 
 可选参数：`text`表示搜索内容，`page`表示页数

 - 添加开源项目 `/op/add-project` ------------  `post`  
 
 status:`TODO`

 - 检查开源项目是否存在 `/project/not-exist` ------------  `get` 

 status:`TODO`

 - 添加标签 `/op/tagging` ------------  `post` 

 status:`TODO`

 - 删除标签 `/op/untagging` ------------  `post` 

 status:`TODO`

 - 根据提交者查询项目列表 `/op/committer/:committer/page/:page` ------------  `get` 

## 源码解析

 - ~~获取源码解析文章列表~~ `/opa/page/:page` ------------  `get` 

 - ~~获取源码解析文章详情~~ `/opa/detail/:id` ------------  `get` 

 - ~~根据作者查询源码解析文章列表~~ `/opa/user/:userName/page/:page` ------------  `get` 

## 职位内推

 - ~~获取职位内推文章列表~~ `/job/page/:page` ------------  `get` 

 - ~~获取职位内推文章详情~~ `/job/detail/:id` ------------  `get` 

## 博客文章

 - ~~获取博客文章列表~~ `/blog/page/:page` ------------  `get` 

 - ~~获取单个博客文章详情~~ `/blog/detail/:id` ------------  `get` 

## 个人笔记

 - 获取个人笔记列表 `/notes/user/:userName/page/:page` ------------  `get` 

## 今日推荐

 - ~~获取今日推荐列表~~ `/recommend/page/:page` ------------  `get` 
 
 - ~~根据推荐者查询推荐列表~~ `/recommend/user/:userName/page/:page` ------------  `get` 

## 公共功能

 - 收藏某个内容 `/common/favorite` ------------  `post` 

 - 取消收藏某个内容 `/common/unfavorite` ------------  `post` 

 - 某个用户的收藏列表 `/favorite/user/:userName/page/:page` ------------  `get` 

 - （未开放）添加标签 `/common/tagging`

 status:`TODO`
 
 - （未开放）删除某个标签 `/common/untagging`

 status:`TODO`