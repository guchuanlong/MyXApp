package com.myunihome.myxapp.utils.aop.dubbo;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.util.ClazzUtil;
import com.myunihome.myxapp.paas.util.StringUtil;
import com.myunihome.myxapp.utils.aop.sandbox.APISetting;
import com.myunihome.myxapp.utils.aop.sandbox.SandboxUtil;

/**
 * 拦截dubbo请求沙箱环境<br>
 * Date: 2015年9月22日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author zhangchao
 */
public class DubboRequestSandboxInterceptor {

    private static Log LOG = LogFactory.getLog(DubboRequestSandboxInterceptor.class);

    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object respParam = null;
        String interfaceName = jp.getTarget().getClass().getInterfaces()[0].getName();
        String methodName = jp.getSignature().getName();
        APISetting setting = SandboxUtil.getAPISetting(interfaceName, methodName);
        LOG.debug("拦截到服务[" + interfaceName + "][" + methodName + "]的沙箱配置信息:"
                + JSON.toJSONString(setting));
        if (setting != null && APISetting.CALL_SANDBOX.equals(setting.getCallType())) {
            String sandboxResp = setting.getSandboxResp();
            LOG.debug("服务[" + interfaceName + "][" + methodName + "]的走沙箱模拟返回结果["+sandboxResp+"]");
            Class<?> interfaceClass = ClazzUtil.getInterfaceClazz(interfaceName);
            List<java.lang.reflect.Method> methods = ClazzUtil.getMethods(interfaceClass,
                    methodName);
            Method method = methods.get(0);
            if(StringUtil.isBlank(sandboxResp)){
                respParam = null;
            }else{
                respParam = JSON.parseObject(sandboxResp, method.getReturnType());
            }
        } else {
            LOG.debug("服务[" + interfaceName + "][" + methodName + "]的走真实调用方式");
            respParam = jp.proceed();
        }

        return respParam;
    }

}
