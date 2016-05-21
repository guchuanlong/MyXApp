package com.myunihome.myxapp.utils.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myunihome.myxapp.base.exception.SystemException;

/**
 * SSO配置文件工具类
 *
 * Date: 2015年11月29日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * @author gucl
 */
public final class SSOUtil {

	private static Logger logger=LoggerFactory.getLogger(SSOUtil.class);
    private static Properties prop= new Properties();
	private SSOUtil() {
	}

	static {
		try {

			InputStream inStream = SSOUtil.class.getClassLoader().getResourceAsStream("sso.properties");
			prop.load(inStream);
			
			logger.debug("加载sso.properties完毕");
		} catch (Exception e) {
			logger.debug("加载sso.properties失败，具体原因："+e.getMessage());
			throw new SystemException("加载sso.properties失败",e);
		}
	}
	
	/**
	 * 单点登录服务器地址
	 * @return
	 * @author gucl
	 */
	public static String getCasServerLoginUrl(){
		return prop.getProperty("casServerLoginUrl", "").trim();
	}
	public static String getIframeCasServerLoginUrl(){
		return prop.getProperty("iframeCasServerLoginUrl", "").trim();
	}
	public static String getCasServerUrlPrefix(){
		return prop.getProperty("casServerUrlPrefix", "").trim();
	}
	public static String getServerName(){
		return prop.getProperty("serverName", "").trim();
	}
	public static String getEncoding(){
		return prop.getProperty("encoding", "").trim();
	}
	public static String getLoginType(){
		return prop.getProperty("loginType", "").trim();
	}
	public static String getLogOutServerUrl(){
		return prop.getProperty("logOutServerUrl", "").trim();
	}
	public static String getLogOutBackUrl(){
		return prop.getProperty("logOutBackUrl", "").trim();
	}
	public static String getTenantId(){
		return prop.getProperty("tenantId", "").trim();
	}
	public static String getProperty(String key){
		return prop.getProperty(key, "").trim();
	}

	
}
