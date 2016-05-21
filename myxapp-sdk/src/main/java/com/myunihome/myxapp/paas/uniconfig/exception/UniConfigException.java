package com.myunihome.myxapp.paas.uniconfig.exception;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class UniConfigException extends PaasRuntimeException {
	private static final long serialVersionUID = -1348655232003111956L;

	public UniConfigException(String errDetail) {
		super(errDetail);
	}

	public UniConfigException(String errCode, String errDetail) {
		super(errCode, errDetail);
	}

	public UniConfigException(String errCode, Exception ex) {
		super(errCode, ex);
	}

	public UniConfigException(String errCode, String errDetail, Exception ex) {
		super(errCode, errDetail, ex);
	}
}