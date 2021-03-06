package com.myunihome.myxapp.paas.exception;

public class PaasException extends Exception
{
  private static final long serialVersionUID = -2084191514082840768L;
  private String errCode;
  private String errDetail;

  public PaasException(String errDetail)
  {
    super(errDetail);
    this.errDetail = errDetail;
  }

  public PaasException(String errCode, String errDetail) {
    super(errCode + ":" + errDetail);
    this.errCode = errCode;
    this.errDetail = errDetail;
  }

  public PaasException(String errCode, Exception ex) {
    super(errCode, ex);
    this.errCode = errCode;
  }

  public PaasException(String errCode, String errDetail, Exception ex) {
    super(errCode + ":" + errDetail, ex);
    this.errCode = errCode;
    this.errDetail = errDetail;
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