package com.hoolai.chatmonitor.open.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;


public class LoginContext {
    /*
    public final static String SESSION_NAME = "login_user";
    private static Map<String, Long> token_client = new HashMap<String, Long>();
    
    public final static AdminUser getLoginUser(HttpServletRequest request) {
    	AdminUser loginUser = (AdminUser) request.getSession().getAttribute(SESSION_NAME);
        return loginUser;
    }
    
    public final static void setAdminUser(HttpServletRequest request, AdminUser user) {
        request.getSession().setAttribute(SESSION_NAME, user);
    }
    public final static boolean validateClientToken(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	if (Strings.isNullOrEmpty(token)) {
			token = request.getHeader("token");
			if (Strings.isNullOrEmpty(token)) {
				return false;
			}
		}
    	
    	if(token_client.containsKey(token)) {
    		if (!Strings.isNullOrEmpty(request.getParameter("uid"))) {   
    			if (token_client.get(token).longValue() != Long.parseLong(request.getParameter("uid"))) {
					return false;
				}
    		}
    		return true;
    	}
    	return true;
    }
    
    public final static String setToken(HttpServletRequest request, Long uid) {
    	String token = UUID.randomUUID().toString();
    	token_client.put(token, uid);
    	return token;
    }
    
    public final static void exitClient(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	if(token_client.containsKey(token))
    		token_client.remove(token);
    }*/
}
