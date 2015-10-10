package com.myunihome.myxapp.paas.docstore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.docstore.client.IDocStoreClient;
import com.myunihome.myxapp.paas.docstore.client.impl.DocStoreClient;
import com.myunihome.myxapp.paas.model.DocStoreConfigInfo;

public final class DocStoreFactory {
    
    private DocStoreFactory(){
        
    }

    private static final Logger LOG = LogManager.getLogger(DocStoreFactory.class);
    private static Map<String, IDocStoreClient> docStoreClients = new ConcurrentHashMap<String, IDocStoreClient>();

    public static IDocStoreClient getDocStorageClient() {
        IDocStoreClient docStoreClient = null;
        LOG.debug("Get DocStore Conf ...");
        DocStoreConfigInfo config=MyXAppConfHelper.getInstance().getDocStoreConfig();
        String appDomain=MyXAppConfHelper.getInstance().getAppDomain();
        String appId=MyXAppConfHelper.getInstance().getAppId();
        String docStoreKey=appDomain+"$"+appId;
        
        if (docStoreClients.containsKey(docStoreKey)) {
          docStoreClient = (IDocStoreClient)docStoreClients.get(docStoreKey);
          return docStoreClient;
        }
        docStoreClient = new DocStoreClient(
        		config.getMongoDBHostAndPorts(),
        		config.getMongoDBDataBaseName(), 
        		config.getMongoDBUserName(),
        		config.getMongoDBPassword(), 
        		config.getRedisHostAndPorts(),
        		config.getMongoDBGridFSBucket(),
        		config.getMongoDBGridFSMaxSize(),
        		config.getMongoDBGridFSFileLimitSize());
        docStoreClients.put(docStoreKey, docStoreClient);
        return docStoreClient;
    }


}
