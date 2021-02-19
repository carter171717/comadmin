package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.DriverInfo;
import com.xiaoshu.admin.entity.OrderInfo;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.mapper.DriverInfoMapper;
import com.xiaoshu.admin.mapper.OrderInfoMapper;
import com.xiaoshu.admin.service.DriverInfoService;
import com.xiaoshu.admin.service.OrderInfoService;
import com.xiaoshu.common.annotation.SysLog;
import com.xiaoshu.common.base.PageData;
import com.xiaoshu.common.enums.OrderStatusEnum;
import com.xiaoshu.common.util.DateUtil;
import com.xiaoshu.common.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping("admin/order")
@Slf4j
public class OrderController extends BaseService {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    DriverInfoService driverInfoService;

    @Autowired
    DriverInfoMapper driverInfoMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;



    @GetMapping("list")
    @SysLog("跳转列表页面")
    public String list(){
        return "admin/order/list";
    }


    @RequiresPermissions("sys:order:list")
    @PostMapping("/list")
    @ResponseBody
    public PageData<OrderInfo> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getLoginName());

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        log.info("map的值：{}",map.toString());
        PageData<OrderInfo> orderPageData = new PageData<>();
        String userId = user.getId();
        QueryWrapper<DriverInfo> ewDriver = new QueryWrapper<>();
        ewDriver.eq("user_id",userId);
        ewDriver.eq("status","1");
        DriverInfo driverInfo =  driverInfoMapper.selectOne(ewDriver);
        if(null == driverInfo){
           return  null;
        }
        QueryWrapper<OrderInfo> ew = new QueryWrapper<>();
        if(!user.getLoginName().equals("admin") && !user.getLoginName().equals("curry")){ //admin 可以查看所有的记录
            ew.and(wrapper -> wrapper.eq("status", 0).or().eq("driver_id", driverInfo.getId()));
        }
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            String orderDate = (String) map.get("orderDate");
            if(StringUtils.isNotBlank(keys)) {
                ew.and(wrapper -> wrapper.like("passenger_name", keys).or().like("phone", keys));
            }
            if(StringUtils.isNotBlank(orderDate)) {
                ew.and(wrapper -> wrapper.eq("order_date", orderDate));
            }
        }
        ew.orderByDesc("update_time");
        IPage<OrderInfo> orderPage = orderInfoService.page(new Page<>(page,limit),ew);
        orderPageData.setCount(orderPage.getTotal());
        orderPageData.setData(orderPage.getRecords());
        return orderPageData;
    }

    @PostMapping("takeOrder")
    @ResponseBody
    @SysLog("接单")
    public ResponseEntity takeOrder(@RequestParam(value = "id",required = false)String id){
        User user = getLoginUserInfo();
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        OrderInfo orderInfo = orderInfoService.getById(id);
        if(orderInfo == null){
            return ResponseEntity.failure("订单不存在");
        }
        String userId = user.getId();
        //查询司机信息

        QueryWrapper<DriverInfo> ew = new QueryWrapper<>();
        ew.eq("user_id",userId);
        ew.eq("status","1");
        DriverInfo driverInfo =  driverInfoMapper.selectOne(ew);
        if(null == driverInfo){
            return ResponseEntity.failure("司机账号异常，请联系管理员");
        }
        orderInfo.setDriverId(driverInfo.getId());
        orderInfo.setDriverName(driverInfo.getDriverName());
        orderInfo.setDriverPhone(driverInfo.getDriverPhone());
        orderInfo.setCarNum(driverInfo.getCarNum());
        orderInfo.setTakeTime(DateUtil.getCurrentTime());
        orderInfo.setStatus(OrderStatusEnum.TAKED.value);
        orderInfoService.updateById(orderInfo);
        return ResponseEntity.success("操作成功");
    }

    @PostMapping("fallBack")
    @ResponseBody
    @SysLog("取消接单")
    public ResponseEntity fallBack(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        OrderInfo orderInfo = orderInfoService.getById(id);
        if(orderInfo == null){
            return ResponseEntity.failure("订单不存在");
        }
        orderInfo.setStatus(OrderStatusEnum.INIT.value);
        orderInfo.setCancelTime(DateUtil.getCurrentTime());
        orderInfoService.updateById(orderInfo);
        orderInfoMapper.fallBack(id);
        return ResponseEntity.success("操作成功");
    }



}
