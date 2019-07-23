package com.xiaoshu.admin.controller;

import com.xiaoshu.admin.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/carbill")
@Slf4j
public class MailController {

    @Autowired
    MailService mailService;



}



