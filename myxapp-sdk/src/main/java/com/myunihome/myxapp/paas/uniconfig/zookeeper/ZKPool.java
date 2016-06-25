package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import java.util.concurrent.ConcurrentHashMap;

import com.myunihome.myxapp.paas.util.StringUtil;
/**
 * zookeeper客户端连接池
 *
 * Date: 2015年10月1日 <br>
 * Copyright (c) 2015 guchuanlong@126.com <br>
 * @author gucl
 */
public class ZKPool
{
  private static ConcurrentHashMap<String, ZKClient> clients = new ConcurrentHashMap<String, ZKClient>();

  public ZKClient getZkClient(String zkAddr, String zkUserName) throws Exception {
    return clients.get(appendKey(zkAddr, zkUserName));
  }

  private String appendKey(String zkAddr, String zkUserName) {
	  StringBuilder key=new StringBuilder(zkAddr);
	  if(!StringUtil.isBlank(zkUserName)){
		  key.append("-");
		  key.append(zkUserName);
	  }
    return key.toString();
  }

  public void addZKClient(String zkAddr, String zkUserName, ZKClient zkClient) {
    clients.put(appendKey(zkAddr, zkUserName), zkClient);
  }

  public boolean exist(String zkAddr, String zkUserName) {
    return clients.containsKey(appendKey(zkAddr, zkUserName));
  }  
  
}