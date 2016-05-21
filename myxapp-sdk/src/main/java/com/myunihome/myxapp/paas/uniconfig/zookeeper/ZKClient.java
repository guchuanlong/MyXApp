 package com.myunihome.myxapp.paas.uniconfig.zookeeper;
 
 import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;

import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.util.Assert;
import com.myunihome.myxapp.paas.util.StringUtil;
 /**
  * Zookeeper客户端
  *
  * Date: 2015年10月1日 <br>
  * Copyright (c) 2015 guchuanlong@126.com <br>
  * @author gucl
  */
 public class ZKClient
 {
   private static final String DIGEST_SCHEMA = "digest"; //通过用户名和密码进行权限认证
   private CuratorFramework client = null; //zookeeper客户端
   private String zkAddr = null; //注册地址
   private int timeOut = 20000; //连接超时时间
   private String authSchema = null; //权限认证方法
   private String authInfo = null;//授权信息
   private ZKPool pool = null;//zookeeper客户端连接池
   
   /**
    * 构造zookeeper客户端
    * @param zkAddr
    * @param timeOut
    * @param auth
    * @throws Exception
    */
   public ZKClient(String zkAddr, int timeOut, String... auth) throws Exception
   {
     Assert.notNull(zkAddr, "zkAddress should not be Null");
     
     this.zkAddr = zkAddr;
     if (timeOut > 0) {
       this.timeOut = timeOut;
     }
     if ((null != auth) && (auth.length >= 2)) {
       if (!StringUtil.isBlank(auth[0])) {
         this.authSchema = auth[0];
       }
       if (!StringUtil.isBlank(auth[1])) {
         this.authInfo = auth[1];
       }
     }
     //构建zookeeper客户端
     CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
    		 .connectString(this.zkAddr)
    		 .connectionTimeoutMs(this.timeOut)
    		 .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 10));
 
     if ((!StringUtil.isBlank(this.authSchema)) && (!StringUtil.isBlank(this.authInfo))) {
       builder.authorization(this.authSchema, this.authInfo.getBytes());
     }
     
     this.client = builder.build();
     this.client.start();
     this.client.blockUntilConnected();
   }
   /**
    * 读取节点数据
    * @param nodePath 节点path
    * @param watch 是否监视
    * @return 节点存储的值
    * @throws Exception
    * @author gucl
    */
   public String getNodeData(String nodePath, boolean watch) throws Exception { 
     byte[] data;
     if (watch) {
       data = (byte[])(this.client.getData().watched()).forPath(nodePath);
     } else {
       data = (byte[])this.client.getData().forPath(nodePath);
     }
     if ((null == data) || (data.length <= 0))
       return null;
     return new String(data, MyXAppPaaSConstant.CHARSET_UTF8);
   }
   
   /**
    * 读取节点数据
    * @param nodePath
    * @return
    * @throws Exception
    * @author gucl
    */
   public String getNodeData(String nodePath) throws Exception {
     return getNodeData(nodePath, false);
   }
   
   /**
    * 读取节点数据
    * @param nodePath
    * @param watcher
    * @return
    * @throws Exception
    * @author gucl
    */
   public String getNodeData(String nodePath, Watcher watcher) throws Exception {
     byte[] data = getNodeBytes(nodePath, watcher);
     return new String(data, MyXAppPaaSConstant.CHARSET_UTF8);
   }
   
   /**
    * 读取节点数据(字节流)
    * @param nodePath
    * @param watcher
    * @return
    * @throws Exception
    * @author gucl
    */
   public byte[] getNodeBytes(String nodePath, Watcher watcher) throws Exception {
     byte[] bytes = null;
     if (null != watcher) {
       bytes = (byte[])(this.client.getData().usingWatcher(watcher)).forPath(nodePath);
     }
     else {
       bytes = (byte[])this.client.getData().forPath(nodePath);
     }
     return bytes;
   }
   
   public byte[] getNodeBytes(String nodePath) throws Exception {
     return getNodeBytes(nodePath, null);
   }
   /**
    * 创建节点（无权限控制）
    * @param nodePath
    * @param data
    * @param createMode
    * @throws Exception
    * @author gucl
    */
   public void createNode(String nodePath, String data, CreateMode createMode) throws Exception {
     createNode(nodePath, ZooDefs.Ids.OPEN_ACL_UNSAFE, data, createMode);
   }
   /**
    * 创建节点（可控制权限）
    * @param nodePath
    * @param acls
    * @param data
    * @param createMode
    * @throws Exception
    * @author gucl
    */
   public void createNode(String nodePath, List<ACL> acls, String data, CreateMode createMode) throws Exception
   {
     byte[] bytes = null;
     if (!StringUtil.isBlank(data))
       bytes = data.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
       createNode(nodePath, acls, bytes, createMode);
   }
   
   /**
    * 创建节点
    * @param nodePath
    * @param acls
    * @param data
    * @param createMode
    * @throws Exception
    * @author gucl
    */
   public void createNode(String nodePath, List<ACL> acls, byte[] data, CreateMode createMode) throws Exception
   {
     this.client.create().creatingParentsIfNeeded().withMode(createMode).withACL(acls).forPath(nodePath, data);
   }
   
   /**
    * 创建节点
    * @param nodePath
    * @param data
    * @throws Exception
    * @author gucl
    */
   public void createNode(String nodePath, String data) throws Exception
   {
     createNode(nodePath, data, CreateMode.PERSISTENT);
   }
   
   /**
    * 设置节点值
    * @param nodePath
    * @param data
    * @throws Exception
    * @author gucl
    */
   public void setNodeData(String nodePath, String data) throws Exception {
     byte[] bytes = null;
     if (!StringUtil.isBlank(data))
       bytes = data.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
     setNodeData(nodePath, bytes);
   }
   /**
    * 设置节点值
    * @param nodePath
    * @param data
    * @throws Exception
    * @author gucl
    */
   public void setNodeData(String nodePath, byte[] data) throws Exception {
     this.client.setData().forPath(nodePath, data);
   }
 
   public void createSeqNode(String nodePath)
     throws Exception
   {
      this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(nodePath);
   }
   /**
    * 判断节点是否存在
    * @param path
    * @return
    * @throws Exception
    * @author gucl
    */
   public boolean exists(String path)
     throws Exception
   {
     return null != this.client.checkExists().forPath(path);
   }
   /**
    * 判断节点是否存在（回调）
    * @param path
    * @param watcher
    * @return
    * @throws Exception
    * @author gucl
    */
   public boolean exists(String path, Watcher watcher) throws Exception {
     if (null != watcher) {
       return null != this.client.checkExists().watched().forPath(path);
     }
     return null != this.client.checkExists().forPath(path);
   }
   /**
    * 判断当前client是否处于连接状态
    * @return
    * @author gucl
    */
   public boolean isConnected()
   {
     if ((null == this.client) || (!CuratorFrameworkState.STARTED.equals(this.client.getState())))
     {
       return false;
     }
     return true;
   }
   /**
    * 重试连接
    * 
    * @author gucl
    */
   public void retryConnection() {
     this.client.start();
   }
   /**
    * 列出子结点（回调）
    * @param nodePath
    * @param watcher
    * @return
    * @throws Exception
    * @author gucl
    */
   public List<String> getChildren(String nodePath, Watcher watcher) throws Exception
   {
     return this.client.getChildren().usingWatcher(watcher).forPath(nodePath);
   }
   /**
    * 列出子结点
    * @param path
    * @return
    * @throws Exception
    * @author gucl
    */
   public List<String> getChildren(String path) throws Exception {
     return this.client.getChildren().forPath(path);
   }
   /**
    * 列出子结点
    * @param path
    * @param watcher
    * @return
    * @throws Exception
    * @author gucl
    */
   public List<String> getChildren(String path, boolean watcher) throws Exception {
     if (watcher) {
       return this.client.getChildren().watched().forPath(path);
     }
     return this.client.getChildren().forPath(path);
   }
   /**
    * 删除节点
    * @param path
    * @throws Exception
    * @author gucl
    */
   public void deleteNode(String path) throws Exception
   {
     this.client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
   }
   /**
    * 关闭client
    * 
    * @author gucl
    */
   public void quit()
   {
     if ((null != this.client) && (CuratorFrameworkState.STARTED.equals(this.client.getState())))
     {
       this.client.close();
     }
   }
   public ZKPool getPool() {
     return this.pool;
   }
   
   public void setPool(ZKPool pool) {
     this.pool = pool;
   }
   
   /**
    * 添加授权信息
    * @param authSchema
    * @param authInfo
    * @return
    * @throws Exception
    * @author gucl
    */
   public ZKClient addAuth(String authSchema, String authInfo) throws Exception
   {
     this.client.getZookeeperClient().getZooKeeper().addAuthInfo(DIGEST_SCHEMA, authInfo.getBytes());
     
     return this;
   }
   /**
    * 争夺分布式锁
    * @param lockPath
    * @return
    * @author gucl
    */
   public InterProcessLock getInterProcessLock(String lockPath) {
     return new InterProcessMutex(this.client, lockPath);
   }
 }

