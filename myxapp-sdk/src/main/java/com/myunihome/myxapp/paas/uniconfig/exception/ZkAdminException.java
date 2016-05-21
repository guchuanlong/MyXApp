package com.myunihome.myxapp.paas.uniconfig.exception;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class ZkAdminException extends PaasRuntimeException {
	private static final long serialVersionUID = -1348655232003111956L;

	public ZkAdminException(String errDetail) {
		super(errDetail);
	}

	public ZkAdminException(String errCode, String errDetail) {
		super(errCode, errDetail);
	}

	public ZkAdminException(String errCode, Exception ex) {
		super(errCode, ex);
	}

	public ZkAdminException(String errCode, String errDetail, Exception ex) {
		super(errCode, errDetail, ex);
	}
}