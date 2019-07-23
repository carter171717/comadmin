package com.xiaoshu.admin.service.impl;

import com.xiaoshu.admin.service.MailService;
import com.xiaoshu.common.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailUtil mailUtil;

    @Override
    public void sendMail(String toUser, String subject, String text) {

        mailUtil.sendMail(toUser,subject,text);

    }


}
