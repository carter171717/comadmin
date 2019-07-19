package com.xiaoshu.admin.controller;

import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin/statistics")
@Slf4j
public class StatisticsController extends BaseService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;
    @Autowired
    CarIncomeService carIncomeService;
    @Autowired
    CreditCardService creditCardService;
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    CarBillService carBillService;

    // 获取主页的数据 一次性把报表的数据全部返回
    @RequestMapping("mainData")
    @ResponseBody
    public Map<String,Object> getMainData(){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = getLoginUserInfo();
        log.info("当前登录的ID为：{}",user.getId());

        map = statisticsService.getMainData(user.getId());

        return map;
    }


}
