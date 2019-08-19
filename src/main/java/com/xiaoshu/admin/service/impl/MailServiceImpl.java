package com.xiaoshu.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoshu.admin.entity.FamilyMember;
import com.xiaoshu.admin.mapper.FamilyMemberMapper;
import com.xiaoshu.admin.service.MailService;
import com.xiaoshu.common.util.LunarUtil;
import com.xiaoshu.common.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    FamilyMemberMapper familyMemberMapper;

    @Override
    public void sendMail(String toUser, String subject, String text) {

        mailUtil.sendMail(toUser,subject,text);

    }

    @Override
    public void noticeBirthday(String userId,String nickName ,String mail) {

        Map<String,String> dateMap = LunarUtil.getLunarDate();
        String lunarDate = dateMap.get("lunar").toString();
        String newDate = dateMap.get("nowDate").toString();
        log.info("今天的农历日期是："+lunarDate);
        QueryWrapper<FamilyMember> ew = new QueryWrapper<>();
        ew.eq("user_id",userId);
        ew.eq("birth_day_lunar",lunarDate);
        ew.ne("is_send","1");
        // 获取本月生日的成员列表
        List<FamilyMember> listTodayBirth = familyMemberMapper.selectList(ew);
        if(listTodayBirth != null && listTodayBirth.size()>0){
            String birthNames = " ";
            for(FamilyMember birth: listTodayBirth){
                String name = birth.getName();
                String isSend = birth.getIsSend();
                String id = birth.getId();
                birth.setIsSend("1");
                familyMemberMapper.updateSendRemark(id);
                birthNames = birthNames + name + " ";
            }
            String subject = "家庭成员生日提醒";
            String text = "可爱而又迷人的"+ nickName + ",今天是" +newDate+",农历"+ lunarDate+ ",您的家人"+ birthNames+"在今天过生日哦！记得送上生日祝福哟！么么哒！";
            mailUtil.sendMail(mail,subject,text);
        }else{
            log.info("今天没有人过生日哦");
        }


    }
}
