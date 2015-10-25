package com.myunihome.myxapp.utils.util;

import com.myunihome.myxapp.base.exception.BusinessException;
import com.myunihome.myxapp.base.exception.CallerException;
import com.myunihome.myxapp.base.exception.SystemException;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.util.ExceptionUtil;
import com.myunihome.myxapp.paas.util.StringUtil;

public final class DubboExceptAssembler {

    private DubboExceptAssembler() {

    }

    public static CallerException assemble(BusinessException ex) {
        return new CallerException(ex.getErrorCode(), ex.getErrorMessage());
    }

    public static CallerException assemble(SystemException ex) {
        return new CallerException(ex.getErrorCode(), ex.getErrorMessage());
    }

    public static CallerException assemble(Exception ex) {
        return new CallerException(MyXAppPaaSConstant.ExceptionCode.SYSTEM_ERROR, StringUtil.isBlank(ex
                .getMessage()) ? "系统异常，请联系管理员" : ExceptionUtil.getTrace(ex));
    }

}
