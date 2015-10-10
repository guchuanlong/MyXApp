package com.myunihome.myxapp.paas.uniconfig.client.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ConfigWatcher;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKAddMode;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPool;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;
import com.myunihome.myxapp.paas.util.StringUtil;

public class UniConfigClient implements IUniConfigClient {

    private static final Logger LOG = LogManager.getLogger(UniConfigClient.class);
    //应用程序域
    private String appDomain;
    //应用程序ID
    private String appId;
    //Zookeeper授权信息
    private String authInfo;
    //Zookeeper用户
    private String zkUser;
    //Zookeeper客户端连接池
    private ZKPool zkPool;
    //Zookeeper地址
    private String zkAddr;
    //Zookeeper授权方式
    private String zkAuthSchema;

    public UniConfigClient(String appDomain,String appId,String zkAddr,String zkAuthSchema, String zkUser, String zkPassword,int timeout) {
        try {
        	this.appDomain=appDomain;
        	this.appId=appId;
            this.authInfo = (zkUser + ":" + zkPassword);
            this.zkUser = zkUser;
            this.zkAddr = zkAddr;
            this.zkAuthSchema = zkAuthSchema;
            this.zkPool = ZKPoolFactory.getZKPool(zkAddr, zkUser, zkPassword, timeout);
        } catch (Exception e) {
            LOG.error(e);
            throw new UniConfigException(e.getMessage(), e);
        }
    }
    private String appendDomainAndId(String path){
    	StringBuilder sb=new StringBuilder();
    	sb.append(MyXAppPaaSConstant.UNIX_SEPERATOR+this.appDomain);
    	sb.append(MyXAppPaaSConstant.UNIX_SEPERATOR+this.appId);
    	sb.append(path);    	
    	LOG.debug("Real path="+sb.toString());
    	return sb.toString();
    }
    @Override
    public String get(String path) {
        try {
            if (!exists(path)) {
                throw new Exception("节点[" + path + "]不存在");
            }
            return get(path, null);
        } catch (Exception e) {
            LOG.error(e);
            throw new UniConfigException("获取节点数据失败", e);
        }
    }

    @Override
    public void add(String path, String config) {
        byte[] data = null;
        if (!StringUtil.isBlank(config)) {
            try {
                data = config.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
                add(path, data, ZKAddMode.PERSISTENT);
            } catch (UnsupportedEncodingException | UniConfigException e) {
                LOG.error(e);
                throw new UniConfigException("字符串转换字节异常", e);
            }
        }

    }

    @Override
    public void modify(String path, String config) {
        try {
            if (!exists(path)) {
                throw new Exception("节点[" + path + "]不存在");
            }
            byte[] data = null;
            if (!StringUtil.isBlank(config)) {
                try {
                    data = config.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
                } catch (UnsupportedEncodingException e) {
                    throw new UniConfigException(e.getMessage(), e);
                }
                modify(path, data);
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new UniConfigException("修改节点["+path+"]配置失败", e);
        }
    }

    private void modify(String path, byte[] value) throws UniConfigException {
        if (!exists(path)) {
            throw new UniConfigException("节点["+path+"]不存在");
        }
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.setNodeData(appendDomainAndId(path), value);
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+path+"]权限");
            }
            throw new UniConfigException("修改节点["+path+"]失败", e);
        }
    }

    @Override
    public boolean exists(String path) {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.exists(appendDomainAndId(path));
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new RuntimeException("无访问节点["+path+"]权限");
            }
            throw new UniConfigException("节点["+path+"]不存在", e);
        }
    }

    @Override
    public String get(String path, ConfigWatcher watcher) throws UniConfigException {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.getNodeData(appendDomainAndId(path), watcher);
        } catch (Exception e) {
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+path+"]权限");
            }
            throw new UniConfigException("节点["+path+"]不存在",e);
        }
    }

    private ZKClient getZkClientFromPool() throws Exception {
        ZKClient zkClient = this.zkPool.getZkClient(this.zkAddr, this.zkUser);

        if (zkClient == null) {
            throw new UniConfigException("获取zkClient失败");
        }
        return zkClient;
    }

    private void add(String path, byte[] bytes, ZKAddMode mode) throws UniConfigException {
        if (exists(path)) {
            throw new UniConfigException("节点["+path+"]已存在，不能重复添加");
        }

        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.createNode(appendDomainAndId(path), createWritableACL(), bytes,
                    ZKAddMode.convertMode(mode.getFlag()));
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+path+"]权限");
            }
            throw new UniConfigException("添加节点["+path+"]失败", e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<ACL> createWritableACL() throws NoSuchAlgorithmException {
        List<ACL> acls = new ArrayList();
        Id id1 = new Id(this.zkAuthSchema, DigestAuthenticationProvider.generateDigest(this.authInfo));
        ACL userACL = new ACL(31, id1);
        acls.add(userACL);
        return acls;
    }

}
