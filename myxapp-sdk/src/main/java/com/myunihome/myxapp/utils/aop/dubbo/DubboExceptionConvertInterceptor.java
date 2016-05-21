package com.myunihome.myxapp.utils.aop.dubbo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.omg.CORBA.SystemException;

import com.myunihome.myxapp.base.exception.BusinessException;
import com.myunihome.myxapp.utils.util.DubboExceptAssembler;

public class DubboExceptionConvertInterceptor {

    private static final Log LOG = LogFactory.getLog(DubboExceptionConvertInterceptor.class);

    public void convertException(JoinPoint joinPoint, Exception error) {
        LOG.error("执行["+joinPoint.getTarget().getClass().getName()+"]类中的["+joinPoint
                .getSignature().getName()+"]方法出错");
        LOG.error(error.getMessage(), error);
        if (error instanceof SystemException) {
            throw DubboExceptAssembler.assemble((SystemException) error);
        }
        if (error instanceof BusinessException) {
            throw DubboExceptAssembler.assemble((BusinessException) error);
        }
        throw DubboExceptAssembler.assemble(error);

    }
}
