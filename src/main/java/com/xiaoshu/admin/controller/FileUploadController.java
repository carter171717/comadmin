package com.xiaoshu.admin.controller;

import com.xiaoshu.admin.service.UploadService;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.util.QiniuUtil;
import com.xiaoshu.common.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("upload")
@Slf4j
public class FileUploadController {

    @Autowired
    UploadService uploadService;

    @Autowired
    QiniuUtil qiniuUtil;

    @SysLog("上传图片")
    @PostMapping("uploadPic")
    @ResponseBody
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        if(file == null){
            return ResponseEntity.failure("上传文件为空 ");
        }
        String url = null;
        Map map = new HashMap();
        try {
            url = uploadService.upload(file);
            map.put("url", url);
            map.put("name", file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.failure(e.getMessage());
        }
        return ResponseEntity.success("操作成功").setAny("data",map);
    }

    @SysLog("上传图片")
    @PostMapping("/qiniu/images")
    @ResponseBody
    public ResponseEntity uploadFileToQiNiu(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
        if(file == null){
            return ResponseEntity.failure("上传文件为空 ");
        }
        String url = null;
        Map map = new HashMap();
        try {
            url = qiniuUtil.uploadImgToQiNiu(file);
            url = "http://"+url;
            map.put("url", url);
            map.put("name", file.getOriginalFilename());
            log.info("上传到七牛云的图片路径是：{}",url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.failure(e.getMessage());
        }
        return ResponseEntity.success("操作成功").setAny("data",map);
    }

}
