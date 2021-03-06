package com.myunihome.myxapp.paas.exception;

public class PaasRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = -8886495803406807620L;
  private String errCode;
  private String errDetail;

  public PaasRuntimeException(String errDetail)
  {
    super(errDetail);
    this.errDetail = errDetail;
  }

  public PaasRuntimeException(String errCode, String errDetail) {
    super(errCode + ":" + errDetail);
    this.errCode = errCode;
    this.errDetail = errDetail;
  }

  public PaasRuntimeException(String errCode, Exception ex) {
    super(errCode, ex);
    this.errCode = errCode;
  }

  public PaasRuntimeException(String errCode, String errDetail, Exception ex) {
    super(errCode + ":" + errDetail, ex);
    this.errCode = errCode;
    this.errDetail = errDetail;
  }
  public PaasRuntimeException(Exception ex)
  {
    super(ex);
  }
  public String getErrCode() {
    return this.errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrDetail() {
    return this.errDetail;
  }

  public void setErrDetail(String errDetail) {
    this.errDetail = errDetail;
  }
}