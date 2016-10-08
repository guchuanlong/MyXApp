package com.myunihome.myxapp.paas.lock;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.exception.PaasException;
import com.myunihome.myxapp.paas.lock.zklock.ZKMutexLock;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;

public class ZKMutexLockFactory {

	public static AbstractMutexLock getZKMutexLock(String zkAddr,int zkTimeoutMillSecs,String zkLockNodePath) throws PaasException{
		ZKClient zkClient = null;
		try {
			zkClient = ZKPoolFactory.getZKPool(zkAddr,zkTimeoutMillSecs).getZkClient(zkAddr);
			return new ZKMutexLock(zkClient.getInterProcessLock(zkLockNodePath));
		} catch (Exception e) {
			throw new PaasException("获取分布式锁失败", e);
		}		
	}
	public static AbstractMutexLock getZKMutexLock(String zkAddr,String zkLockNodePath) throws PaasException{
		//zk连接超时时间默认为60000毫秒，即1分钟。
		return getZKMutexLock(zkAddr,60000,zkLockNodePath);		
	}
	
	public static AbstractMutexLock getZKMutexLock(String zkLockNodePath) throws PaasException{
		UniConfigZkInfo uniConfZkInfo=MyXAppConfHelper.getInstance().getUniConfigZkConf();
		return getZKMutexLock(uniConfZkInfo.getZkAddress(),zkLockNodePath);		
	}	
	
}
