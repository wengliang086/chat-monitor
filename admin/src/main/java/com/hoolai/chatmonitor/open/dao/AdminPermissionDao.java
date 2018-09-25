package com.hoolai.chatmonitor.open.dao;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermission;
import java.lang.Long;
import java.util.List;


public interface AdminPermissionDao extends Dao<AdminPermission,Long>{

	
	////*******自定义开始********//
    List<AdminPermission> getPermissions(boolean isAdmin);
	//**********自定义结束*****////

}
