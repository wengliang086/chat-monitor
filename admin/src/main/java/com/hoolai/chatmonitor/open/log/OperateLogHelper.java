package com.hoolai.chatmonitor.open.log;

import com.alibaba.fastjson.JSON;
import com.hoolai.chatmonitor.open.aspect.AuthAspect;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminLog;
import com.hoolai.chatmonitor.open.model.UserLoginResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class OperateLogHelper {


    public static AdminLog createOperatorLog(Object result, String extend1, String extend2) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        UserLoginResponse user = AuthAspect.get(request);
        String account = "account";
        if (user != null) {
            account = user.getAccount();
        }

        //String ip = RequestContextHolderUtils.getRequestIp();
        AdminLog operateLog = new AdminLog();
        operateLog.setAccount(account);
        operateLog.setMethod(request.getServletPath());
        operateLog.setResult(null == result ? null : result instanceof String ? result.toString() : JSON.toJSONString(result));
        operateLog.setOpData(new Date());
        operateLog.setExtend1(extend1);
        operateLog.setExtend2(extend2);
        return operateLog;
    }


}
