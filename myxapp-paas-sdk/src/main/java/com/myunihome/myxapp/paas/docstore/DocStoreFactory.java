package com.myunihome.myxapp.paas.docstore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.cache.CacheFactory;
import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.docstore.client.IDocStoreClient;
import com.myunihome.myxapp.paas.docstore.client.impl.DocStoreClient;
import com.myunihome.myxapp.paas.model.DocStoreConfigInfo;
import com.myunihome.myxapp.paas.util.StringUtil;

public final class DocStoreFactory {
    
    private DocStoreFactory(){
        
    }

    private static final Log LOG = LogFactory.getLog(DocStoreFactory.class);
    private static Map<String, IDocStoreClient> docStoreClients = new ConcurrentHashMap<String, IDocStoreClient>();

    public static IDocStoreClient getDocStorageClient() {
        return getDocStorageClient(MyXAppPaaSConstant.DEFAULT);
    }
    
    public static IDocStoreClient getDocStorageClient(String namespace) {
        IDocStoreClient docStoreClient = null;
        if(StringUtil.isBlank(namespace)){
        	namespace=MyXAppPaaSConstant.DEFAULT;
        }
        LOG.debug("Get DocStore Conf ...");
        DocStoreConfigInfo config=MyXAppConfHelper.getInstance().getDocStoreConfig(namespace);
        String appDomain=MyXAppConfHelper.getInstance().getAppDomain();
        String appId=MyXAppConfHelper.getInstance().getAppId();
        String docStoreKey=appDomain+"$"+appId+"$"+namespace;
        
        if (docStoreClients.containsKey(docStoreKey)) {
          docStoreClient = (IDocStoreClient)docStoreClients.get(docStoreKey);
          return docStoreClient;
        }
        ICacheClient cacheClient=null;
        if(StringUtil.isBlank(config.getCacheNameSpace())){
        	cacheClient=CacheFactory.getCacheClient();
        }
        else{
        	cacheClient=CacheFactory.getCacheClient(config.getCacheNameSpace());
        }
        docStoreClient = new DocStoreClient(
        		config.getMongoDBHostAndPorts(),
        		config.getMongoDBDataBaseName(), 
        		config.getMongoDBUserName(),
        		config.getMongoDBPassword(),         		
        		config.getMongoDBGridFSBucket(),
        		config.getMongoDBGridFSMaxSize(),
        		config.getMongoDBGridFSFileLimitSize(),
        		cacheClient);
        docStoreClients.put(docStoreKey, docStoreClient);
        return docStoreClient;
    }


}
