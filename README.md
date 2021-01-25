# 短视频去水印小程序 MaYi-DouYin-NoCode
一款短视频去水印，去水印小工具，短视频无水印，短视频去水印，免费去水印，提取无水印视频，支持抖音，火山，西瓜视频视频等。
## 预览
<img src='https://www.antsclimb.com/assets/github/gh_fb90e1080b3e_860.jpg' width="300">

## 技术
Springboot + 小程序
## 文档结构
<table width="100%" border="1">
  <tr>
    <td width="200">小程序前端</td>
    <td>MaYi-DouYin-NoCode-Applet</td>
  </tr>
  <tr>
    <td>Springboot后端</td>
    <td>MaYi-DouYin-NoCode-Admin</td>
  </tr>
</table>

## 感谢
小程序前端样式：https://github.com/ithere/douyindownload-miniapp

## 部署
### 修改一下参数和配置
0.前端：app.js
``` javascript
globalData: {
    userInfo: null,
    default: '后端接口的url'
}
```
1.后端：
/src/main/resources/application.properties
``` java 
server.port=8081
server.servlet.context-path=/applet

mayi.ftpAddress=localhost
mayi.ftpPort=21
mayi.ftpUsername=mayi
mayi.ftpPassword=ftp密码
mayi.ftpUrl=ftp的ip地址/assets/video/
mayi.ftpPath=/assets/video/

spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=200MB
```
/src/main/java/com/ants/douyin/util/download/DownloadUtil.java
``` java
private static final String basic = "ftp的ip地址";
```

## 效果
### 0.首页
<img src='https://www.antsclimb.com/assets/github/IMG_2791.PNG' width="300">

### 1.除水印下载
<img src='https://www.antsclimb.com/assets/github/IMG_2792.PNG' width="300">

### 2.视频去MD5
<img src='https://www.antsclimb.com/assets/github/IMG_2794.PNG' width="300">
