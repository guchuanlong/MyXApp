package com.myunihome.myxapp.utils.aop.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.myunihome.myxapp.paas.util.CollectionUtil;
import com.myunihome.myxapp.paas.util.ThreadLocalUtils;
import com.myunihome.myxapp.paas.util.UUIDUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * Dubbo请求参数日志记录
 *
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class DubboRequestTracking {

    private static final String REQUEST_PARAM = "request_param";

    private static final Logger LOG = LogManager.getLogger(DubboRequestTracking.class);

    public void printReturn(JoinPoint joinPoint, Object returnVal) {
        String reqSV = joinPoint.getTarget().getClass().getName();
        String reqMethod = joinPoint.getSignature().getName();
        String tradeSeq = ThreadLocalUtils.get(REQUEST_PARAM);
        if (StringUtils.isBlank(tradeSeq))
            {LOG.error("没有发现交易ID");}
        String rpString = null;
        try {
            rpString = JSON.toJSONString(returnVal).toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            rpString = new Gson().toJson(returnVal);
        }
        LOG.error("{OPERATE_TYPE:{},OPERATE_CODE:{},OPERATE_DESC:{},OPERATE_DATA:[DUBBO_REQUEST_CLASS:{},DUBBO" +
                        "_REQUEST_METHOD:{},TRADE_SEQ:{},RESPONSE_PARAM:{}]}", "Dubbo请求参数拦截", "20003", "Dubbo有请求返回参数",
                reqSV, reqMethod, tradeSeq, rpString);
    }

    public void beforeInvorkMethod(JoinPoint joinPoint) {
        String tradeSeq = UUIDUtil.genId32();
        String reqSV = joinPoint.getTarget().getClass().getName();
        String reqMethod = joinPoint.getSignature().getName();
        Object[] requestParams = joinPoint.getArgs();
        JSONObject p = new JSONObject();
        if (CollectionUtil.isEmpty(requestParams)) {
            LOG.error("{OPERATE_TYPE:{},OPERATE_CODE:{},OPERATE_DESC:{},OPERATE_DATA:[DUBBO_REQUEST_CLASS:{},DUBBO" +
                            "_REQUEST_METHOD:{},TRADE_SEQ:{},REQUEST_PARAM:{}]}", "Dubbo请求参数拦截", "20001", "Dubbo没有请求参数",
                    reqSV, reqMethod, tradeSeq, "");
        } else {
            int i = 0;
            for (Object param : requestParams) {
                String paramStr = null;
                if (param != null) {
                    try {
                        paramStr = JSON.toJSONString(param);
                    } catch (Exception e) {
                        LOG.error("翻译对象类型[{}]出错",param.getClass());
                    	LOG.error(e.getMessage(),e);
                        try {
                            paramStr = JSON.toJSONString(param);
                        } catch (Exception ee) {
                            LOG.error(ee.getMessage(),ee);
                            paramStr = param.toString();
                        }
                    }
                }
                p.put("P_" + i, paramStr);
            }
            LOG.error("{OPERATE_TYPE:{},OPERATE_CODE:{},OPERATE_DESC:{},OPERATE_DATA:[DUBBO_REQUEST_CLASS:{},DUBBO" +
                            "_REQUEST_METHOD:{},TRADE_SEQ:{},REQUEST_PARAM:{}]}", "Dubbo请求参数拦截", "20001", "Dubbo有请求参数",
                    reqSV, reqMethod, tradeSeq, p);
        }
        ThreadLocalUtils.set(REQUEST_PARAM, tradeSeq);
    }

}
