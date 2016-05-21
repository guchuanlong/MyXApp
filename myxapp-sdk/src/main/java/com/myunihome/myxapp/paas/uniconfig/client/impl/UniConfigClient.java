package com.myunihome.myxapp.paas.uniconfig.client.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ConfigWatcher;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.MutexLock;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKAddMode;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPool;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;
import com.myunihome.myxapp.paas.util.StringUtil;

public class UniConfigClient implements IUniConfigClient {

	private static final Logger LOG = LoggerFactory.getLogger(UniConfigClient.class);
	//无授权信息
	private static final String NO_AUTHINFO=":";
		
	// 应用程序域
	private String appDomain;
	// 应用程序ID
	private String appId;
	// Zookeeper授权信息
	private String authInfo;
	// Zookeeper用户
	private String zkUser="";
	// Zookeeper密码
	private String zkPassword="";
	// Zookeeper客户端连接池
	private ZKPool zkPool;
	// Zookeeper地址
	private String zkAddr;
	// Zookeeper授权方式
	private String zkAuthSchema="digest";

	
	//不带认证的客户端
	public UniConfigClient(String appDomain, String appId, String zkAddr) {
		try {
			this.appDomain = appDomain;
			this.appId = appId;
			this.authInfo = NO_AUTHINFO;
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, 2000,null);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new UniConfigException(e.getMessage(), e);
		}
	}
	//不带认证的客户端
	public UniConfigClient(String appDomain, String appId, String zkAddr, int timeout) {
		try {
			this.appDomain = appDomain;
			this.appId = appId;
			this.authInfo = NO_AUTHINFO;
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, timeout,null);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new UniConfigException(e.getMessage(), e);
		}
	}
	//带认证的客户端
	public UniConfigClient(String appDomain, String appId, String zkAddr, String zkUser,
			String zkPassword, int timeout) {
		try {
			this.appDomain = appDomain;
			this.appId = appId;
			this.authInfo = (zkUser + ":" + zkPassword);
			this.zkUser = zkUser;
			this.zkAddr = zkAddr;
			this.zkPool = ZKPoolFactory.getZKPool(zkAddr, zkUser, zkPassword, timeout);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new UniConfigException(e.getMessage(), e);
		}
	}
	/**
	 * 组装节点真实路径
	 * @param path
	 * @return
	 * @author gucl
	 */
	private String appendDomainAndId(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(MyXAppPaaSConstant.UNIX_SEPERATOR + this.appDomain);
		sb.append(MyXAppPaaSConstant.UNIX_SEPERATOR + this.appId);
		sb.append(path);
		LOG.debug("Real path=" + sb.toString());
		return sb.toString();
	}

	@Override
	public String get(String path) throws UniConfigException {
		try {
			if (!exists(path)) {
				throw new UniConfigException("节点[" + path + "]不存在");
			}
			return get(path, null);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
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
			LOG.error(e.getMessage(),e);
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
			client.setNodeData(appendDomainAndId(path), value);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
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
				return client.exists(appendDomainAndId(path), configWatcher);
			}
			return client.exists(appendDomainAndId(path));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
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
			return client.getNodeData(appendDomainAndId(path), watcher);
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
			client.createNode(appendDomainAndId(path), createWritableACL(), bytes,
					ZKAddMode.convertMode(mode.getFlag()));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			if ((e instanceof KeeperException.NoAuthException)) {
				throw new UniConfigException("无访问节点[" + path + "]权限");
			}
			throw new UniConfigException("添加节点[" + path + "]失败", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ACL> createWritableACL() throws NoSuchAlgorithmException {
		//如果授权信息里没有用户名和密码，则访问控制列表面向所有人
		if(NO_AUTHINFO.equalsIgnoreCase(this.authInfo.trim())){
			return ZooDefs.Ids.OPEN_ACL_UNSAFE;
		}
		else{
			//访问控制列表为当前的authInfo信息
			List<ACL> acls = new ArrayList();
			Id id1 = new Id(this.zkAuthSchema, DigestAuthenticationProvider.generateDigest(this.authInfo));
			ACL userACL = new ACL(31, id1);
			acls.add(userACL);
			return acls;
			
		}
	}

	@Override
	public MutexLock getMutexLock(String path) throws UniConfigException {
		if (!validatePath(path))
			throw new UniConfigException("path[" + path + "]不合法,必须以'/'开头，且不能以'/'结尾");
		ZKClient zkClient = null;
		try {
			zkClient = getZkClientFromPool();
			return new MutexLock(zkClient.getInterProcessLock(appendDomainAndId(path)));
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
			return client.getNodeBytes(appendDomainAndId(path), configWatcher);
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
			return zkClient.getChildren(appendDomainAndId(path), configWatcher);
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
			zkClient.deleteNode(appendDomainAndId(path));
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
