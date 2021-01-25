package com.ants.douyin.util.file;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class FtpUtil {

    /**
     * 上传数据文件
     * <p>
     * //
     */
    @Value("${mayi.ftpAddress}")
    private String ftpAddress;

    @Value("${mayi.ftpPort}")
    private Integer ftpPort;

    @Value("${mayi.ftpUsername}")
    private String ftpUsername;

    @Value("${mayi.ftpPassword}")
    private String ftpPassword;

    @Value("${mayi.ftpUrl}")
    private String ftpUrl;

    @Value("${mayi.ftpPath}")
    private String ftpPath;

    public String uploadFile(MultipartFile file) {


        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(ftpAddress, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            int replyCode = ftpClient.getReplyCode();
            ftpClient.setDataTimeout(120000);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//设置为二进制文件
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
//            System.out.println("FTP连接失败");
                return "FTP连接失败";
            } else {
//            System.out.println("FTP连接成功");
            }
            // 获取文件全名a.py
//        String fileName = file.getOriginalFilename();
            String fileName = file.getName();
            if (fileName.indexOf("\\") != -1) {
                fileName = fileName.substring(fileName.lastIndexOf("\\"));
            }
            //System.out.println(fileName);
            fileName = UUID.randomUUID() + "." + fileName.split("\\.")[1];
            // 本地文件存放指定路径

            // 文件上传路径
            String filePath = ftpPath;
            //System.out.println("文件路径:" + filePath);
            InputStream in = file.getInputStream();
            filePath = filePath + fileName;
            String removePath = new String(filePath.getBytes("UTF-8"), "iso-8859-1");
            //上传
            // 开启被动模式
//            ftpClient.enterLocalPassiveMode();
            if (ftpClient.storeFile(removePath, in)) {
//                System.out.println("FTP文件上传成功");
            } else {
//                System.out.println("FTP文件上传失败");
            }
            //关闭文件流
            in.close();
            //关闭连接
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            String url = ftpUrl + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}
