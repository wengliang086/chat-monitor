package com.hoolai.chatmonitor.open.model;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

public class UserLoginResponse {

    private Long uid;
    private String account;
    private String email;
    private String phone;
    private Integer groupId;
    private String accessToken;

    public UserLoginResponse(AdminUser user) {
        this.uid = user.getUid();
        this.account = user.getAccount();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.groupId = user.getGroupId();
        this.accessToken = createToken(user.getPassword());
    }

    private String createToken(String pwd) {
        long time = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30);
        String srcStr = uid + "." + pwd + "." + time;
        return DigestUtils.md5DigestAsHex(srcStr.getBytes()) + "." + time;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
