package com.myunihome.myxapp.paas.uniconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.client.IZkAdminClient;
import com.myunihome.myxapp.paas.uniconfig.client.impl.ZkAdminClient;

public final class ZkAdminFactory {
    
    private ZkAdminFactory(){
        
    }

    private static final Logger LOG = LogManager.getLogger(ZkAdminFactory.class);

    public static IZkAdminClient getZkAdminClient() {
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
        return new ZkAdminClient(zkAddress, zkAuthSchema,zkUser, zkPassword,  zkTimeout);
    }

}
