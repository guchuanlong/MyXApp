package com.myunihome.myxapp.base.exception;

import java.io.Serializable;

/**
 * Dubbo接口定义抛出的异常类.<br>
 * Dubbo接口定义时使用该异常
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 *
 */
public class CallerException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 异常详情
     */
    private String errorDetail;

    /**
     * 异常构造器
     * @param errorCode 异常代码
     * @param errorMessage 异常信息
     * @ServiceMethod
     */
    public CallerException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 异常构造器
     * @param errorCode 异常代码
     * @param errorMessage 异常信息
     * @param errorDetail 异常详情
     * @ServiceMethod
     */
    public CallerException(String errorCode, String errorMessage, String errorDetail) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
    }
    
    /**
     * 异常构造器
     * @param errorCode 异常代码
     * @param errorMessage 异常信息
     * @param rawException 原始异常信息
     */
    public CallerException(String errorCode, String errorMessage, Exception rawException) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.setStackTrace(rawException.getStackTrace());
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
