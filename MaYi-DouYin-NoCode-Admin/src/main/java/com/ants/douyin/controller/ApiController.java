package com.ants.douyin.controller;

import com.ants.douyin.util.douyin.DouYinUtil;
import com.ants.douyin.util.file.FtpUtil;
import com.ants.douyin.util.huoshan.HuoShanUtil;
import com.ants.douyin.util.json.JsonToMapUtil;
import com.ants.douyin.util.kuaishou.KuaiShouUtil;
import com.ants.douyin.util.pipixia.PiPiXiaUtil;
import com.ants.douyin.util.result.Result;
import com.ants.douyin.util.weishi.WeiShiUtil;
import com.ants.douyin.util.wx.XmlUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private FtpUtil ftpUtil;

    @Value("${mayi.ftpUrl}")
    private String ftpUrl;

    @Value("${mayi.ftpPath}")
    private String ftpPath;

    @RequestMapping("nocode")
    public Result nocode(String url) throws IOException {
        String s = "";
        if(url.contains("https://v.douyin.com/")){
            // 抖音
            s = DouYinUtil.downLoad(url);
        } else if (url.contains("https://v.kuaishou.com/")) {
            // 快手
            s = KuaiShouUtil.downLoad(url);
        } else if (url.contains("https://share.huoshan.com")) {
            // 火山
            s = HuoShanUtil.download(url);
        } else if (url.contains("https://h5.pipix.com")) {
            // 皮皮虾
            s = PiPiXiaUtil.download(url);
        } else if (url.contains("https://isee.weishi.qq.com")) {
            // 微视
            s = WeiShiUtil.download(url);
        } else {
            // 其他
            return new Result(201,"请确保分享链接正确，程序猿正在紧急上线",null);
        }
        return new Result(200,"解析成功",s);
    }

    @RequestMapping("/callback")
    public void callback(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, Object> map = XmlUtil.parseXML(request.getInputStream());
        String openid = String.valueOf(map.get("FromUserName"));
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        // 上传到本地服务器
        String localPath = ftpPath;
        File localFile = new File(localPath);
        if(!localFile.exists()) {
            //如果没有目录应该创建目录
            localFile.mkdirs();
        }
        //获取图片名称
        String localFileName = UUID.randomUUID()+".mp4";
//        String localFileName = "1.mp4";
        String path = localPath+localFileName;
        //文件实现上传
        file.transferTo(new File(path));
        String video_url = ftpUrl+localFileName;
        String video_path = localPath+localFileName;
        Map<String,String> map = new HashMap<>();
        map.put("video_url",video_url);
        map.put("video_path",video_path);
        return new Result(200,"上传成功",map);
    }

    @RequestMapping("/md5")
    public Result md5(@RequestParam("dataPath") String dataPath) {
        File file0 = new File(dataPath);
        String localFileName = UUID.randomUUID()+".mp4";
        try {
            File file1 = new File(ftpPath + "black.mp4");
            OutputStream out=new FileOutputStream(ftpPath + localFileName);
            InputStream in0=new FileInputStream(file0);
            InputStream in1=new FileInputStream(file1);
            byte[] b=new byte[1024*1024];
            int len=0;
            while((len=in0.read(b))!=-1)
            {
                out.write(b);
            }
            while((len=in1.read(b))!=-1)
            {
                out.write(b);
            }
            in0.close();
            in1.close();
            out.close();
            // 删除file0
            if(file0.exists()){
                file0.delete();
            }
        } catch (FileNotFoundException e) {
            if(file0.exists()){
                file0.delete();
            }
            e.printStackTrace();
        } catch (IOException e) {
            if(file0.exists()){
                file0.delete();
            }
            e.printStackTrace();
        }
        String video_url = ftpUrl+localFileName;
        return new Result(200,"去除视频md5成功",video_url);
    }
}
