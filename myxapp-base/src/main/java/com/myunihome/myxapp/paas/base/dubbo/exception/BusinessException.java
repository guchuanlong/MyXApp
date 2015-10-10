package com.myunihome.myxapp.paas.base.dubbo.exception;


/**
 * Dubbo实现类中的业务异常类.<br>
 * 捕捉Dubbo实现类中的业务级错误的异常类
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class BusinessException extends GenericException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * @param errorCode 异常代码
     * @param errorMessage 异常信息
     */
    public BusinessException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
