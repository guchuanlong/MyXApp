package com.myunihome.myxapp.base.exception;


/**
 * Dubbo实现类中的系统异常类.<br>
 * 捕捉Dubbo实现类中系统级错误的异常类
 * @author gucl
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 *
 */
public class SystemException extends GenericException {

    private static final long serialVersionUID = 1L;

    /**
     * 系统异常构造器.<br>
     * @param message 异常信息
     * @ServiceMethod
     */
    public SystemException(String message) {
        super(message); 
        this.errorMessage = message;
    }
    /**
     * 系统异常构造器.<br>
     * @param errcode 异常信息代码
     * @param message 异常信息
     * @ServiceMethod
     */
    public SystemException(String errcode,String message) {
    	super(message); 
    	this.errorCode=errcode;
    	this.errorMessage = message;
    }

    /**
     * 系统异常构造器.<br>
     * @param oriEx 异常对象
     * @ServiceMethod
     */
    public SystemException(Exception oriEx) {
        super(oriEx);
        this.errOri = oriEx; 
        //ByteArrayOutputStream os = new ByteArrayOutputStream();
        //PrintStream p = new PrintStream(os);
        //this.errOri.printStackTrace(p);
    }

    /**
     * 系统异常构造器.<br>
     * @param message 异常信息
     * @param oriEx 异常对象
     * @ServiceMethod
     */
    public SystemException(String message, Exception oriEx) {
        super(message, oriEx); 
        this.errorMessage = message;
        this.errOri = oriEx;
        //ByteArrayOutputStream os = new ByteArrayOutputStream();
        //PrintStream p = new PrintStream(os);
        //this.errOri.printStackTrace(p);
    }

}
