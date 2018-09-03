package com.hoolai.chatmonitor.open.controller;

import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.model.UserLoginResponse;
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

@PermissionAnnotation(PermissionType.LOGINED)
@RestController
@RequestMapping("user")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @PermissionAnnotation(PermissionType.PUBLIC)
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        return new ModelAndView("user/login");
    }

    /**
     * 根据账号密码登录
     *
     * @param account  账号 (必填)
     * @param password 密码(必填)
     */
    @PermissionAnnotation(PermissionType.PUBLIC)
    @CrossOrigin
    @RequestMapping("loginByAccount")
    public UserLoginResponse loginByAccount(HttpServletRequest request, String account, String password) {
        AdminUser adminUser = adminUserService.loginByAccount(account, password);
        return new UserLoginResponse(adminUser);
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
    @PermissionAnnotation(PermissionType.PUBLIC)
    @GetMapping("register")
    public AdminUser register(AdminUser user) {
        return adminUserService.register(user);
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
    public AdminUser updateUserInfo(HttpServletRequest request, AdminUser user) {
        AdminUser adminUser = adminUserService.updateLoginInfo(user);
        return adminUser;
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
    public List<Map<String, Object>> list(HttpServletRequest request, Integer groupId, String account, String email, String phone) {
//        if ((groupId == null || groupId == 0)) {
//            AdminUser user = LoginContext.getLoginUser(request);
//            if (!user.getAccount().equals("admin")) {
//                groupId = user.getGroupId();
//            }
//        }
        return adminUserService.selectUserMapList(account, email, phone, groupId);
    }

}
