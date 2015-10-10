package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

public class UniConfigZkInfo implements Serializable{
	private static final long serialVersionUID = -7405684580384565622L;
	//配置中心zookeeper主机和端口
	private String zkAddress;
	//配置中心zookeeper权限认证方式，目前只支持digest方式
    private String zkAuthSchema;
    //配置中心zookeeper授权用户
    private String zkUser;
    //配置中心zookeeper授权密码
    private String zkPasswd;
    //配置中心zookeeper连接请求超时时间（millseconds）
    private int zkTimeout;
    
    public UniConfigZkInfo() {
		super();
	}    
    
	public UniConfigZkInfo(String zkAddress, String zkAuthSchema, String zkUser, String zkPasswd, int zkTimeout) {
		super();
		this.zkAddress = zkAddress;
		this.zkAuthSchema = zkAuthSchema;
		this.zkUser = zkUser;
		this.zkPasswd = zkPasswd;
		this.zkTimeout = zkTimeout;
	}

	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String getZkAuthSchema() {
		return zkAuthSchema;
	}

	public void setZkAuthSchema(String zkAuthSchema) {
		this.zkAuthSchema = zkAuthSchema;
	}

	public String getZkUser() {
		return zkUser;
	}

	public void setZkUser(String zkUser) {
		this.zkUser = zkUser;
	}

	public String getZkPasswd() {
		return zkPasswd;
	}

	public void setZkPasswd(String zkPasswd) {
		this.zkPasswd = zkPasswd;
	}

	public int getZkTimeout() {
		return zkTimeout;
	}

	public void setZkTimeout(int zkTimeout) {
		this.zkTimeout = zkTimeout;
	}

	
}
