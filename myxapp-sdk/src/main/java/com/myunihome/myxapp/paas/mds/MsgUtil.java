package com.myunihome.myxapp.paas.mds;

import java.util.Properties;

import com.google.gson.Gson;
import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;
import com.myunihome.myxapp.paas.mds.impl.consumer.MessageConsumer;
import com.myunihome.myxapp.paas.mds.impl.consumer.client.Config;
import com.myunihome.myxapp.paas.mds.impl.consumer.client.DynamicBrokersReader;
import com.myunihome.myxapp.paas.mds.impl.consumer.client.KafkaConfig;
import com.myunihome.myxapp.paas.mds.impl.consumer.client.ZkState;
import com.myunihome.myxapp.paas.mds.impl.sender.MessageSender;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;
import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKAddMode;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;
import com.myunihome.myxapp.paas.util.Assert;
import com.myunihome.myxapp.paas.util.ResourceUtil;

import kafka.producer.ProducerConfig;

public class MsgUtil {
	private MsgUtil() {

	}

	public static void validate(/*AuthDescriptor ad*/) {
		/*Assert.notNull(ad,
				ResourceUtil.getMessage("com.ai.paas.ipaas.common.auth_null"));
		Assert.notNull(ad.getAuthAdress(), ResourceUtil
				.getMessage("com.ai.paas.ipaas.common.auth_addr_null"));
		Assert.notNull(ad.getPid(), ResourceUtil
				.getMessage("com.ai.paas.ipaas.common.auth_pid_null"));
		Assert.notNull(ad.getPassword(), ResourceUtil
				.getMessage("com.ai.paas.ipaas.common.auth_passwd_null"));
		Assert.notNull(ad.getServiceId(),
				ResourceUtil.getMessage("com.ai.paas.ipaas.common.srvid_null"));*/

	}

	public static void validateTopic(String topic) {
		Assert.notNull(topic,
				ResourceUtil.getMessage("com.ai.paas.ipaas.msg.topic_null"));
	}

	public static IMessageSender instanceSender(String serviceId,
			 String topic) {
		IMessageSender sender = null;
		String msgConf;
		try {
			msgConf = UniConfigFactory.getUniConfigClient().get(
					MsgConstant.MSG_CONFIG_ROOT + serviceId
							+ MyXAppPaaSConstant.UNIX_SEPERATOR + topic + "/sender");
		} catch (UniConfigException e) {
			throw new MessageClientException(
					"MsgSenderFactory getClient error!", e);
		}
		// 封装成配置对象
		ProducerConfig cfg = null;
		Gson gson = new Gson();
		Properties props = gson.fromJson(msgConf, Properties.class);
		cfg = new ProducerConfig(props);
		int maxProducer = 0;
		if (null != props.get(MsgConstant.PROP_MAX_PRODUCER)) {
			maxProducer = Integer.parseInt((String) props
					.get(MsgConstant.PROP_MAX_PRODUCER));
		}
		// 这里还需要取一下分区数,由于没有记录下来，直接去取
		// 获取所有分区数，还得初始化
		KafkaConfig kafkaConfig = buildConfig(topic, serviceId,
				null);
		ZkState zkState = new ZkState(kafkaConfig);
		DynamicBrokersReader reader = new DynamicBrokersReader(kafkaConfig,
				zkState);
		int partitionCount = reader.getNumPartitions();
		zkState.close();
		reader.close();
		// 开始构建实例
		sender = new MessageSender(cfg, maxProducer, topic, partitionCount);
		return sender;
	}

	public static IMessageConsumer instanceConsumer(String serviceId,
			String consumerId,  String topic,
			IMsgProcessorHandler msgProcessorHandler) {
		IMessageConsumer consumer = null;
		KafkaConfig kafkaConfig = buildConfig(topic, serviceId,
				consumerId);
		// 开始构建实例
		ZKClient zkClient = null;
		try {
			UniConfigZkInfo uniConfZkInfo=MyXAppConfHelper.getInstance().getUniConfigZkConf();
			
			zkClient = ZKPoolFactory.getZKPool(uniConfZkInfo.getZkAddress(),
					uniConfZkInfo.getZkUser(), uniConfZkInfo.getZkPasswd(),
					60000).getZkClient(uniConfZkInfo.getZkAddress(),
							uniConfZkInfo.getZkUser());
		} catch (Exception e) {
			throw new MessageClientException("MessageConsumer init error!", e);
		}
		consumer = new MessageConsumer(zkClient, kafkaConfig,
				msgProcessorHandler);

		return consumer;
	}

	private static KafkaConfig buildConfig( String topic,
			String serviceId, String consumerId) {
		String msgConf;
		try {
			msgConf = UniConfigFactory.getUniConfigClient()
					.get(MsgConstant.MSG_CONFIG_ROOT + serviceId
							+ MyXAppPaaSConstant.UNIX_SEPERATOR + topic + "/consumer");
		} catch (UniConfigException e) {
			throw new MessageClientException(
					"MsgConsumerFactory getClient error!", e);
		}
		// 封装成配置对象
		KafkaConfig kafkaConfig = null;
		Gson gson = new Gson();
		Properties props = gson.fromJson(msgConf, Properties.class);
		// 这里需要将topic加上
		props.put("kafka.topic", topic);
		props.put(Config.MDS_USER_SRV_ID, serviceId);
		String basePath = MsgConstant.MSG_CONFIG_ROOT
				+ props.getProperty(Config.MDS_USER_SRV_ID)
				+ MyXAppPaaSConstant.UNIX_SEPERATOR
				+ props.getProperty("kafka.topic")
				+ MyXAppPaaSConstant.UNIX_SEPERATOR + consumerId
				+ MyXAppPaaSConstant.UNIX_SEPERATOR;
		
		UniConfigZkInfo uniConfZkInfo=MyXAppConfHelper.getInstance().getUniConfigZkConf();
		
		try {
			// mds.partition.runninglock.path
			props.put(Config.MDS_PARTITION_RUNNING_LOCK_PATH, basePath+ "partitions/running");

			// mds.partition.pauselock.path
			props.put(Config.MDS_PARTITION_PAUSE_LOCK_PATH, basePath+ "partitions/pause");

			// mds.partition.offset.basepath
			props.put(Config.MDS_PARTITION_OFFSET_BASE_PATH, basePath+ "offsets");

			// mds.consumer.base.path
			props.put(Config.MDS_CONSUMER_BASE_PATH, basePath + "consumers");
		} catch (Exception e) {
			throw new MessageClientException("MessageConsumer init error!", e);
		}
		// 再次初始化
		kafkaConfig = new KafkaConfig(props);
		return kafkaConfig;
	}

	public static IMessageConsumer instanceConsumer(String serviceId,
			 String topic,
			IMsgProcessorHandler msgProcessorHandler) {
		return instanceConsumer(serviceId, "consumer", topic,
				msgProcessorHandler);
	}
}
