package com.xiaoshu.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.CreditCard;
import com.xiaoshu.admin.entity.Role;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.mapper.CreditCardMapper;
import com.xiaoshu.admin.service.CreditCardService;
import com.xiaoshu.admin.service.RoleService;
import com.xiaoshu.admin.service.UploadService;
import com.xiaoshu.admin.service.UserService;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.base.PageData;
import com.xiaoshu.common.realm.AuthRealm;
import com.xiaoshu.common.util.Constants;
import com.xiaoshu.common.util.ResponseEntity;
import com.xiaoshu.common.util.UUIDHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fugl
 * @since 2019-07-03
 */
@Controller
@RequestMapping("admin/creditCard")
@Slf4j
public class CreditCardController extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    CreditCardService creditCardService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;


    @GetMapping("list")
    @SysLog("跳转信用卡列表页面")
    public String list(){
        return "admin/creditCard/list";
    }

    @RequiresPermissions("sys:card:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<CreditCard> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                     @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                     ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<CreditCard> cardPageData = new PageData<>();
        QueryWrapper<CreditCard> cardWrapper = new QueryWrapper<>();
        if(!user.getLoginName().equals("admin")){ //admin 可以查看所有的记录
            cardWrapper.eq("user_id",user.getId());
        }
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                cardWrapper.eq("is_admin", "admin".equals(type) ? true : false);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                cardWrapper.and(wrapper -> wrapper.like("bank_name", keys).or().like("card_owner", keys).or().like("card_num", keys));
            }
        }
        cardWrapper.orderByAsc("sort");
        IPage<CreditCard> cardPage = creditCardService.page(new Page<>(page,limit),cardWrapper);
        cardPageData.setCount(cardPage.getTotal());
        cardPageData.setData(cardPage.getRecords());
        return cardPageData;
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        List<Role> roleList = roleService.selectAll();
        modelMap.put("roleList",roleList);
        return "admin/creditCard/add";
    }

    @RequiresPermissions("sys:card:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增卡片数据")
    public ResponseEntity add(@RequestBody  CreditCard card){
        //验证卡片必须的参数
        if(StringUtils.isBlank(card.getBankName())){
            return ResponseEntity.failure("银行名称不能为空");
        }
        if(StringUtils.isBlank(card.getCardNum())){
            return ResponseEntity.failure("信用卡卡号不能为空");
        }
        if(StringUtils.isBlank(card.getCardOwner())){
            return ResponseEntity.failure("持卡人姓名不能为空");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        User user = getLoginUserInfo();
        log.info("当前登录的用户为：{}",user.getLoginName());
        card.setId(UUIDHelper.getUUID32());
        card.setUserId(user.getId());
        card.setUserName(user.getLoginName());
        card.setStatus("1");
        //保存卡片信息
        //creditCardMapper.insert(card);
        creditCardService.save(card);
        return ResponseEntity.success("操作成功");
    }


    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        CreditCard card = creditCardService.getById(id);
        String roleIds = "";
        List<Role> roleList = roleService.selectAll();
        modelMap.put("localcard",card);
        modelMap.put("roleIds",roleIds);
        modelMap.put("roleList",roleList);
        return "admin/creditCard/edit";
    }


    @RequiresPermissions("sys:card:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存信用卡编辑数据")
    public ResponseEntity edit(@RequestBody CreditCard card){
        if(StringUtils.isBlank(card.getId())){
            return ResponseEntity.failure("ID不能为空");
        }
        if(StringUtils.isBlank(card.getBankName())){
            return ResponseEntity.failure("银行名称不能为空");
        }
        if(StringUtils.isBlank(card.getCardNum())){
            return ResponseEntity.failure("信用卡卡号不能为空");
        }
        if(StringUtils.isBlank(card.getCardOwner())){
            return ResponseEntity.failure("持卡人姓名不能为空");
        }
        creditCardService.updateById(card);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:card:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除系统用户数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        CreditCard card = creditCardService.getById(id);
        if(card == null){
            return ResponseEntity.failure("用户不存在");
        }
        creditCardService.removeById(id);
        return ResponseEntity.success("操作成功");
    }


}

