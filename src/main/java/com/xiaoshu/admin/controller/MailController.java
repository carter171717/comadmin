package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.mapper.UserMapper;
import com.xiaoshu.admin.service.MailService;
import com.xiaoshu.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("admin/carbill")
@Slf4j
public class MailController  extends BaseService {

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Scheduled(cron = "0 0/60 0/1 * * ? ") // 每60分钟执行一次
    public void MailNoticeBirthdaySchedule(){
            QueryWrapper<User> ew = new QueryWrapper<>();
            ew.eq("del_flag",0);
            List<User> listUser =  userMapper.selectList(ew);
            for( User user : listUser){
                log.info("当前登录的ID为：{}", user.getLoginName());
                String userId = user.getId();
                String mail = user.getEmail(); //获取登录用户的email
                String nickName = user.getNickName();
                //获取
                mailService.noticeBirthday(userId, nickName, mail);
            }

    }

}



