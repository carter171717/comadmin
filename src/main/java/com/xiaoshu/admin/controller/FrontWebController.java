package com.xiaoshu.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.entity.MyBlog;
import com.xiaoshu.admin.service.MailService;
import com.xiaoshu.admin.service.MyBlogService;
import com.xiaoshu.common.util.ResponseEntity;
import com.xiaoshu.rabbitmq.MsgProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("web")
@Slf4j
public class FrontWebController {

    @Autowired
    MyBlogService myBlogService;

    @Autowired
    MailService mailService;

    @Autowired
    MsgProducer msgProducer;

    @GetMapping(value = {"/sendEmailTest"})
    @ResponseBody
    public ResponseEntity sendEmail(String category,ModelMap modelMap) {
        MyBlog blog = new MyBlog();
        blog.setTitle("这是一篇神秘的博客");
        blog.setRemark("这是备注");
        String blogStr = JSON.toJSONString(blog);
        //mailService.sendMail("814199688@qq.com","测试2","你好，小伙伴！很高兴认识你");

        for(int i=1 ; i<= 5; i++){
            msgProducer.sendMsg(blogStr);
            //msgProducer.sendMsg("帅气而又迷人的阿凌同学，我给你发了第"+i+"个邮件");
        }
        return ResponseEntity.success("操作成功");
    }

    @GetMapping(value = {"","/index"})
    public String webIndex(String category,ModelMap modelMap) {
        log.info("要查询的日志类别是："+category);

        QueryWrapper<MyBlog> ew = new QueryWrapper<>();
        ew.eq("status","1");
        if(StringUtils.isNotBlank(category)){
            if(!"全部".equals(category)){
                ew.eq("category",category);
            }
        }
        List<MyBlog> list  = myBlogService.list(ew);
        //System.out.println("这里是博客列表页面了,一共" + list.size()+"篇博客");
        modelMap.put("blogList",list);
        return "admin/front/index";
    }

    @GetMapping("/article/{blogId}.html")
    public String article(@PathVariable String blogId, Model model) {
        System.out.println("这里是博客详情页面了");
        MyBlog blog = myBlogService.getById(blogId);
        //只能访问是已经发表的文章
        if (blog == null) {
            return "error/404";
        }
        model.addAttribute("blog", blog);
        return "admin/front/article";
    }

}
