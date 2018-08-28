package com.hoolai.chatmonitor.open.controller;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("user")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        AdminUser loginUser = LoginContext.getLoginUser(request);
        if (loginUser != null) {
            String url = "main";
            ModelAndView model = new ModelAndView(url);
            model.addObject("loginUser", loginUser);
            return model;
        }
        return new ModelAndView("user/login");
    }


    /**
     * 根据账号密码登录
     *
     * @param account  账号 (必填)
     * @param password 密码(必填)
     */
    @CrossOrigin
    @RequestMapping("loginByAccount")
    public ReturnValue<AdminUser> login(HttpServletRequest request, String account, String password) {

        ReturnValue<AdminUser> returnVal = adminUserService.loginByAccount(account, password);
        LoginContext.setAdminUser(request, returnVal.getValue());
        return returnVal;
    }


    /*新增新用户
     *
     * @param account 账号 (必填)
     * @param password 密码(可选) 默认:123456
     * @param email 邮箱 (可选)
     * @param phone 手机号(可选)
     * @param groupId 用户组(必填)
     *
     * */
    @GetMapping("register")
    public ReturnValue<AdminUser> register(AdminUser user) {

        ReturnValue<AdminUser> returnVal = adminUserService.register(user);
        return returnVal;
    }


    /*更改用户
     *
     * @param uid admin_user中的uid(必填)
     * @param password 密码(可选)
     * @param email 邮箱 (可选)
     * @param phone 手机号(可选)
     * @param groupId 用户组(可选)
     *
     * */
    @GetMapping("updateUserInfo")
    public ReturnValue<AdminUser> updateUserInfo(HttpServletRequest request, AdminUser user) {

        ReturnValue<AdminUser> returnVal = adminUserService.updateLoginInfo(user);
        user = LoginContext.getLoginUser(request);
        if (user != null && null != returnVal.getValue() && user.getUid() == returnVal.getValue().getUid()) {
            LoginContext.setAdminUser(request, returnVal.getValue());//更新用户在缓存中的信息
        }

        return returnVal;
    }

    /**
     * 用户列表
     *
     * @param groupId groupId (可选)
     * @param account account (可选)
     * @param email   email (可选)
     * @param phone   phone (可选)
     */
    @GetMapping("list")
    public ReturnValue<List<Map<String, Object>>> list(HttpServletRequest request, Integer groupId, String account, String email, String phone) {
//        if ((groupId == null || groupId == 0)) {
//            AdminUser user = LoginContext.getLoginUser(request);
//            if (!user.getAccount().equals("admin")) {
//                groupId = user.getGroupId();
//            }
//        }

        ReturnValue<List<Map<String, Object>>> gameVal = adminUserService.selectUserMapList(account, email, phone, groupId);
        return gameVal;
    }

}
