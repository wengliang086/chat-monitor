package com.hoolai.chatmonitor.open.controller;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@PermissionAnnotation(PermissionType.LOGINED)
@RestController
@RequestMapping("group")
public class AdminGroupController {

    @Resource
    private AdminGroupService adminGroupService;

    @GetMapping("list")
    public ReturnValue<List<AdminGroup>> list(String name) {
        List<AdminGroup> list = adminGroupService.list(name);

        ReturnValue<List<AdminGroup>> returnVal = new ReturnValue<List<AdminGroup>>();
        returnVal.setValue(list);

        return returnVal;
    }

    @GetMapping("add")//新增组
    public AdminGroup register(HttpServletRequest request, String name) {
        return adminGroupService.add(name);
    }

    @GetMapping("update")//更改组信息
    public AdminGroup updateUserInfo(HttpServletRequest request, Integer groupId, String name) {
        return adminGroupService.update(groupId, name);
    }

}
