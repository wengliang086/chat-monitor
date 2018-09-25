package com.hoolai.chatmonitor.open.service.impl;


import com.google.common.base.Strings;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.AdminPermissionDao;
import com.hoolai.chatmonitor.open.dao.AdminUserDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermission;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    @Resource
    private AdminGroupDao adminGropuDao;

    @Resource
    private AdminPermissionDao adminPermissionDao;

    //根据uid获取用户信息
    @Override
    public AdminUser loginByUdid(long uid) throws HException {
        AdminUser loginInfo = adminUserDao.get(uid);
        if (loginInfo == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.UID_NOT_FIND).build();
        }
        return loginInfo;
    }

    //根据账号、密码获取用户信息
    @Override
    public AdminUser loginByAccount(String account, String password) throws HException {
        AdminUser loginInfo = adminUserDao.getByAccountAndPwd(account, encryPassword(password));
        if (loginInfo == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.LOGIN_FAILED).build();
        }
        return loginInfo;
    }

    //密码加密
    private String encryPassword(String password) {
        if (password.length() == 32) {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
        } else {
            password = DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(password.getBytes()).getBytes());
        }
        return password;
    }

    //注册新用户信息
    @Override
    public AdminUser register(AdminUser user) throws HException {
        String account = user.getAccount();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        Integer groupId = user.getGroupId();
        if (Strings.isNullOrEmpty(account)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.ACCOUNT_IS_INVALID).build();
        }
        checkAccount(account);
        // 帐号可以输入邮箱格式，如果是邮箱格式帐号的话，两列都存
        if (isEmail(account) && Strings.isNullOrEmpty(email)) {
            email = account;
        }

        AdminUser loginInfo = adminUserDao.getByPassport(account, null, null);//根据账号查找是否已经存在过了

        if (isAccountExist(loginInfo)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.ACCOUNT_ALREDAY_EXIST).build();
        }

        if (Strings.isNullOrEmpty(password)) {
            password = "123456";
        } else {
            checkPassword(password);//检查密码的合法性
        }

        password = encryPassword(password);

        existGroup(groupId);

        AdminUser registerInfo = new AdminUser();
        registerInfo.setAccount(account);
        registerInfo.setEmail(email);
        registerInfo.setPassword(password);
        registerInfo.setPhone(phone);
        registerInfo.setGroupId(groupId);
        adminUserDao.save(registerInfo);//新用户入库
        return registerInfo;
    }

    //判断账号是否合法
    private void checkAccount(String account) {
        if (Strings.isNullOrEmpty(account) || (!Pattern.matches("^[a-zA-Z][a-zA-z_0-9]*[a-zA-Z0-9]$", account))) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.ACCOUNT_IS_INVALID).build();
        }
    }

    //判断email是否合法
    private boolean isEmail(String email) {
        return email != null && Pattern.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", email);
    }

    //判断密码是否合法
    private void checkPassword(String password) {
        if (Strings.isNullOrEmpty(password)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.PASSWORD_CAN_NOT_BE_EMPTY).build();
        }
        if (password.length() == 32) {
            return;
        }
        if (!Pattern.matches("[0-9a-zA-z`~!@#\\$%\\^&\\*\\(\\)\\-_=+\\[\\]\\{\\};:'\",<>./?\\\\|]{6,20}", password)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.PASSWORD_IS_INVALID).build();
        }
    }

    //判断账号是否存在
    private boolean isAccountExist(AdminUser loginInfo) {
        if (loginInfo != null) {
            return true;
        }
        return false;
    }

    private void existGroup(Integer groupId) {

        if (groupId == null || groupId == 0) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_ID_IS_NULL).build();
        }

        AdminGroup group = adminGropuDao.get(groupId);
        if (group == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NOT_EXIST).build();
        }
    }

    //修改用户信息
    @Override
    public AdminUser updateLoginInfo(AdminUser user)
            throws HException {
        Long uid = user.getUid();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        Integer groupId = user.getGroupId();
        AdminUser userInfo = adminUserDao.get(uid);
        if (userInfo == null) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.UID_NOT_FIND).build();
        }

        // 帐号可以输入邮箱格式，如果是邮箱格式帐号的话，两列都存
        if (Strings.isNullOrEmpty(email) && isEmail(userInfo.getAccount())) {
            email = userInfo.getAccount();
        }

        if (!Strings.isNullOrEmpty(password)) {
            checkPassword(password);//检查密码的合法性
            password = encryPassword(password);
            userInfo.setPassword(password);
        }

        if (!(null == groupId || groupId == 0)) {
            existGroup(groupId);//检测group是否存
            userInfo.setGroupId(groupId);
        }

        userInfo.setEmail(Strings.isNullOrEmpty(email) ? userInfo.getEmail() : email);
        userInfo.setPhone(Strings.isNullOrEmpty(phone) ? userInfo.getPhone() : phone);

        adminUserDao.update(userInfo);//修改用户

        return userInfo;
    }

    //用户列表
    @Override
    public List<Map<String, Object>> selectUserMapList(
            String account, String email, String phone, Integer groupId) {
        List<Map<String, Object>> list = adminUserDao.selectUserMapList(account, email, phone, groupId);
        ReturnValue<List<Map<String, Object>>> returnVal = new ReturnValue<List<Map<String, Object>>>();
        returnVal.setValue(list);
        return list;
    }

    @Override
    public List<AdminPermission> getPermissions(boolean isAdmin) {
        return adminPermissionDao.getPermissions(isAdmin);
    }
}
