package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.CarBill;
import com.xiaoshu.admin.entity.MyBlog;
import com.xiaoshu.admin.entity.Role;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.*;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.base.PageData;
import com.xiaoshu.common.util.DateUtil;
import com.xiaoshu.common.util.LocalDateTimeUtils;
import com.xiaoshu.common.util.ResponseEntity;
import com.xiaoshu.common.util.UUIDHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("admin/blog")
@Slf4j
public class MyBlogController extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    CarBillService carBillService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;
    @Autowired
    MyBlogService myBlogService;

    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/blog/list";
    }

    @RequiresPermissions("sys:blog:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<MyBlog> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<MyBlog> blogPageData = new PageData<>();
        QueryWrapper<MyBlog> ew = new QueryWrapper<>();

        if(!user.getLoginName().equals("admin")){ //admin 可以查看所有的记录
            ew.eq("user_id",user.getId());
        }
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                ew.eq("is_admin", "admin".equals(type) ? true : false);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                ew.and(wrapper -> wrapper.like("title", keys).or().like("summary", keys).or().like("create_by", keys));
            }
        }
        ew.orderByDesc("update_time");
        IPage<MyBlog> blogPage = myBlogService.page(new Page<>(page,limit),ew);
        blogPageData.setCount(blogPage.getTotal());
        blogPageData.setData(blogPage.getRecords());
        return blogPageData;
    }


    @GetMapping("add")
    public String add(ModelMap modelMap){
        List<Role> roleList = roleService.selectAll();
        modelMap.put("roleList",roleList);
        return "admin/blog/add";
    }

    @RequiresPermissions("sys:blog:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增博文数据")
    public ResponseEntity add(@RequestBody  MyBlog blog){
        //验证卡片必须的参数
        if(StringUtils.isBlank(blog.getTitle())){
            return ResponseEntity.failure("博客标题不能为空");
        }
        if(StringUtils.isBlank(blog.getSummary())){
            return ResponseEntity.failure("博客摘要不能为空");
        }
        if(StringUtils.isBlank(blog.getContent())){
            return ResponseEntity.failure("博客内容不能为空");
        }
        if(StringUtils.isBlank(blog.getCreateBy())){
            return ResponseEntity.failure("博客作者不能为空");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        User user = getLoginUserInfo();
        log.info("当前登录的用户为：{}",user.getLoginName());
        blog.setId(UUIDHelper.getUUID32());
        blog.setUserId(user.getId());
        blog.setUserName(user.getLoginName());
        blog.setCreateBy(blog.getCreateBy());
        String today = DateUtil.getCurrDate();
        blog.setCreateDay(today);
        blog.setRemark(blog.getRemark());
        blog.setStatus("0"); //0初始化草稿箱，1 已发布
        //保存消费信息
        myBlogService.save(blog);
        return ResponseEntity.success("操作成功");
    }

    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        MyBlog blog = myBlogService.getById(id);
        String roleIds = "";
        List<Role> roleList = roleService.selectAll();
        modelMap.put("localBlog",blog);
        modelMap.put("roleIds",roleIds);
        modelMap.put("roleList",roleList);
        return "admin/blog/edit";
    }


    @RequiresPermissions("sys:blog:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存博客编辑数据")
    public ResponseEntity edit(@RequestBody MyBlog blog){
        if(StringUtils.isBlank(blog.getId())){
            return ResponseEntity.failure("ID不能为空");
        }
        if(StringUtils.isBlank(blog.getTitle())){
            return ResponseEntity.failure("博客标题不能为空");
        }
        if(StringUtils.isBlank(blog.getSummary())){
            return ResponseEntity.failure("博客摘要不能为空");
        }
        if(StringUtils.isBlank(blog.getContent())){
            return ResponseEntity.failure("博客内容不能为空");
        }
        blog.setUpdateTime(LocalDateTimeUtils.convertDateToLDT(new Date()));
        blog.setStatus("0"); //编辑后需要重新发布
        myBlogService.updateById(blog);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:blog:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        MyBlog blog = myBlogService.getById(id);
        if(blog == null){
            return ResponseEntity.failure("用户不存在");
        }
        myBlogService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

    @PostMapping("deploy")
    @ResponseBody
    @SysLog("发布博客")
    public ResponseEntity deploy(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        MyBlog blog = myBlogService.getById(id);
        if(blog == null){
            return ResponseEntity.failure("用户不存在");
        }
        blog.setStatus("1");
        myBlogService.updateById(blog);
        return ResponseEntity.success("操作成功");
    }

    @PostMapping("fallBack")
    @ResponseBody
    @SysLog("发布博客")
    public ResponseEntity fallBack(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        MyBlog blog = myBlogService.getById(id);
        if(blog == null){
            return ResponseEntity.failure("用户不存在");
        }
        blog.setStatus("0");
        myBlogService.updateById(blog);
        return ResponseEntity.success("操作成功");
    }




}
