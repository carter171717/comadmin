package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.entity.MyBlog;
import com.xiaoshu.admin.service.MyBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("web")
@Slf4j
public class FrontWebController {

    @Autowired
    MyBlogService myBlogService;

    @GetMapping(value = {"","/index"})
    public String webIndex(String category,ModelMap modelMap) {
        log.info("要查询的日志类别是："+category);
        QueryWrapper<MyBlog> ew = new QueryWrapper<>();
        ew.eq("status","1");
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
