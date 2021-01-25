package com.ants.douyin.util.kuaishou;

import com.alibaba.fastjson.JSONObject;
import com.ants.douyin.util.download.DownloadUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class KuaiShouUtil {

    public static String downLoad (String url) throws IOException {
        // 获取重定向地址
        String sub = "https://v.kuaishou.com/"+url.split("https://v.kuaishou.com/")[1].substring(0, 6);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String body = Jsoup.connect(sub).headers(headers).execute().body();
        Document doc= Jsoup.parse(body);
        Elements videoElement = doc.select("script[type=text/javascript]");
        String videoInfo = videoElement.get(2).data().replaceAll("window.pageData= ","");
        JSONObject json = JSONObject.parseObject(videoInfo);
        String videoUrl=json.getJSONObject("video").getString("srcNoMark");
        return videoUrl;
    }
}
