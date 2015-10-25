package com.myunihome.myxapp.utils.extension;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.alibaba.fastjson.JSONObject;

public class DubboRestResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        int status = responseContext.getStatus();
        String reson = (responseContext.getEntity() == null) ? null : responseContext.getEntity()
                .toString();
        JSONObject data = new JSONObject();
        data.put("statusCode", status);
        if(status==200){
            data.put("statusMessage", "OK");
            data.put("data", responseContext.getEntity());
        }else{
            data.put("statusMessage", reson);
        }
        responseContext.setEntity(data);

    }

}
