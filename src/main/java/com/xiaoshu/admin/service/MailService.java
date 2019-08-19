package com.xiaoshu.admin.service;

public interface MailService {

    public void sendMail(String toUser, String subject, String text);

    public void noticeBirthday(String userId,String nickName,String mail);
}
