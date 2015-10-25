package com.myunihome.myxapp.base.vo;

import java.io.Serializable;

/**
 * 鉴权类.<br>
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class BaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 租户Id
	 */
	private String tenantId;
	/**
	 * 租户密码
	 */
	private String tenantPwd;

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantPwd() {
		return tenantPwd;
	}
	public void setTenantPwd(String tenantPwd) {
		this.tenantPwd = tenantPwd;
	}
	
	
}
