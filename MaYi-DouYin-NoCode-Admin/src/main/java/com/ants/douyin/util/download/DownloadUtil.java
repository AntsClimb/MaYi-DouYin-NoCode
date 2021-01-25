package com.ants.douyin.util.download;

import org.jsoup.Jsoup;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DownloadUtil {

    private static final String basic = "FTP对应的域名或IP地址";

    public static String download(String playUrl) throws IOException {
        String pathUrl = "/assets/video/"+Math.random() * 100+".mp4";
        Map<String, String> headers = new HashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("Host", "aweme.snssdk.com");
        headers.put("User-Agent", "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        //6.利用Joup获取视频对象,并作封装成一个输入流对象
        BufferedInputStream in = Jsoup.connect(playUrl).headers(headers).timeout(10000).ignoreContentType(true).execute().bodyStream();
        //7.封装一个保存文件的路径对象
        File fileSavePath = new File(pathUrl);
        //注:如果保存文件夹不存在,那么则创建该文件夹
        File fileParent = fileSavePath.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        //8.新建一个输出流对象downLoad
        OutputStream out = new BufferedOutputStream(new FileOutputStream(fileSavePath));
        //9.遍历输出文件
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        out.close();//关闭输出流
        in.close(); //关闭输入流
        return basic+pathUrl;
    }
}
