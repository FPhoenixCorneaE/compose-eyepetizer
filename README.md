# compose-eyepetizer

[![GitHub](https://img.shields.io/badge/GitHub-FPhoenixCorneaE-00BFFF.svg?style=flat&logo=GitHub)][1]
[![Android API](https://img.shields.io/badge/API%20level-24%2B-00CD00?logo=android)][2]
[![License](https://img.shields.io/badge/License-Apache%20License%202.0-1d7fbf.svg?style=flat)][3]

基于 [Kotlin][11] 语言，采用 [Jetpack][12] [Compose][13] + [Retrofit2][14] + [协程][15] + [Coil][16] 实现的 [MVI][17] 架构，
高仿「[开眼 Eyepetizer][18]」的一个 Android 客户端项目。 此项目开发规范参考了 [Kotlin 官方文档][19]
与 [AndroidStandardDevelop][20]。
通过此项目，新手可以快速入门 Kotlin 语言，掌握 MVI 架构，更好地熟悉 Jetpack 组件以及 Compose 的使用。

这款 App 的 UI 对应的是「开眼 Eyepetizer」的 v6.3.1 版本。（[查看历史版本][21]）

## 屏幕截图

<img src="screenshots/splash.webp" width="50%"/>
<img src="screenshots/homepage.webp" width="50%"/>
<img src="screenshots/login.webp" width="50%"/>
<img src="screenshots/mine.webp" width="50%"/>
<img src="screenshots/notification_push.webp" width="50%"/>
<img src="screenshots/notification_interaction.webp" width="50%"/>
<img src="screenshots/notification_inbox.webp" width="50%"/>
<img src="screenshots/search.webp" width="50%"/>
<img src="screenshots/setting.webp" width="50%"/>
<img src="screenshots/ugc_detail_photos.webp" width="50%"/>
<img src="screenshots/ugc_detail_video.webp" width="50%"/>
<img src="screenshots/community_commend.webp" width="50%"/>
<img src="screenshots/community_follow.webp" width="50%"/>
<img src="screenshots/homepage_discovery.webp" width="50%"/>
<img src="screenshots/homepage_commend.webp" width="50%"/>
<img src="screenshots/homepage_daily.webp" width="50%"/>
<img src="screenshots/video_detail.webp" width="50%"/>

## Apk 下载体验
- [点击下载 eyepetizer.apk][22]

## 主要功能
* [x] 首页发现精选榜单与专题内容，推荐精选视频日报、新鲜资讯。
* [x] 社区精彩瞬间图文与视频有机生态展示。
* [x] 观看优质高清短视频与评论。
* [x] 推送内容通知列表。
* [x] 热搜关键词。
* [ ] 用户、作者账号登录/注册。
* [ ] 搜索视频、作者、用户及标签。
* [ ] 分享精彩视频与新鲜资讯。

## 更新日志
[历史发布更新日志][23]

## 鼓励
如果你感觉本项目的源代码对你的学习有所帮助，可以点右上角 **"Star"** 支持一下，谢谢！^_^

## 致谢
- [Accompanist][24] Utils for Jetpack Compose
- [Coil][16] 图片加载
- [Coroutines][15] 协程
- [Gson][25] Gson 解析
- [GSYVideoPlayer][26] 视频播放器
- [PermissionX][27] 动态请求权限
- [Retrofit2][14] 网络请求框架

## License

**所有数据来源于开眼，仅供学习和交流使用，严禁用于任何商业用途，原作公司拥有所有权利。**

```
Copyright 2023 FPhoenixCorneaE.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[1]:https://github.com/FPhoenixCorneaE/

[2]:https://android-arsenal.com/api?level=24#l24

[3]:https://www.apache.org/licenses/LICENSE-2.0

[11]:https://kotlinlang.org

[12]:https://developer.android.com/jetpack

[13]:https://developer.android.com/jetpack/compose

[14]:https://square.github.io/retrofit/

[15]:https://github.com/Kotlin/kotlinx.coroutines

[16]:https://coil-kt.github.io/coil/

[17]:https://www.kodeco.com/817602-mvi-architecture-for-android-tutorial-getting-started#toc-anchor-002

[18]:https://www.kaiyanapp.com

[19]:https://www.kotlincn.net/docs/reference/coding-conventions.html

[20]:https://github.com/Blankj/AndroidStandardDevelop

[21]:https://www.wandoujia.com/apps/6619883/history_y2023

[22]:https://github.com/FPhoenixCorneaE/compose-eyepetizer/blob/main/eyepetizer.apk

[23]:https://github.com/FPhoenixCorneaE/compose-eyepetizer/releases

[24]:https://google.github.io/accompanist/

[25]:https://github.com/google/gson

[26]:https://github.com/CarGuo/GSYVideoPlayer

[27]:https://github.com/guolindev/PermissionX