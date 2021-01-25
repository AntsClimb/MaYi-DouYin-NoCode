package com.ants.douyin.util.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @author dadan
 * @date 2020/10/12 8:40
 * @explain http工具类
 */
public class HttpUtil {

    public static String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }

    public static String doPost(String url, Map<String, String> param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            arrayList.add(new BasicNameValuePair(key, param.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
        return execute(httpPost);
    }


    private static String execute(HttpRequestBase request) throws IOException, ClientProtocolException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        if (200 == response.getStatusLine().getStatusCode()) {
            return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } else {
            System.out.println(EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        }
        return "";
    }
}
