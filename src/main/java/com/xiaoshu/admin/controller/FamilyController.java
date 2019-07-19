package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.CarBill;
import com.xiaoshu.admin.entity.FamilyMember;
import com.xiaoshu.admin.entity.Role;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.*;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.base.PageData;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/family")
@Slf4j
public class FamilyController  extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;

    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/family/list";
    }

    @RequiresPermissions("sys:family:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<FamilyMember> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                       @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                       ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<FamilyMember> cardPageData = new PageData<>();
        QueryWrapper<FamilyMember> ew = new QueryWrapper<>();
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
                ew.and(wrapper -> wrapper.like("name", keys).or().like("sex", keys).or().like("birth_day_new", keys).or().like("birth_day_lunar", keys));
            }
        }
        ew.orderByAsc("birth_day_new");
        IPage<FamilyMember> cardPage = familyMemberService.page(new Page<>(page,limit),ew);
        cardPageData.setCount(cardPage.getTotal());
        cardPageData.setData(cardPage.getRecords());
        return cardPageData;
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        List<Role> roleList = roleService.selectAll();
        modelMap.put("roleList",roleList);
        return "admin/family/add";
    }

    @RequiresPermissions("sys:family:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增消费记录数据")
    public ResponseEntity add(@RequestBody  FamilyMember member){
        //验证卡片必须的参数
        if(StringUtils.isBlank(member.getName())){
            return ResponseEntity.failure("姓名不能为空");
        }
        if(StringUtils.isBlank(member.getSex())){
            return ResponseEntity.failure("性别不能为空");
        }
        if(StringUtils.isBlank(member.getBirthDayNew())){
            return ResponseEntity.failure("新历生日日期不能为空");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        User user = getLoginUserInfo();
        log.info("当前登录的用户为：{}",user.getLoginName());
        member.setId(UUIDHelper.getUUID32());
        member.setUserId(user.getId());
        member.setUserName(user.getLoginName());
        //保存消费信息
        familyMemberService.save(member);
        return ResponseEntity.success("操作成功");
    }


    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        FamilyMember member = familyMemberService.getById(id);
        String roleIds = "";
        List<Role> roleList = roleService.selectAll();
        modelMap.put("localFamily",member);
        modelMap.put("roleIds",roleIds);
        modelMap.put("roleList",roleList);
        return "admin/family/edit";
    }


    @RequiresPermissions("sys:family:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存信用卡编辑数据")
    public ResponseEntity edit(@RequestBody FamilyMember member){
        if(StringUtils.isBlank(member.getId())){
            return ResponseEntity.failure("ID不能为空");
        }
        if(StringUtils.isBlank(member.getName())){
            return ResponseEntity.failure("姓名不能为空");
        }
        if(StringUtils.isBlank(member.getSex())){
            return ResponseEntity.failure("性别不能为空");
        }
        if(StringUtils.isBlank(member.getBirthDayNew())){
            return ResponseEntity.failure("新历生日日期不能为空");
        }
        familyMemberService.updateById(member);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:family:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        FamilyMember member = familyMemberService.getById(id);
        if(member == null){
            return ResponseEntity.failure("成员不存在");
        }
        familyMemberService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

}
