package com.xiaoshu.admin.controller;


import com.xiaoshu.admin.service.CarBillService;
import com.xiaoshu.admin.service.RoleService;
import com.xiaoshu.admin.service.UploadService;
import com.xiaoshu.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/table")
@Slf4j
public class CarTableController {
    @Autowired
    UserService userService;
    @Autowired
    CarBillService carBillService;
    @Autowired
    RoleService roleService;
    @Autowired
    UploadService uploadService;

    @GetMapping("/carTable")
    public String main(ModelMap map){
        return "admin/table/carTable";
    }

}
