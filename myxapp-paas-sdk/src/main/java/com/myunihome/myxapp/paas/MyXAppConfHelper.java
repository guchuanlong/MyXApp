package com.myunihome.myxapp.paas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.exception.PaasRuntimeException;
import com.myunihome.myxapp.paas.model.C3P0DataSourceConfig;
import com.myunihome.myxapp.paas.model.CacheConfigInfo;
import com.myunihome.myxapp.paas.model.DBCPDataSourceConfig;
import com.myunihome.myxapp.paas.model.DocStoreConfigInfo;
import com.myunihome.myxapp.paas.model.DruidDataSourceConfig;
import com.myunihome.myxapp.paas.model.HikariCPDataSourceConfig;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.util.StringUtil;
/**
 * MyXApp-PaaS平台全局配置工具类
 *
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public final class MyXAppConfHelper {

    private static MyXAppConfHelper instance = null;

    private static Properties prop = new Properties();

    private MyXAppConfHelper() { }

    public static MyXAppConfHelper getInstance() {
        if (instance == null) {
            loadProp();
            instance = new MyXAppConfHelper();
        }
        return instance;
    }

    private static void loadProp() {
        InputStream is = MyXAppConfHelper.class.getClassLoader().getResourceAsStream(
                MyXAppConfConstants.PAAS_CONFIG_FILE);
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new PaasRuntimeException("loding myapp paas config file failed", e);
        }
    }
    /**
     * 应用程序域.<br>
     * 应用程序域一般为网站的域名
     * @return
     * @author gucl
     */
    public String getAppDomain() {
    	String appDomain = prop.getProperty("paas.uniconfig.appDomain");
    	if (StringUtil.isBlank(appDomain)) {
    		throw new PaasRuntimeException("uniconfig appDomain is null");
    	}
    	return appDomain;
    }
    /**
     * 应用程序Id.<br>
     * @return
     * @author gucl
     */
    public String getAppId() {
    	String appId = prop.getProperty("paas.uniconfig.appId");
    	if (StringUtil.isBlank(appId)) {
    		throw new PaasRuntimeException("uniconfig appId is null");
    	}
    	return appId;
    }
    
    public String getAppDomainAndAppId(){
    	String appDomain = prop.getProperty("paas.uniconfig.appDomain");
    	if (StringUtil.isBlank(appDomain)) {
    		throw new PaasRuntimeException("uniconfig appDomain is null");
    	}
    	String appId = prop.getProperty("paas.uniconfig.appId");
    	if (StringUtil.isBlank(appId)) {
    		throw new PaasRuntimeException("uniconfig appId is null");
    	}
    	return appDomain+"."+appId;
    }
    /**
     * 配置中心类型.<br>
     * 目前仅支持zookeeper的配置中心paas.uniconfig.type=uniconfig_zk
     * @return
     * @author gucl
     */
    public String getUniConfigType() {
		String type = prop.getProperty("paas.uniconfig.type");
        if (StringUtil.isBlank(type)) {
            throw new PaasRuntimeException("uniconfig type is null");
        }
        return type;
	}
    /**
     * zookeeper配置信息
     * @return
     * @author gucl
     */
    public UniConfigZkInfo getUniConfigZkConf() {
        String zkAddress = prop.getProperty("paas.uniconfig.zkAddress");
        String zkAuthSchema = prop.getProperty("paas.uniconfig.zkAuthSchema");
        String zkUser = prop.getProperty("paas.uniconfig.zkUser");
        String zkPasswd = prop.getProperty("paas.uniconfig.zkPassword");
        String timeoutStr = prop.getProperty("paas.uniconfig.zkTimeout");
        int timeout=0;
        if (StringUtil.isBlank(zkAddress)) {
            throw new PaasRuntimeException("paas uniconfig zkAddress is null");
        }
        if (StringUtil.isBlank(zkAuthSchema)) {
            throw new PaasRuntimeException("paas uniconfig zkAuthSchema is null");
        }
        if (StringUtil.isBlank(zkUser)) {
            throw new PaasRuntimeException("paas uniconfig zkUser is null");
        }
        if (StringUtil.isBlank(zkPasswd)) {
            throw new PaasRuntimeException("paas uniconfig zkPasswd is null");
        }
        if (!StringUtil.isBlank(timeoutStr)) {
            timeout=Integer.parseInt(timeoutStr);
        }
        
        return new UniConfigZkInfo(zkAddress,zkAuthSchema,zkUser,zkPasswd,timeout);
    }
    /**
     * 缓存（Redis）配置数据
     * @return
     * @author gucl
     */
    public CacheConfigInfo getCacheConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String cacheConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_CACHE_CONFIG_PATH);
        if (StringUtil.isBlank(cacheConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because cacheConfig in UniConfig is blank");
        }
        CacheConfigInfo config=JSON.parseObject(cacheConfig, CacheConfigInfo.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because cacheConfig is null");
        }
        return config;
		
	}
    /**
     * 文档存储配置
     * @return
     * @author gucl
     */
    public DocStoreConfigInfo getDocStoreConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dssConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DOCSTORE_CONFIG_PATH);
        if (StringUtil.isBlank(dssConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because dssConfig in UniConfig is blank");
        }
        DocStoreConfigInfo config=JSON.parseObject(dssConfig, DocStoreConfigInfo.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because dssConfig is null");
        }
        return config;
		
	}
    public HikariCPDataSourceConfig getHikariCPDataSourceConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_HIKARICP_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure HikariCP Config in UniConfig is blank");
        }
        HikariCPDataSourceConfig config=JSON.parseObject(dsConfig, HikariCPDataSourceConfig.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure HikariCP is null");
        }
        return config;		
	}
    public DruidDataSourceConfig getDruidDataSourceConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_DRUID_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure Druid Config in UniConfig is blank");
        }
        DruidDataSourceConfig config=JSON.parseObject(dsConfig, DruidDataSourceConfig.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure Druid is null");
        }
        return config;		
	}
    public DBCPDataSourceConfig getDBCPDataSourceConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_DBCP_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure DBCP Config in UniConfig is blank");
        }
        DBCPDataSourceConfig config=JSON.parseObject(dsConfig, DBCPDataSourceConfig.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure DBCP is null");
        }
        return config;		
	}
    public C3P0DataSourceConfig getC3P0DataSourceConfig() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_C3P0_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure C3P0 Config in UniConfig is blank");
        }
        C3P0DataSourceConfig config=JSON.parseObject(dsConfig, C3P0DataSourceConfig.class);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure C3P0 is null");
        }
        return config;		
	}
    
    /*public String getCacheType() {
        String type = prop.getProperty("paas.cache.type");
        if (StringUtil.isBlank(type)) {
            throw new PaasRuntimeException("cache type is null");
        }
        return type;
    }
    
    public String getSequenceType() {
        String type = prop.getProperty("paas.sequence.type");
        if (StringUtil.isBlank(type)) {
            throw new PaasRuntimeException("sequence type is null");
        }
        return type;
    }

    public String getDocStorageType() {
        String type = prop.getProperty("paas.doc.storage.type");
        if (StringUtil.isBlank(type)) {
            throw new PaasRuntimeException("doc storage type is null");
        }
        return type;
    }*/

    public String getPropValue(String key) {
        if (StringUtil.isBlank(key)) {
            throw new PaasRuntimeException("cannt get value because key is null");
        }
        return prop.containsKey(key) ? prop.getProperty(key) : null;
    }

	

	

}
