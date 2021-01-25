package com.ants.douyin.util.pipixia;

import com.alibaba.fastjson.JSONArray;
import com.ants.douyin.util.download.DownloadUtil;
import com.ants.douyin.util.json.JsonToMapUtil;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

public class PiPiXiaUtil {

    public static String download(String url) throws IOException {
        // 重定向地址
        String s = Jsoup.connect(url).execute().url().toString();
        String newUrl = "https://h5.pipix.com/bds/webapi/item/detail/?item_id="+s.split("https://h5.pipix.com/item/")[1].substring(0, 19);
        // 获取新地址
        String body = Jsoup.connect(newUrl).ignoreContentType(true).execute().body();
        // 解析地址中的信息
        String data = String.valueOf(JsonToMapUtil.toMap(body).get("data"));
        String item = String.valueOf(JsonToMapUtil.toMap(data).get("item"));
        String video = String.valueOf(JsonToMapUtil.toMap(item).get("video"));
        String video_download = String.valueOf(JsonToMapUtil.toMap(video).get("video_download"));
        String url_list = String.valueOf(JsonToMapUtil.toMap(video_download).get("url_list"));
        List list = (List) JSONArray.parse(url_list);
        // 获得最终地址
        String videoUrl = String.valueOf(JsonToMapUtil.toMap(String.valueOf(list.get(0))).get("url"));
        String download = DownloadUtil.download(videoUrl);
        return download;
    }
}
