package com.xiaoshu.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.base.BaseService;
import com.xiaoshu.admin.entity.FamilyMember;
import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.mapper.FamilyMemberMapper;
import com.xiaoshu.admin.mapper.UserMapper;
import com.xiaoshu.admin.service.MailService;
import com.xiaoshu.admin.service.UserService;
import com.xiaoshu.common.util.LunarUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

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

    @Scheduled(cron = "0 0/15 0/1 * * ? ") // 每十五分钟执行一次
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
            for (int i = 0; i < 10; i++) {
                System.out.println("霍华德会加盟湖人吗？" + i);
            }
    }

}



