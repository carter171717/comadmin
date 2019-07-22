package com.xiaoshu.common.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class QiniuUtil {

    @Value("${spring.qiniu.accessKey}")
    private String accessKey;

    @Value("${spring.qiniu.secretKey}")
    private String secretKey;

    @Value("${spring.qiniu.bucket}")
    private String bucket;

    @Value("${spring.qiniu.path}")
    private String path;

    private static int counter = 0;

    /**
     * 将图片上传到七牛云
     *
     * @param file
     * @param key  保存在空间中的名字，如果为空会使用文件的hash值为文件名
     * @return
     */
    public String uploadImg(FileInputStream file, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String return_path = path + "/" + putRet.key;
                return return_path;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "";
    }


    /**
     * 上传图片到七牛云服务器
     *
     * @param file 需要上传的文件
     * @return 上传后的文件地址
     */
    public String uploadImgToQiNiu(MultipartFile file) {
        //检验文件大小
        String imgPath = "";
        String originalFilename = file.getOriginalFilename();
        //编码文件名
        String randomName = encodingFilename(originalFilename);
        Configuration configuration = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(configuration);
        //create auth for qi niu
        Auth auth = Auth.create(accessKey, secretKey);
        String token =  auth.uploadToken(bucket);
        Response response = null;
        try {
            response = uploadManager.put(file.getInputStream(), randomName, token, null, null);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            imgPath = path + File.separator + defaultPutRet.key;
            //log.info("upload img to qi niu success,the path of img is {}", imgPath);
        } catch (QiniuException e) {
            Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return imgPath;
    }


    /**
     * 编码文件名
     */
    public static final String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++);
        return filename;
    }
}

