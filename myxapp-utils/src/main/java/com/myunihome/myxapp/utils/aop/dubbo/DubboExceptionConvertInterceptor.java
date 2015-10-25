package com.myunihome.myxapp.utils.aop.dubbo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.myunihome.myxapp.base.exception.BusinessException;
import com.myunihome.myxapp.base.exception.SystemException;
import com.myunihome.myxapp.utils.util.DubboExceptAssembler;

public class DubboExceptionConvertInterceptor {

    private static final Logger LOG = LogManager.getLogger(DubboExceptionConvertInterceptor.class);

    public void convertException(JoinPoint joinPoint, Exception error) {
        LOG.error("执行{}类中的{}方法出错", joinPoint.getTarget().getClass().getName(), joinPoint
                .getSignature().getName());
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
