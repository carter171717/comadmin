package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.CarBill;
import com.xiaoshu.admin.entity.CarIncome;
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
@RequestMapping("admin/carIncome")
@Slf4j
public class CarIncomeController extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    CarIncomeService carIncomeService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;


    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/carIncome/list";
    }

    @RequiresPermissions("sys:carIncome:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<CarIncome> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<CarIncome> cardPageData = new PageData<>();
        QueryWrapper<CarIncome> ew = new QueryWrapper<>();
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
                ew.and(wrapper -> wrapper.like("passenger_name", keys).or().like("sex", keys).or().like("ride_day", keys));
            }
        }
        ew.orderByDesc("ride_day");
        IPage<CarIncome> carIncomePage = carIncomeService.page(new Page<>(page,limit),ew);
        cardPageData.setCount(carIncomePage.getTotal());
        cardPageData.setData(carIncomePage.getRecords());
        return cardPageData;
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        List<Role> roleList = roleService.selectAll();
        modelMap.put("roleList",roleList);
        return "admin/carIncome/add";
    }

    @RequiresPermissions("sys:carIncome:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增消费记录数据")
    public ResponseEntity add(@RequestBody  CarIncome income){
        //验证卡片必须的参数
        if(StringUtils.isBlank(income.getPassengerName())){
            return ResponseEntity.failure("乘客姓名不能为空");
        }
        if(StringUtils.isBlank(income.getSex())){
            return ResponseEntity.failure("乘客性别不能为空");
        }
        if(StringUtils.isBlank(income.getRideDay())){
            return ResponseEntity.failure("乘车日期不能为空");
        }
        if(StringUtils.isBlank(income.getFee())){
            return ResponseEntity.failure("乘车费用不能为空");
        }
        if(StringUtils.isBlank(income.getStartLocation())){
            return ResponseEntity.failure("乘车起点不能为空");
        }
        if(StringUtils.isBlank(income.getEndLocation())){
            return ResponseEntity.failure("乘车终点不能为空");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        User user = getLoginUserInfo();
        log.info("当前登录的用户为：{}",user.getLoginName());
        income.setId(UUIDHelper.getUUID32());
        income.setUserId(user.getId());
        income.setUserName(user.getLoginName());
        //保存消费信息
        carIncomeService.save(income);
        return ResponseEntity.success("操作成功");
    }


    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        CarIncome income = carIncomeService.getById(id);
        String roleIds = "";
        List<Role> roleList = roleService.selectAll();
        modelMap.put("local",income);
        modelMap.put("roleIds",roleIds);
        modelMap.put("roleList",roleList);
        return "admin/carIncome/edit";
    }


    @RequiresPermissions("sys:carIncome:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存信用卡编辑数据")
    public ResponseEntity edit(@RequestBody CarIncome income){
        if(StringUtils.isBlank(income.getId())){
            return ResponseEntity.failure("ID不能为空");
        }
        if(StringUtils.isBlank(income.getPassengerName())){
            return ResponseEntity.failure("乘客姓名不能为空");
        }
        if(StringUtils.isBlank(income.getSex())){
            return ResponseEntity.failure("乘客性别不能为空");
        }
        if(StringUtils.isBlank(income.getRideDay())){
            return ResponseEntity.failure("乘车日期不能为空");
        }
        if(StringUtils.isBlank(income.getFee())){
            return ResponseEntity.failure("乘车费用不能为空");
        }
        if(StringUtils.isBlank(income.getStartLocation())){
            return ResponseEntity.failure("乘车起点不能为空");
        }
        if(StringUtils.isBlank(income.getEndLocation())){
            return ResponseEntity.failure("乘车终点不能为空");
        }
        carIncomeService.updateById(income);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:carIncome:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        CarIncome income = carIncomeService.getById(id);
        if(income == null){
            return ResponseEntity.failure("用户不存在");
        }
        carIncomeService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

}
