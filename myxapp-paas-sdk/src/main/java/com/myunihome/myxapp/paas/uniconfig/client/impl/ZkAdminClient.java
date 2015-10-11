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
import com.myunihome.myxapp.paas.uniconfig.zookeeper.MutexLock;
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
	public String get(String path) throws UniConfigException {
		try {
			if (!exists(path)) {
				throw new UniConfigException("节点[" + path + "]不存在");
			}
			return get(path, null);
		} catch (Exception e) {
			LOG.error(e);
			throw new UniConfigException("获取节点数据失败", e);
		}
	}

	@Override
	public void add(String path, String config) throws UniConfigException {
		byte[] data = null;
		if (!StringUtil.isBlank(config)) {
			try {
				data = config.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
				add(path, data, ZKAddMode.PERSISTENT);
			} catch (UnsupportedEncodingException | UniConfigException e) {
				LOG.error("添加节点[" + path + "]数据["+config+"]失败",e);
				throw new UniConfigException("添加节点[" + path + "]数据["+config+"]失败", e);
			}
		}

	}

	@Override
	public void modify(String path, String config) throws UniConfigException {
		try {
			if (!exists(path)) {
				throw new UniConfigException("节点[" + path + "]不存在");
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
			throw new UniConfigException("修改节点[" + path + "]配置失败", e);
		}
	}

	@Override
	public void modify(String path, byte[] value) throws UniConfigException {
		if (!exists(path)) {
			throw new UniConfigException("节点[" + path + "]不存在");
		}
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			client.setNodeData(path, value);
		} catch (Exception e) {
			LOG.error(e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限");
			}
			throw new UniConfigException("修改节点[" + path + "]失败", e);
		}
	}

	@Override
	public boolean exists(String path) throws UniConfigException {
		return exists(path, null);
	}

	@Override
	public boolean exists(String path, ConfigWatcher configWatcher) throws UniConfigException {
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			if (null != configWatcher) {
				return client.exists(path, configWatcher);
			}
			return client.exists(path);
		} catch (Exception e) {
			LOG.error(e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new RuntimeException("无访问节点[" + path + "]权限");
			}
			throw new UniConfigException("节点[" + path + "]不存在", e);
		}
	}

	@Override
	public String get(String path, ConfigWatcher watcher) throws UniConfigException {
		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			return client.getNodeData(path, watcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限");
			}
			throw new UniConfigException("节点[" + path + "]不存在", e);
		}
	}

	private ZKClient getZkClientFromPool() throws Exception {
		ZKClient zkClient = this.zkPool.getZkClient(this.zkAddr, this.zkUser);

		if (zkClient == null) {
			throw new UniConfigException("获取zkClient失败");
		}
		return zkClient;
	}

	@Override
	public void add(String path, byte[] bytes, ZKAddMode mode) throws UniConfigException {
		if (exists(path)) {
			throw new UniConfigException("节点[" + path + "]已存在，不能重复添加");
		}

		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			client.createNode(path, createWritableACL(), bytes,
					ZKAddMode.convertMode(mode.getFlag()));
		} catch (Exception e) {
			LOG.error(e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限");
			}
			throw new UniConfigException("添加节点[" + path + "]失败", e);
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

	@Override
	public MutexLock getMutexLock(String path) throws UniConfigException {
		if (!validatePath(path))
			throw new UniConfigException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");
		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			return new MutexLock(zkClient.getInterProcessLock(path));
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("授权失败", e);
			}
			throw new UniConfigException("获取分布式锁失败", e);
		}
	}

	@Override
	public byte[] readBytes(String path) throws UniConfigException {
		return readBytes(path, null);
	}

	@Override
	public byte[] readBytes(String path, ConfigWatcher configWatcher) throws UniConfigException {
		if (!exists(path)) {
			throw new UniConfigException("节点[" + path + "]不存在");
		}

		ZKClient client = null;
		try {
			client = getZkClientFromPool();
			return client.getNodeBytes(path, configWatcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限",e);
			}
			throw new UniConfigException("读取节点[" + path + "]数据失败", e);
		}
	}

	@Override
	public void add(String path, String config, ZKAddMode zkAddMode) throws UniConfigException {
		byte[] bytes = null;
		if (!StringUtil.isBlank(config)) {
			try {
				bytes = config.getBytes(MyXAppPaaSConstant.CHARSET_UTF8);
			} catch (UnsupportedEncodingException e) {
				throw new UniConfigException("字符串转byte数组错误", e);
			}
		}

		add(path, bytes, zkAddMode);

	}

	@Override
	public void add(String path, byte[] bytes) throws UniConfigException {
		add(path, bytes, ZKAddMode.PERSISTENT);
	}

	@Override
	public List<String> listSubPath(String path) throws UniConfigException {
		return listSubPath(path, null);
	}

	@Override
	public List<String> listSubPath(String path, ConfigWatcher configWatcher) throws UniConfigException {

		if (!validatePath(path)) {
			throw new UniConfigException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");
		}
		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			return zkClient.getChildren(path, configWatcher);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限", e);
			}
			throw new UniConfigException("列出path[" + path + "]的子结点失败", e);
		}
	}

	@Override
	public void remove(String path) throws UniConfigException {
		if (!validatePath(path))
			throw new UniConfigException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");

		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			zkClient.deleteNode(path);
		} catch (Exception e) {
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限", e);
			}
			throw new UniConfigException("删除节点[" + path + "]失败", e);
		}

	}

	/**
	 * 验证路径是否合法
	 * 
	 * @param path
	 * @return
	 * @author gucl
	 */
	public boolean validatePath(String path) {
		if ((!path.startsWith("/")) && (path.endsWith("/"))) {
			return false;
		}
		return true;
	}


}
