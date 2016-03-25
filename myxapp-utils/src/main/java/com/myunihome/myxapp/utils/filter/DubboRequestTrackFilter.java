package com.myunihome.myxapp.utils.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.base.exception.BusinessException;
import com.myunihome.myxapp.base.exception.CallerException;
import com.myunihome.myxapp.base.vo.BaseInfo;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.util.CollectionUtil;
import com.myunihome.myxapp.paas.util.StringUtil;
import com.myunihome.myxapp.paas.util.UUIDUtil;
import com.myunihome.myxapp.utils.util.DubboExceptAssembler;

public class DubboRequestTrackFilter implements Filter {
	//private static final String REQUEST_PARAM = "request_param";
	private static final Logger LOG = LoggerFactory.getLogger(DubboRequestTrackFilter.class.getName());
	
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws CallerException {
		String reqSV=invoker.getInterface().getName();
		String reqMethod=invocation.getMethodName();
		Object[] requestParams=invocation.getArguments();
		//交易序列
		String tradeSeq = UUIDUtil.genId32();
		//打印请求参数明细
		if (CollectionUtil.isEmpty(requestParams)) {
            LOG.info("TRADE_SEQ:{},拦截类型:{},请求类(接口):{},请求方法:{},请求参数:{}"
            		,tradeSeq, "Dubbo请求参数拦截(无参数)", reqSV, reqMethod, "");
        } else {            
            LOG.info("TRADE_SEQ:{},拦截类型:{},请求类(接口):{},请求方法:{},请求参数:{}"
            		,tradeSeq, "Dubbo请求参数拦截(有参数)", reqSV, reqMethod, JSON.toJSONString(requestParams));
        }
        //ThreadLocalUtils.set(REQUEST_PARAM, tradeSeq);
        //执行结果
        Result result=null;
        try{
        	//校验租户ID是否存在
        	/*boolean isOK=validateTenantId(requestParams);
        	if(!isOK){
        		throw new BusinessException(MyXAppPaaSConstant.ExceptionCode.PARAM_IS_NULL, "租户ID(tenantId)不能为空！");
        	}*/
        	result=invoker.invoke(invocation);
        	if(result.hasException()){
        		LOG.error("TRADE_SEQ:{},Dubbo服务端执行{}类中的{}方法发生异常", tradeSeq,reqSV, reqMethod,result.getException().getMessage());
        		throw DubboExceptAssembler.assemble(result.getException());        		
        	}
        	LOG.info("TRADE_SEQ:{},拦截类型:{},请求结果:{}"
        			,tradeSeq, "Dubbo请求返回结果拦截", JSON.toJSONString(result.getValue()));
        
          return result;
        }        
        catch(Exception ex){
        	LOG.error("TRADE_SEQ:{},执行{}类中的{}方法发生异常:", tradeSeq,reqSV, reqMethod);
        	RpcResult r = new RpcResult();
            r.setException(DubboExceptAssembler.assemble(ex));
            return r;
        }
		
	}
	/**
	 * 校验租户ID是否为空
	 * @param requestParams
	 * @return
	 * @author gucl
	 */
	private boolean validateTenantId(Object[] requestParams) {
		boolean isOK=true;
		if(requestParams!=null){
			for (Object param : requestParams) {
				if(param instanceof BaseInfo){
					BaseInfo tmpBaseInfo=(BaseInfo)param;
					if(StringUtil.isBlank(tmpBaseInfo.getTenantId())){
						isOK=false;
						break;
					}
				}
			}
		}
		return isOK;		
	}

}
