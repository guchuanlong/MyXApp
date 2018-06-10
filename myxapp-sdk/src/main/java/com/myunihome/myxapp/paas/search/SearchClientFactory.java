package com.myunihome.myxapp.paas.search;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.model.ESConfigInfo;
import com.myunihome.myxapp.paas.search.client.ISearchClient;
import com.myunihome.myxapp.paas.search.client.impl.SearchClientImpl;

public class SearchClientFactory {
	private static final transient Logger log = LoggerFactory.getLogger(SearchClientFactory.class);
	private static Map<String, ISearchClient> searchClients = new ConcurrentHashMap<String, ISearchClient>();

	public static ISearchClient getSearchClient(String namespace) throws Exception {
		ISearchClient iSearchClient = null;
		log.info("Check Formal Parameter AuthDescriptor ...");
		String clientKey = MyXAppConfHelper.getInstance().getAppId() + "$" + namespace;
		if (searchClients.containsKey(clientKey)) {
			iSearchClient = (ISearchClient) searchClients.get(clientKey);
			return iSearchClient;
		}

		log.info("Get confBase&conf ...");

		String indexName = String.valueOf(Math.abs(clientKey.hashCode()));
		log.info("indexName="+indexName);
		ESConfigInfo esconf=MyXAppConfHelper.getInstance().getSearchConfig(namespace);
		String hosts = esconf.getHostAndPorts();

		iSearchClient = new SearchClientImpl(hosts, indexName);
		searchClients.put(clientKey, iSearchClient);
		return iSearchClient;
	}
}