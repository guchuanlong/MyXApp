package com.myunihome.myxapp.paas;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.myunihome.myxapp.paas.constants.MyXAppConfConstants;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.exception.PaasRuntimeException;
import com.myunihome.myxapp.paas.model.C3P0DataSourceConfig;
import com.myunihome.myxapp.paas.model.CacheConfigInfo;
import com.myunihome.myxapp.paas.model.DBCPDataSourceConfig;
import com.myunihome.myxapp.paas.model.DocStoreConfigInfo;
import com.myunihome.myxapp.paas.model.DruidDataSourceConfig;
import com.myunihome.myxapp.paas.model.ESConfigInfo;
import com.myunihome.myxapp.paas.model.HikariCPDataSourceConfig;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.util.CollectionUtil;
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
        	synchronized(MyXAppConfHelper.class){
        		if (instance == null) {
        			loadProp();
        			instance = new MyXAppConfHelper();
        		}
        		
        	}
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
    /*public String getAppDomain() {
    	String appDomain = prop.getProperty("paas.uniconfig.appDomain");
    	if (StringUtil.isBlank(appDomain)) {
    		throw new PaasRuntimeException("uniconfig appDomain is null");
    	}
    	return appDomain;
    }*/
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
    
    /*public String getAppDomainAndAppId(){
    	String appDomain = prop.getProperty("paas.uniconfig.appDomain");
    	if (StringUtil.isBlank(appDomain)) {
    		throw new PaasRuntimeException("uniconfig appDomain is null");
    	}
    	String appId = prop.getProperty("paas.uniconfig.appId");
    	if (StringUtil.isBlank(appId)) {
    		throw new PaasRuntimeException("uniconfig appId is null");
    	}
    	return appDomain+"$"+appId;
    }*/
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
        //默认超时时间2秒
        int timeout=2000;
        if (StringUtil.isBlank(zkAddress)) {
            throw new PaasRuntimeException("paas uniconfig zkAddress is null");
        }
        /*if (StringUtil.isBlank(zkAuthSchema)) {
            throw new PaasRuntimeException("paas uniconfig zkAuthSchema is null");
        }
        if (StringUtil.isBlank(zkUser)) {
            throw new PaasRuntimeException("paas uniconfig zkUser is null");
        }
        if (StringUtil.isBlank(zkPasswd)) {
            throw new PaasRuntimeException("paas uniconfig zkPasswd is null");
        }*/
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
        return getCacheConfig(MyXAppPaaSConstant.DEFAULT);
	}
    public CacheConfigInfo getCacheConfig(String namespace) {
    	Map<String,CacheConfigInfo> configMap=getCacheConfigMap();
    	CacheConfigInfo config=configMap.get(namespace);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because cacheConfig is null");
        }
        return config;
		
	}
    public List<String> getCacheConfigMapNameSpaces(){
    	Map<String,CacheConfigInfo> configMap=getCacheConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> namespaces=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){
    		namespaces.addAll(keySet);
    	}
    	return namespaces;
    }
    public Map<String,CacheConfigInfo> getCacheConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String cacheConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_CACHE_CONFIG_PATH);
        if (StringUtil.isBlank(cacheConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because cacheConfig in UniConfig is blank");
        }
        Map<String,CacheConfigInfo> configMap=JSON.parseObject(cacheConfig, new TypeReference<Map<String,CacheConfigInfo>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because cacheConfig is null");
        }
        return configMap;
		
	}
    /**
     * 文档存储配置
     * @return
     * @author gucl
     */
    public DocStoreConfigInfo getDocStoreConfig() {
        return getDocStoreConfig(MyXAppPaaSConstant.DEFAULT);
	}
    public DocStoreConfigInfo getDocStoreConfig(String namespace) {
    	Map<String,DocStoreConfigInfo> configMap=getDocStoreConfigMap();
        DocStoreConfigInfo config=configMap.get(namespace);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas docstore configInfo because dssConfig is null");
        }
        return config;
		
	}
    public List<String> getDocStoreConfigMapNameSpaces(){
    	Map<String,DocStoreConfigInfo> configMap=getDocStoreConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> namespaces=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){
    		namespaces.addAll(keySet);
    	}
    	return namespaces;
    }
    public Map<String,DocStoreConfigInfo> getDocStoreConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dssConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DOCSTORE_CONFIG_PATH);
        if (StringUtil.isBlank(dssConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because dssConfig in UniConfig is blank");
        }
        Map<String,DocStoreConfigInfo> configMap=JSON.parseObject(dssConfig, new TypeReference<Map<String,DocStoreConfigInfo>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because dssConfigMap is null");
        }
        return configMap;
		
	}
    
    public HikariCPDataSourceConfig getHikariCPDataSourceConfig(){
    	return getHikariCPDataSourceConfig(MyXAppPaaSConstant.DEFAULT);
    }
    public DruidDataSourceConfig getDruidDataSourceConfig(){
    	return getDruidDataSourceConfig(MyXAppPaaSConstant.DEFAULT);
    }
    public DBCPDataSourceConfig getDBCPDataSourceConfig(){
    	return getDBCPDataSourceConfig(MyXAppPaaSConstant.DEFAULT);
    }
    public C3P0DataSourceConfig getC3P0DataSourceConfig(){
    	return getC3P0DataSourceConfig(MyXAppPaaSConstant.DEFAULT);
    }
    public ESConfigInfo getSearchConfig(){
    	return getSearchConfig(MyXAppPaaSConstant.DEFAULT);
    }
    public HikariCPDataSourceConfig getHikariCPDataSourceConfig(String dataSourceName){
    	Map<String,HikariCPDataSourceConfig> configMap=getHikariCPDataSourceConfigMap();
    	HikariCPDataSourceConfig config=configMap.get(dataSourceName);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas datasource configInfo because config is null");
        }
        return config;
    }
    public DruidDataSourceConfig getDruidDataSourceConfig(String dataSourceName){
    	Map<String,DruidDataSourceConfig> configMap=getDruidDataSourceConfigMap();
    	DruidDataSourceConfig config=configMap.get(dataSourceName);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas datasource configInfo because config is null");
        }
        return config;
    }
    public DBCPDataSourceConfig getDBCPDataSourceConfig(String dataSourceName){
    	Map<String,DBCPDataSourceConfig> configMap=getDBCPDataSourceConfigMap();
    	DBCPDataSourceConfig config=configMap.get(dataSourceName);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas datasource configInfo because config is null");
        }
        return config;
    }
    public C3P0DataSourceConfig getC3P0DataSourceConfig(String dataSourceName){
    	Map<String,C3P0DataSourceConfig> configMap=getC3P0DataSourceConfigMap();
    	C3P0DataSourceConfig config=configMap.get(dataSourceName);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas datasource configInfo because config is null");
        }
        return config;
    }
    public ESConfigInfo getSearchConfig(String namespace){
    	Map<String,String> configMap=getSearchConfigMap();
    	String config=configMap.get(namespace);
        if (config==null) {
            throw new PaasRuntimeException("cann't get paas search configInfo because config is null");
        }
        ESConfigInfo esconf=JSON.parseObject(config, ESConfigInfo.class);
        
        return esconf;
    }

    public List<String> getHikariCPDataSourceNames(){
    	Map<String,HikariCPDataSourceConfig> configMap=getHikariCPDataSourceConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> dataSourceNames=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){    		
    		dataSourceNames.addAll(keySet);
    	}
    	return dataSourceNames;
    }
    public List<String> getDruidDataSourceNames(){
    	Map<String,DruidDataSourceConfig> configMap=getDruidDataSourceConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> dataSourceNames=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){    		
    		dataSourceNames.addAll(keySet);
    	}
    	return dataSourceNames;
    }
    public List<String> getDBCPDataSourceNames(){
    	Map<String,DBCPDataSourceConfig> configMap=getDBCPDataSourceConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> dataSourceNames=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){    		
    		dataSourceNames.addAll(keySet);
    	}
    	return dataSourceNames;
    }
    public List<String> getC3P0DataSourceNames(){
    	Map<String,C3P0DataSourceConfig> configMap=getC3P0DataSourceConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> dataSourceNames=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){    		
    		dataSourceNames.addAll(keySet);
    	}
    	return dataSourceNames;
    }
    
    public List<String> getSearchNameSpaces(){
    	Map<String,String> configMap=getSearchConfigMap();
    	Set<String> keySet=configMap.keySet();
    	List<String> searchNameSpaces=new ArrayList<String>();
    	if(!CollectionUtil.isEmpty(keySet)){    		
    		searchNameSpaces.addAll(keySet);
    	}
    	return searchNameSpaces;
    }
        
    public Map<String,HikariCPDataSourceConfig> getHikariCPDataSourceConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_HIKARICP_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure HikariCP Config in UniConfig is blank");
        }
        Map<String,HikariCPDataSourceConfig> configMap=JSON.parseObject(dsConfig, new TypeReference<Map<String,HikariCPDataSourceConfig>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure HikariCP is null");
        }
        return configMap;		
	}
    public Map<String,DruidDataSourceConfig> getDruidDataSourceConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_DRUID_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure Druid Config in UniConfig is blank");
        }
        Map<String,DruidDataSourceConfig> configMap=JSON.parseObject(dsConfig, new TypeReference<Map<String,DruidDataSourceConfig>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure Druid is null");
        }
        return configMap;		
	}
    public Map<String,DBCPDataSourceConfig> getDBCPDataSourceConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_DBCP_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure DBCP Config in UniConfig is blank");
        }
        Map<String,DBCPDataSourceConfig> configMap=JSON.parseObject(dsConfig, new TypeReference<Map<String,DBCPDataSourceConfig>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure DBCP is null");
        }
        return configMap;		
	}
    public Map<String,C3P0DataSourceConfig> getC3P0DataSourceConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because uniConfigClient is null");
        }
        String dsConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_DATASOURCE_C3P0_CONFIG_PATH);
        if (StringUtil.isBlank(dsConfig)) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure C3P0 Config in UniConfig is blank");
        }
        Map<String,C3P0DataSourceConfig> configMap=JSON.parseObject(dsConfig, new TypeReference<Map<String,C3P0DataSourceConfig>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas cache configInfo because datasoure C3P0 is null");
        }
        return configMap;		
	}
    public Map<String,String> getSearchConfigMap() {
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas search configInfo because uniConfigClient is null");
        }
        String esConfig=uniConfigClient.get(MyXAppConfConstants.PAAS_SEARCH_CONFIG_PATH);
        if (StringUtil.isBlank(esConfig)) {
            throw new PaasRuntimeException("cann't get paas search configInfo because search Config in UniConfig is blank");
        }
        Map<String,String> configMap=JSON.parseObject(esConfig, new TypeReference<Map<String,String>>(){});
        if (configMap==null) {
            throw new PaasRuntimeException("cann't get paas search configInfo because searchconfigMap is null");
        }
        return configMap;		
	}
    public JSONObject getDubboProviderConf(){
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
        if (uniConfigClient == null) {
            throw new PaasRuntimeException("cann't get paas dubbo provider configInfo because uniConfigClient is null");
        }
        String strDubboProviderConf=uniConfigClient.get(MyXAppConfConstants.DUBBO_PROVIDER_CONF_PATH);
        if (StringUtil.isBlank(strDubboProviderConf)) {
            throw new PaasRuntimeException("cann't get paas dubbo provider configInfo because dubbo provider Config in UniConfig is blank");
        }
        JSONObject jsonConf=JSON.parseObject(strDubboProviderConf, JSONObject.class);
        if (jsonConf==null) {
            throw new PaasRuntimeException("cann't get paas dubbo provider configInfo because configInfo is null");
        }
    	return jsonConf;
    }
    public JSONObject getDubboConsumerConf(){
    	IUniConfigClient uniConfigClient = UniConfigFactory.getUniConfigClient();
    	if (uniConfigClient == null) {
    		throw new PaasRuntimeException("cann't get paas dubbo consumer configInfo because uniConfigClient is null");
    	}
    	String strDubboConsumerConf=uniConfigClient.get(MyXAppConfConstants.DUBBO_CONSUMER_CONF_PATH);
    	if (StringUtil.isBlank(strDubboConsumerConf)) {
    		throw new PaasRuntimeException("cann't get paas dubbo consumer configInfo because dubbo consumer Config in UniConfig is blank");
    	}
    	JSONObject jsonConf=JSON.parseObject(strDubboConsumerConf, JSONObject.class);
    	if (jsonConf==null) {
    		throw new PaasRuntimeException("cann't get paas dubbo consumer configInfo because configInfo is null");
    	}
    	return jsonConf;
    }

    public String getPropValue(String key) {
        if (StringUtil.isBlank(key)) {
            throw new PaasRuntimeException("cannt get value because key is null");
        }
        return prop.containsKey(key) ? prop.getProperty(key) : null;
    }

	

	

}
