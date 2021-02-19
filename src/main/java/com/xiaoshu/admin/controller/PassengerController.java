package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.entity.MyBlog;
import com.xiaoshu.admin.entity.OrderInfo;
import com.xiaoshu.admin.entity.vo.OrderTableVo;
import com.xiaoshu.admin.entity.vo.OrderVo;
import com.xiaoshu.admin.mapper.OrderInfoMapper;
import com.xiaoshu.admin.service.OrderInfoService;
import com.xiaoshu.common.base.PageData;
import com.xiaoshu.common.enums.OrderStatusEnum;
import com.xiaoshu.common.enums.RouteEnum;
import com.xiaoshu.common.util.ResponseEntity;
import com.xiaoshu.common.util.UUIDHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("letsGo")
@Slf4j
public class PassengerController {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @GetMapping(value = {"","/index"})
    public String webIndex(String category, ModelMap modelMap) {
        log.info("要查询的日志类别是："+category);

        QueryWrapper<MyBlog> ew = new QueryWrapper<>();
        ew.eq("status","1");
        if(StringUtils.isNotBlank(category)){
            if(!"全部".equals(category)){
                ew.eq("category",category);
            }
        }
        //System.out.println("这里是博客列表页面了,一共" + list.size()+"篇博客");
        return "admin/passenger/passenger";
    }

    @PostMapping("orderAdd")
    @ResponseBody
    public ResponseEntity orderAdd(@RequestBody OrderInfo orderInfo){
        //验证卡片必须的参数
        if(StringUtils.isBlank(orderInfo.getPassengerName())){
            return ResponseEntity.failure("请输入乘客昵称");
        }
        if(StringUtils.isBlank(orderInfo.getRoute())){
            return ResponseEntity.failure("请选择路线");
        }
        if(StringUtils.isBlank(orderInfo.getPhone())){
            return ResponseEntity.failure("手机号码不能为空");
        }
        if(StringUtils.isBlank(orderInfo.getOrderNum())){
            return ResponseEntity.failure("请输入乘车人数");
        }
        if(StringUtils.isBlank(orderInfo.getOrderDate())){
            return ResponseEntity.failure("请选择出发日期");
        }
        if(StringUtils.isBlank(orderInfo.getOrderTime())){
            return ResponseEntity.failure("请填写出发时间");
        }
        if(StringUtils.isBlank(orderInfo.getStartAddress())){
            return ResponseEntity.failure("请填写上车地址");
        }
        if(StringUtils.isBlank(orderInfo.getEndAddress())){
            return ResponseEntity.failure("请填写下车地址");
        }
        if(orderInfo.getPhone().length() != 11){
            return ResponseEntity.failure("手机号码有误");
        }
        //先查询是否存在当日未完成订单如果存在则不允许添加

        QueryWrapper<OrderInfo> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("order_date",orderInfo.getOrderDate());
        orderWrapper.eq("phone",orderInfo.getPhone());
        orderWrapper.eq("route",orderInfo.getRoute());
        orderWrapper.ne("status",OrderStatusEnum.CANCEL.value);

        int count =  orderInfoMapper.selectCount(orderWrapper);
        if(count >0 ){
            return ResponseEntity.failure("您今日已经预约过啦，请勿重复预约");
        }
        //shiro 获取当前登录用户的ID和姓名 补充到card信息中
        log.info("开始准备写入数据库啦");
        orderInfo.setId(UUIDHelper.getUUID32());
        orderInfo.setStatus(OrderStatusEnum.INIT.value);
        orderInfo.setRouteName(RouteEnum.getEnum(orderInfo.getRoute()).desc);
        orderInfoMapper.insert(orderInfo);

        return ResponseEntity.success("预约成功，请等待司机接单");
    }

    @RequestMapping("queryOrder")
    @ResponseBody
    public OrderVo queryOrder(@RequestBody OrderInfo orderInfo){
        OrderVo orderVo = new OrderVo();
        String orderDate = orderInfo.getOrderDate();
        String phone = orderInfo.getPhone();
        System.out.println("预约日期的值="+orderDate);
        System.out.println("电话的值="+phone);
        QueryWrapper<OrderInfo> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("order_date",orderDate);
        orderWrapper.eq("phone",phone);
        orderWrapper.ne("status",OrderStatusEnum.CANCEL.value);
        List<OrderInfo> list =  orderInfoMapper.selectList(orderWrapper);
        if(null != list && list.size()>0){
            OrderInfo order= list.get(0);
            orderVo.setStatus(order.getStatus());
            orderVo.setDriverPhone(order.getDriverPhone());
            orderVo.setDriverName(order.getDriverName());
            orderVo.setCarNum(order.getCarNum());
        }else{
            orderVo.setStatus("99"); //没有找到订单
        }
        return orderVo;
    }

    @RequestMapping("orderList")
    @ResponseBody
    public OrderTableVo orderList(ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        System.out.println("map的值="+map.toString());
        log.info("map的值：{}",map.toString());
        PageData<OrderInfo> cardPageData = new PageData<>();
        QueryWrapper<OrderInfo> orderWrapper = new QueryWrapper<>();
        OrderTableVo orderVo = new OrderTableVo();

        if(!map.isEmpty()){
            String phone = (String) map.get("phone");
            String orderDate = (String) map.get("orderDate");
            System.out.println("要查询的电话号码="+phone);
            System.out.println("要查询的预约日期="+orderDate);
            log.info("要查询的电话号码={}，预约日期={}",phone,orderDate);

        }else{
            log.info("获取到的参数为空");
        }
        orderVo.setCode(0);
        List<OrderInfo> list =  orderInfoMapper.selectList(new QueryWrapper<OrderInfo>());
        orderVo.setCount(list.size());
        orderVo.setMsg("查询成功");
        orderVo.setData(list);
        return orderVo;
    }


}
