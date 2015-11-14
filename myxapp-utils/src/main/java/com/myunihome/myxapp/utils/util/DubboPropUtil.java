package com.myunihome.myxapp.utils.util;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.exception.PaasRuntimeException;
import com.myunihome.myxapp.paas.util.CollectionUtil;
import com.myunihome.myxapp.paas.util.StringUtil;


public class DubboPropUtil {

    public static void setDubboProviderProperties() {
        JSONObject props = MyXAppConfHelper.getInstance().getDubboProviderConf();
        if (props == null) {
            return;
        }
        
        Set<String> keys=props.keySet();
        if(!CollectionUtil.isEmpty(keys)){
        	for(String key:keys){
        		String value = props.getString(key);
        		if (!StringUtil.isBlank(key)) {
        			if (StringUtil.isBlank(value)) {
        				throw new PaasRuntimeException("cannt get value for key[" + key + "] from ["
                                + MyXAppConfConstants.DUBBO_PROVIDER_CONF_PATH + "]");
        			}
        			System.setProperty(key.trim(), value);
        		}
        	}
        }
        
    }

    public static void setDubboConsumerProperties() {
    	JSONObject props = MyXAppConfHelper.getInstance().getDubboConsumerConf();
        if (props == null) {
            return;
        }
        
        Set<String> keys=props.keySet();
        if(!CollectionUtil.isEmpty(keys)){
        	for(String key:keys){
        		String value = props.getString(key);
        		if (!StringUtil.isBlank(key)) {
        			if (StringUtil.isBlank(value)) {
        				throw new PaasRuntimeException("cannt get value for key[" + key + "] from ["
                                + MyXAppConfConstants.DUBBO_CONSUMER_CONF_PATH + "]");
        			}
        			System.setProperty(key.trim(), value);
        		}
        	}
        }
    }

}
