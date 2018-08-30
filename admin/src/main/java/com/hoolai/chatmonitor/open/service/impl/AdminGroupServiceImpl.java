package com.hoolai.chatmonitor.open.service.impl;


import com.google.common.base.Strings;
import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGroupService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

@Service
@Transactional
public class AdminGroupServiceImpl implements AdminGroupService {

    @Resource
    private AdminGroupDao adminGropuDao;

    @Override
    public List<AdminGroup> list(String name) throws HException {
        return adminGropuDao.list(name);
    }

    @Override
    public ReturnValue<AdminGroup> add(String groupName) throws HException {
        AdminGroup group = new AdminGroup();
        group.setGroupName(groupName);
        adminGropuDao.save(group);
        return createLoginResult(group);
    }

    @Override
    public ReturnValue<AdminGroup> update(Integer groupId,
                                          String groupName) throws HException {
        if (groupId == null || groupId == 0) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_ID_IS_NULL).build();
        }

        AdminGroup group = adminGropuDao.get(groupId);

        if (group == null) {
        	throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NOT_IEXIST).build();
        }

        if (Strings.isNullOrEmpty(groupName)) {
            throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NAME_IS_NULL).build();
        }

        group.setGroupName(groupName);
        adminGropuDao.update(group);
        return createLoginResult(group);
    }

    //生成returnvalue
    private ReturnValue<AdminGroup> createLoginResult(AdminGroup group) {
        ReturnValue<AdminGroup> returnVal = new ReturnValue<AdminGroup>();
        returnVal.setValue(group);
        return returnVal;
    }

}
