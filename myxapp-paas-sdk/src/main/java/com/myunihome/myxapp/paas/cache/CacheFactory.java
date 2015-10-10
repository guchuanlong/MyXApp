package com.myunihome.myxapp.paas.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.cache.client.impl.CacheClient;
import com.myunihome.myxapp.paas.cache.client.impl.CacheClusterClient;
import com.myunihome.myxapp.paas.model.CacheConfigInfo;

/**
 * 缓存工厂
 *
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public final class CacheFactory {
    
    private CacheFactory(){
        
    }
    private static final Logger LOG = LogManager.getLogger(CacheFactory.class);
    private static Map<String, ICacheClient> cacheClients = new ConcurrentHashMap<String, ICacheClient>();
    
    public static ICacheClient getCacheClient(){
        ICacheClient cacheClient = null;
        
        String appDomain=MyXAppConfHelper.getInstance().getAppDomain();
        String appId=MyXAppConfHelper.getInstance().getAppId();
        String cacheClientId=appDomain+"-"+appId;
        //获取配置信息
        LOG.info("Get CacheConfigInfo ...");
        CacheConfigInfo cacheConfigInfo=MyXAppConfHelper.getInstance().getCacheConfig();
        
        if (cacheClients.containsKey(cacheClientId)) {
          cacheClient = (ICacheClient)cacheClients.get(cacheClientId);
          return cacheClient;
        }
        LOG.info("Get hostAndPort ...");
        String[] hostAndPortArray = cacheConfigInfo.getJedisHostAndPorts().split(",");
        LOG.info("Get RedisClient ...");
        if (hostAndPortArray.length > 1) {
          cacheClient = new CacheClusterClient(cacheConfigInfo.getJedisPoolConfig(), hostAndPortArray);
        } else {
          cacheClient = new CacheClient(cacheConfigInfo.getJedisPoolConfig(), cacheConfigInfo.getJedisHostAndPorts());
        }
        LOG.info("Get RedisClient ...");
        cacheClients.put(cacheClientId, cacheClient);
        return cacheClient;
      }
}
