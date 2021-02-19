package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.DriverInfo;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.DriverInfoService;
import com.xiaoshu.admin.service.UserService;
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
@RequestMapping("admin/driver")
@Slf4j
public class DriverController extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    DriverInfoService driverInfoService;

    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/driver/list";
    }

    @GetMapping("add")
    public String add(ModelMap modelMap){
        return "admin/driver/add";
    }

    @GetMapping("edit")
    public String edit(String id,ModelMap modelMap){
        //User user = userService.findUserById(id);
        DriverInfo driverInfo = driverInfoService.getById(id);
        String roleIds = "";
        modelMap.put("driver",driverInfo);
        return "admin/driver/edit";
    }

    @RequiresPermissions("sys:driver:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<DriverInfo> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                     @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                     ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<DriverInfo> driverPageData = new PageData<>();
        QueryWrapper<DriverInfo> driverWapper = new QueryWrapper<>();
        if(!user.getLoginName().equals("admin")){ //admin 可以查看所有的记录
            //billWrapper.eq("user_id",user.getId());
        }
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                driverWapper.and(wrapper -> wrapper.like("driver_name", keys).or().like("car_num", keys).or().like("user_name", keys).or().like("driver_phone", keys));
            }
        }
        driverWapper.orderByDesc("create_time");
        IPage<DriverInfo> driverPage = driverInfoService.page(new Page<>(page,limit),driverWapper);
        driverPageData.setCount(driverPage.getTotal());

        List<DriverInfo> list = driverPage.getRecords();
        for(DriverInfo driverInfo : list){
            if("0".equals(driverInfo.getStatus())){
                driverInfo.setStatus("禁用");
            }else{
                driverInfo.setStatus("正常");
            }
        }
        driverPageData.setData(list);
        return driverPageData;
    }



    @RequiresPermissions("sys:driver:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增消费记录数据")
    public ResponseEntity add(@RequestBody  DriverInfo driverInfo){
        //验证卡片必须的参数
        if(StringUtils.isBlank(driverInfo.getDriverName())){
            return ResponseEntity.failure("车主名称不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getDriverPhone())){
            return ResponseEntity.failure("车主电话不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarType())){
            return ResponseEntity.failure("车辆类型不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarNum())){
            return ResponseEntity.failure("车牌号不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarColor())){
            return ResponseEntity.failure("车辆颜色不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getUserName())){
            return ResponseEntity.failure("用户账号不能为空");
        }
        User user =  userService.findUserByLoginName(driverInfo.getUserName());
        if(null == user){
            return ResponseEntity.failure("用户账号有误");
        }
        driverInfo.setId(UUIDHelper.getUUID32());
        driverInfo.setUserId(user.getId());
        driverInfo.setUserName(user.getLoginName());
        driverInfo.setStatus("1");
        //保存消费信息
        driverInfoService.save(driverInfo);
        return ResponseEntity.success("操作成功");
    }




    @RequiresPermissions("sys:driver:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("编辑车主数据")
    public ResponseEntity edit(@RequestBody DriverInfo driverInfo){
        //验证卡片必须的参数
        if(StringUtils.isBlank(driverInfo.getDriverName())){
            return ResponseEntity.failure("车主名称不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getDriverPhone())){
            return ResponseEntity.failure("车主电话不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarType())){
            return ResponseEntity.failure("车辆类型不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarNum())){
            return ResponseEntity.failure("车牌号不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getCarColor())){
            return ResponseEntity.failure("车辆颜色不能为空");
        }
        if(StringUtils.isBlank(driverInfo.getUserName())){
            return ResponseEntity.failure("用户账号不能为空");
        }
        driverInfoService.updateById(driverInfo);
        return ResponseEntity.success("操作成功");
    }


    @RequiresPermissions("sys:driver:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除数据(单个)")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        DriverInfo driverInfo = driverInfoService.getById(id);
        if(driverInfo == null){
            return ResponseEntity.failure("车主不存在");
        }
        driverInfoService.removeById(id);
        return ResponseEntity.success("操作成功");
    }

}
