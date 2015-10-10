package com.myunihome.myxapp.paas.sequence.exception;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class SequenceRuntimeException extends PaasRuntimeException
{
  private static final long serialVersionUID = -2196845577057650318L;
  	public SequenceRuntimeException(Exception ex) {
		super(ex);
	}
  	public SequenceRuntimeException(String errDetail) {
		super(errDetail);
	}

	public SequenceRuntimeException(String errCode, String errDetail) {
		super(errCode, errDetail);
	}

	public SequenceRuntimeException(String errCode, Exception ex) {
		super(errCode, ex);
	}

	public SequenceRuntimeException(String errCode, String errDetail, Exception ex) {
		super(errCode, errDetail, ex);
	}
}