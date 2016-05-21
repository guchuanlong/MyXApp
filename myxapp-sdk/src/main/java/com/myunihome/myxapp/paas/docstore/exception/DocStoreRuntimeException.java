package com.myunihome.myxapp.paas.docstore.exception;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class DocStoreRuntimeException extends PaasRuntimeException
{
  private static final long serialVersionUID = -2196845577057650318L;
  	public DocStoreRuntimeException(Exception ex) {
		super(ex);
	}
  	public DocStoreRuntimeException(String errDetail) {
		super(errDetail);
	}

	public DocStoreRuntimeException(String errCode, String errDetail) {
		super(errCode, errDetail);
	}

	public DocStoreRuntimeException(String errCode, Exception ex) {
		super(errCode, ex);
	}

	public DocStoreRuntimeException(String errCode, String errDetail, Exception ex) {
		super(errCode, errDetail, ex);
	}
}