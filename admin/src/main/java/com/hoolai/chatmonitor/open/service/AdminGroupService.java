package com.hoolai.chatmonitor.open.service;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;

import java.util.List;

public interface AdminGroupService {

    List<AdminGroup> list(String name) throws HException;

    //新增用户组
    AdminGroup add(String groupName) throws HException;

    //修改用户组信息
    AdminGroup update(Integer groupId, String groupName) throws HException;

    //用户组列表
//	ReturnValue<List<AdminGroup>> list(Integer groupId,String groupName,List<Integer>groupIds) throws HException;
}
