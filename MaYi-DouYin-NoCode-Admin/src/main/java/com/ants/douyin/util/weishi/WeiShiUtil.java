package com.ants.douyin.util.weishi;

import com.alibaba.fastjson.JSONArray;
import com.ants.douyin.util.download.DownloadUtil;
import com.ants.douyin.util.json.JsonToMapUtil;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

public class WeiShiUtil {

    public static String download(String url) throws IOException {
        url = "https://isee.weishi.qq.com"+url.split("https://isee.weishi.qq.com")[1];
        url = Jsoup.connect(url).execute().url().toString();
        String id = url.split("&id=")[1].substring(0,17);
        String newUrl = "https://h5.weishi.qq.com/webapp/json/weishi/WSH5GetPlayPage?t=0.7532600494918984&g_tk=&feedid="+id+"&recommendtype=0&datalvl=&qua=&uin=&format=json&inCharset=utf-8&outCharset=utf-8";
        String body = Jsoup.connect(newUrl).ignoreContentType(true).ignoreContentType(true).followRedirects(true).execute().body();
        String data = String.valueOf(JsonToMapUtil.toMap(body).get("data"));
        List list = (List) JSONArray.parse(String.valueOf(JsonToMapUtil.toMap(data).get("feeds")));
        String video_spec_urls = String.valueOf(JsonToMapUtil.toMap(String.valueOf(list.get(0))).get("video_spec_urls"));
        String zore = String.valueOf(JsonToMapUtil.toMap(video_spec_urls).get("0"));
        String  videoUrl = String.valueOf(JsonToMapUtil.toMap(zore).get("url"));
        String download = DownloadUtil.download(videoUrl);
        return download;
    }
}
