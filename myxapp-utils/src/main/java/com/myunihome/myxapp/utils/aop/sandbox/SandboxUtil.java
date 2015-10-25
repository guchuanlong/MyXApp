package com.myunihome.myxapp.utils.aop.sandbox;

import com.alibaba.fastjson.JSON;
import com.myunihome.myxapp.base.exception.SystemException;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.util.StringUtil;

public class SandboxUtil {

    private static final String PATH = "/com/myunihome/myxapp/osp/";

    private static IUniConfigClient configCenterClient;

    private static IUniConfigClient getUniConfigClient() {
        if (configCenterClient == null) {
            configCenterClient = UniConfigFactory.getUniConfigClient();
        }
        return configCenterClient;
    }

    public static void addAPISetting(String interfaceName, String method, APISetting data) {
        if (StringUtil.isBlank(interfaceName)) {
            throw new SystemException("向配置中心增加API设置错误:服务接口为空");
        }
        if (StringUtil.isBlank(method)) {
            throw new SystemException("向配置中心增加API设置错误:方法为空");
        }
        if (data == null) {
            throw new SystemException("向配置中心增加API设置错误:API设置信息为空");
        }
        if (StringUtil.isBlank(data.getCallType())) {
            throw new SystemException("向配置中心增加API设置错误:API调用方式为空");
        }
        if (APISetting.CALL_SANDBOX.equals(data.getCallType())) {
            if (StringUtil.isBlank(data.getSandboxResp())) {
                throw new SystemException("向配置中心增加API设置错误:API沙箱调用返回报文为空");
            }
        }
        String pth = PATH + interfaceName + "/" + method;
        IUniConfigClient client = getUniConfigClient();
        if (client.exists(pth)) {
            client.modify(pth, JSON.toJSONString(data));
            return;
        }
        client.add(pth, JSON.toJSONString(data));
    }

    public static APISetting getAPISetting(String interfaceName, String method) {
        if (StringUtil.isBlank(interfaceName)) {
            throw new SystemException("获取API设置错误:服务接口为空");
        }
        if (StringUtil.isBlank(method)) {
            throw new SystemException("获取API设置错误:方法为空");
        }
        String pth = PATH + interfaceName + "/" + method;
        IUniConfigClient client = getUniConfigClient();
        if (!client.exists(pth)) {
            return null;
        }
        String data = client.get(pth);
        if (StringUtil.isBlank(data))
            return null;
        return JSON.parseObject(data, APISetting.class);
    }

}
