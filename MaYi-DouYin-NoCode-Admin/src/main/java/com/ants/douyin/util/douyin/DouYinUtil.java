package com.ants.douyin.util.douyin;

import com.ants.douyin.util.download.DownloadUtil;
import com.ants.douyin.util.json.JsonToMapUtil;
import org.jsoup.Jsoup;
import java.io.*;

public class DouYinUtil {

    public static String downLoad (String url) throws IOException {
        // 获取短连接码
        String sub = "https://v.douyin.com/"+url.split("https://v.douyin.com/")[1].substring(0, 7);
        // 通过短连接获取长链接
        String redirectUrl = Jsoup.connect(sub).followRedirects(true).execute().url().toString();
        // 获取itemId
        String itemId = redirectUrl.split("https://www.iesdouyin.com/share/video/")[0].split("/\\?region=")[0];
        // 重新信息页面
        String newUrl = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids="+itemId;
        // 获取主页面，核心数据
        String body = Jsoup.connect(newUrl).ignoreContentType(true).execute().body();
        // 获取列表
        String item_list = String.valueOf(JsonToMapUtil.toMap(body).get("item_list"));
        String substring = item_list.substring(1, item_list.length() - 1);
        String video = String.valueOf(JsonToMapUtil.toMap(substring).get("video"));
        String play_addr = String.valueOf(JsonToMapUtil.toMap(video).get("play_addr"));
        String url_list = String.valueOf(JsonToMapUtil.toMap(play_addr).get("url_list"));
        String play_url = url_list.substring(2, url_list.length() - 2);
        String videoUrl = (play_url.split("https://aweme.snssdk.com/aweme")[2].split("&ratio=720p&line=0")[1]).replace("playwm","play");
        String playUrl = "https://aweme.snssdk.com/aweme"+videoUrl+"&ratio=720p&line=0";
        String downLoad = DownloadUtil.download(playUrl);
        return downLoad;
    }
}
