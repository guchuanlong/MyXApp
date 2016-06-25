

package com.myunihome.myxapp.paas.mds.impl.consumer.client;

public interface IBrokerReader {

	GlobalPartitionInformation getCurrentBrokers();

	void close();
}
