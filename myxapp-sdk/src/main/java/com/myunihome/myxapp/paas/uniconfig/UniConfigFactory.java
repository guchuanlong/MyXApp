package com.myunihome.myxapp.paas.uniconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.client.IUniConfigClient;
import com.myunihome.myxapp.paas.uniconfig.client.impl.UniConfigClient;

public final class UniConfigFactory {
	private static final Logger LOG = LoggerFactory.getLogger(UniConfigFactory.class);
    private UniConfigFactory(){
        
    }
    public static IUniConfigClient getUniConfigClient() {
    	String appDomain=MyXAppConfHelper.getInstance().getAppDomain();
    	String appId=MyXAppConfHelper.getInstance().getAppId();
    	UniConfigZkInfo uniConfZkInfo=MyXAppConfHelper.getInstance().getUniConfigZkConf();
        String zkAddress = uniConfZkInfo.getZkAddress();
        String zkAuthSchema = uniConfZkInfo.getZkAuthSchema();
        String zkUser = uniConfZkInfo.getZkUser();
        String zkPassword = uniConfZkInfo.getZkPasswd();
        int zkTimeout = uniConfZkInfo.getZkTimeout();
        LOG.debug("uniconfig zkAddress:" + zkAddress);
        LOG.debug("uniconfig zkUser:" + zkUser);
        LOG.debug("uniconfig zkPassword:" + zkPassword);
        LOG.debug("uniconfig zkTimeout:" + zkTimeout);
        LOG.debug("uniconfig zkAuthSchema:" + zkAuthSchema);
        return new UniConfigClient(appDomain,appId,zkAddress, zkUser, zkPassword,  zkTimeout);
    }

}
