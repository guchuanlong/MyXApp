package com.myunihome.myxapp.utils.extension;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.myunihome.myxapp.base.exception.BusinessException;
import com.myunihome.myxapp.base.exception.CallerException;
import com.myunihome.myxapp.base.exception.SystemException;

public class DubboRestExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        if ((ex instanceof CallerException) || (ex instanceof SystemException)
                || (ex instanceof BusinessException)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage())
                    .type("text/plain").build();
        } else if (ex instanceof ForbiddenException) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage())
                    .type("text/plain").build();
        } else if (ex instanceof BadRequestException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage())
                    .type("text/plain").build();
        } else if (ex instanceof InternalServerErrorException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage())
                    .type("text/plain").build();
        } else if (ex instanceof NotAuthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage())
                    .type("text/plain").build();
        } else if (ex instanceof NotAcceptableException) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ex.getMessage())
                    .type("text/plain").build();
        }  else if (ex instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage())
                    .type("text/plain").build();
        }else if (ex instanceof NullPointerException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("服务出现空指针异常")
                    .type("text/plain").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("出现未知异常，请联系服务提供者处理").type("text/plain").build();
        }
    }
}
