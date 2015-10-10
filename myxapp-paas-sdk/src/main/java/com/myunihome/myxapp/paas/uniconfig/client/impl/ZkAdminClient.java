package com.myunihome.myxapp.paas.uniconfig.client.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.uniconfig.client.IZkAdminClient;
import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ConfigWatcher;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKAddMode;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPool;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;
import com.myunihome.myxapp.paas.util.StringUtil;

public class ZkAdminClient implements IZkAdminClient {

    private static final Logger LOG = LogManager.getLogger(ZkAdminClient.class);

    private String authInfo;

    private String zkUser;

    private ZKPool zkPool;

    private String zkAddr;

    private String zkAuthSchema;

    public ZkAdminClient(String zkAddr,String zkAuthSchema, String zkUser, String zkPassword,int timeout) {
        try {
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

    private void modify(String configPath, byte[] value) throws UniConfigException {
        if (!exists(configPath)) {
            throw new UniConfigException("节点["+configPath+"]不存在");
        }
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.setNodeData(configPath, value);
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+configPath+"]权限");
            }
            throw new UniConfigException("修改节点["+configPath+"]失败", e);
        }
    }

    @Override
    public boolean exists(String path) {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.exists(path);
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new RuntimeException("无访问节点["+path+"]权限");
            }
            throw new UniConfigException("节点["+path+"]不存在", e);
        }
    }

    public String get(String configPath, ConfigWatcher watcher) throws UniConfigException {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.getNodeData(configPath, watcher);
        } catch (Exception e) {
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+configPath+"]权限");
            }
            throw new UniConfigException("节点["+configPath+"]不存在",e);
        }
    }

    private ZKClient getZkClientFromPool() throws Exception {
        ZKClient zkClient = this.zkPool.getZkClient(this.zkAddr, this.zkUser);

        if (zkClient == null) {
            throw new UniConfigException("获取zkClient失败");
        }
        return zkClient;
    }

    private void add(String configPath, byte[] bytes, ZKAddMode mode) throws UniConfigException {
        if (exists(configPath)) {
            throw new UniConfigException("节点["+configPath+"]已存在，不能重复添加");
        }

        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.createNode(configPath, createWritableACL(), bytes,
                    ZKAddMode.convertMode(mode.getFlag()));
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new UniConfigException("无访问节点["+configPath+"]权限");
            }
            throw new UniConfigException("添加节点["+configPath+"]失败", e);
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
