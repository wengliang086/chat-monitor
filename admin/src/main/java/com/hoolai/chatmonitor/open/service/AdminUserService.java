package com.hoolai.chatmonitor.open.service;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermission;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;

import java.util.List;
import java.util.Map;

public interface AdminUserService {

    /**
     * 根据uid获取用户
     *
     * @param udid
     * @return
     * @throws HException
     */
    AdminUser loginByUdid(long udid) throws HException;

    /**
     * 根据账号获取用户
     *
     * @param account
     * @param password
     * @return
     * @throws HException
     */
    AdminUser loginByAccount(String account, String password) throws HException;

    /*
     * @param account         账户名
     * @param password        密码
     * @param email           邮箱
     * @param phone           电话
     * @return
     * @throws HException
     */
    //注册用户信息
    AdminUser register(AdminUser user) throws HException;

    //更新用户信息
    AdminUser updateLoginInfo(AdminUser user) throws HException;

    //后台操作用户列表
    List<Map<String, Object>> selectUserMapList(String account, String email, String phone, Integer groupId);

    List<AdminPermission> getPermissions(boolean isAdmin);
}
