package com.myunihome.myxapp.paas.lock;

import com.myunihome.myxapp.paas.cache.CacheFactory;
import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.exception.PaasException;
import com.myunihome.myxapp.paas.lock.redislock.CacheInterProcessLock;
import com.myunihome.myxapp.paas.lock.redislock.RedisMutexLock;
import com.myunihome.myxapp.paas.util.StringUtil;

public class RedisMutexLockFactory {
	
	public static AbstractMutexLock getRedisMutexLock(String namespace,String redisKey) throws PaasException{
		if(StringUtil.isBlank(namespace)){
        	namespace=MyXAppPaaSConstant.DEFAULT;
        }
		try {
			ICacheClient cacheClient=CacheFactory.getCacheClient(namespace);
			CacheInterProcessLock cacheInterProcessLock=new CacheInterProcessLock(cacheClient);
			return new RedisMutexLock(redisKey, cacheInterProcessLock);
		} catch (Exception e) {
			throw new PaasException("获取分布式锁失败", e);
		}
	}
	public static AbstractMutexLock getRedisMutexLock(String redisKey) throws PaasException{
		return getRedisMutexLock(MyXAppPaaSConstant.DEFAULT,redisKey);
	}
}
