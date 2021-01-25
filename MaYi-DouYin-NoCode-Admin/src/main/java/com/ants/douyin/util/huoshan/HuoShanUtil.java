package com.ants.douyin.util.huoshan;

import com.alibaba.fastjson.JSONObject;
import com.ants.douyin.util.download.DownloadUtil;
import com.ants.douyin.util.json.JsonToMapUtil;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class HuoShanUtil {

    public static String download(String url) throws IOException {
        url = "https://share.huoshan.com/hotsoon/s/"+url.split("https://share.huoshan.com/hotsoon/s/")[1].substring(0,11);
        String s = Jsoup.connect(url).execute().url().toString();
        String substring = s.substring(s.length() - 19);
        String newUrl = "https://share.huoshan.com/api/item/info?item_id="+substring;
        String body = Jsoup.connect(newUrl).ignoreHttpErrors(true).ignoreContentType(true).execute().body();
        Map<String, Object> map = JsonToMapUtil.analysis(JSONObject.parseObject(body));
        String videoUrl = String.valueOf(map.get("url"));
        String video_id = videoUrl.split("video_id=")[2].split("&line=")[0];
        String endUrl = "http://hotsoon.snssdk.com/hotsoon/item/video/_playback/?video_id="+video_id;
        String download = DownloadUtil.download(endUrl);
        return download;
    }
}
