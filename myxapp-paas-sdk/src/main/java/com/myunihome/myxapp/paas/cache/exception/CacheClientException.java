package com.myunihome.myxapp.paas.cache.exception;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class CacheClientException extends PaasRuntimeException
{
  private static final long serialVersionUID = -2196845577057650318L;
  	public CacheClientException(Exception ex) {
		super(ex);
	}
  	public CacheClientException(String errDetail) {
		super(errDetail);
	}

	public CacheClientException(String errCode, String errDetail) {
		super(errCode, errDetail);
	}

	public CacheClientException(String errCode, Exception ex) {
		super(errCode, ex);
	}

	public CacheClientException(String errCode, String errDetail, Exception ex) {
		super(errCode, errDetail, ex);
	}
}