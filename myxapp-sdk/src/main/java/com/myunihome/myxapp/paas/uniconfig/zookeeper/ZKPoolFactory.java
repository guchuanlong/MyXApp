package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.util.Assert;
import com.myunihome.myxapp.paas.util.StringUtil;
/**
 * ZKPool工厂
 *
 * Date: 2015年10月1日 <br>
 * Copyright (c) 2015 guchuanlong@126.com <br>
 * @author gucl
 */
public class ZKPoolFactory
{
  private static final ZKPool zkPool = new ZKPool();
  public static ZKPool getZKPool(String zkAddress) throws UniConfigException {
	    return getZKPool(zkAddress, null, null, 2000);
  }
  public static ZKPool getZKPool(String zkAddress,int timeOut) throws UniConfigException {
	  return getZKPool(zkAddress, null, null, timeOut);
  }
  public static ZKPool getZKPool(String zkAddress, String zkUser, String zkPasswd) throws UniConfigException {
	    return getZKPool(zkAddress, zkUser, zkPasswd, 2000);
  }
  public static ZKPool getZKPool(String zkAddress, int timeOut, String[] authInfo) throws UniConfigException {
    String zkUser = null;
    String zkPasswd = null;
    if ((null != authInfo) && (authInfo.length >= 2)) {
      if (!StringUtil.isBlank(authInfo[0])) {
        zkUser = authInfo[0];
      }
      if (!StringUtil.isBlank(authInfo[1])) {
        zkPasswd = authInfo[1];
      }
    }
    return getZKPool(zkAddress, zkUser, zkPasswd, 2000);
  }
  public static ZKPool getZKPool(String zkAddress, String zkUser, String zkPasswd, int timeOut) throws UniConfigException {
    validateParam(zkAddress);
    if (zkPool.exist(zkAddress, zkUser)) {
      return zkPool;
    }
    ZKClient client = null;
    try {
      if(!StringUtil.isBlank(zkUser)&&!StringUtil.isBlank(zkPasswd)){
    	  client = new ZKClient(zkAddress, timeOut, new String[] { "digest", getAuthInfo(zkUser, zkPasswd) });
    	  client.addAuth("digest", getAuthInfo(zkUser, zkPasswd));
      }
      else{
    	  client = new ZKClient(zkAddress, timeOut, new String[0]);
      }
      
    } catch (Exception e) {
      throw new UniConfigException(e.getMessage());
    }
    zkPool.addZKClient(zkAddress, zkUser, client);
    return zkPool;
  }

  private static void validateParam(String zkAddress, String zkUser, String zkPasswd) {
    Assert.notNull(zkAddress, "zookeeper地址为空");
    Assert.notNull(zkUser, "zookeeper用户名为空");
    Assert.notNull(zkPasswd, "zookeeper密码为空");
  }
  private static void validateParam(String zkAddress) {
	  Assert.notNull(zkAddress, "zookeeper地址为空");
  }

  private static String getAuthInfo(String zkUser, String zkPasswd) {
    return zkUser + ":" + zkPasswd;
  }

}