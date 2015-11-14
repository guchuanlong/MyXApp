package com.myunihome.myxapp.paas.search.constants;

import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

public class SearchClientException extends PaasRuntimeException
{
  private static final long serialVersionUID = -8886495803406807620L;
  private String errCode;
  private String errDetail;

  public SearchClientException(String errDetail)
  {
    super(errDetail);
    this.errDetail = errDetail;
  }

  public SearchClientException(String errCode, String errDetail) {
    super(errCode + ":" + errDetail);
    this.errCode = errCode;
    this.errDetail = errDetail;
  }

  public SearchClientException(String errCode, Exception ex) {
    super(errCode + ":" + errCode, ex);
    this.errCode = errCode;
  }

  public SearchClientException(String errCode, String errDetail, Exception ex) {
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