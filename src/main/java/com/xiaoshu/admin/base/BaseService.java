package com.xiaoshu.admin.base;

import com.xiaoshu.admin.entity.User;
import com.xiaoshu.admin.service.UserService;
import com.xiaoshu.common.realm.AuthRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BaseService {

    @Autowired
    UserService userService;

    public User getLoginUserInfo(){
        AuthRealm.ShiroUser shiroUser = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserId = shiroUser.getId();
        User user =  userService.findUserById(currentUserId);
        return user;
    }

}
