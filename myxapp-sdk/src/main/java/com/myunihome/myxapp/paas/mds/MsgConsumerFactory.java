package com.myunihome.myxapp.paas.mds;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.myunihome.myxapp.paas.MyXAppConfHelper;
import com.myunihome.myxapp.paas.exception.PaasRuntimeException;
import com.myunihome.myxapp.paas.mds.impl.consumer.MessageConsumer;
import com.myunihome.myxapp.paas.mds.impl.consumer.client.KafkaConfig;
import com.myunihome.myxapp.paas.model.UniConfigZkInfo;
import com.myunihome.myxapp.paas.uniconfig.UniConfigFactory;
import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKClient;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKPoolFactory;
import com.myunihome.myxapp.paas.util.Assert;
import com.myunihome.myxapp.paas.util.ResourceUtil;

public class MsgConsumerFactory {
	static {
		ResourceUtil.addBundle("com.ai.paas.ipaas.mds.ipaas-message");
	}

	private MsgConsumerFactory() {
		// 禁止私有化
	}

	private static Map<String, IMessageConsumer> consumers = new ConcurrentHashMap<String, IMessageConsumer>();
	private static Map<String, IMessageConsumer> _consumers = new ConcurrentHashMap<String, IMessageConsumer>();

	/**
	 * 支持多消费端，获取消息队列消费端
	 * 
	 * @param ad
	 *            带服务标识身份认证信息
	 * @param msgProcessorHandler
	 *            消息处理器
	 * @param consumerId
	 *            消费者标识，默认为consumer，尽量不要使用consumer
	 * @return
	 */
	public static IMessageConsumer getClient(String mdsns,
			IMsgProcessorHandler msgProcessorHandler, String consumerId) {
		IMessageConsumer consumer = null;
		Assert.notNull(consumerId, "consumer id is null!");
		if ("consumer".equalsIgnoreCase(consumerId)) {
			throw new PaasRuntimeException("consumer id ");
		}
		List<String> children = null;
		try {
			children = UniConfigFactory.getUniConfigClient().listSubPath(
					MsgConstant.MSG_CONFIG_ROOT + mdsns);
		} catch (UniConfigException e) {
			throw new MessageClientException(
					"MsgSenderFactory getClient error!", e);
		}
		if (null == children || children.size() <= 0) {
			throw new MessageClientException(
					"MsgSenderFactory can not get config info for:"
							+ mdsns);
		}
		String topic = children.get(0);
		MsgUtil.validateTopic(topic);
		// 获取该用户申请的kafka服务配置信息
		consumer = MsgUtil.instanceConsumer(mdsns, consumerId,
				 topic, msgProcessorHandler);
		return consumer;
	}

	/**
	 * 获取消息队列消费端
	 * 
	 * @param ad
	 *            含服务标识等信息的身份验证对象
	 * @param msgProcessorHandler
	 *            如何处理消息的对象，里面的消息处理器和分区数对应
	 * @return
	 */
	public static IMessageConsumer getClient(String mdsns,
			IMsgProcessorHandler msgProcessorHandler) {
		IMessageConsumer consumer = null;

		List<String> children = null;
		try {
			children = UniConfigFactory.getUniConfigClient().listSubPath(
					MsgConstant.MSG_CONFIG_ROOT + mdsns);
		} catch (UniConfigException e) {
			throw new MessageClientException(
					"MsgSenderFactory getClient error!", e);
		}
		if (null == children || children.size() <= 0) {
			throw new MessageClientException(
					"MsgSenderFactory can not get config info for:"
							+ mdsns);
		}
		String topic = children.get(0);
		MsgUtil.validateTopic(topic);
		// 认证通过后，判断是否存在已有实例，有，直接返回
		if (null != consumers.get(mdsns + "_" + topic)) {
			consumer = consumers.get(mdsns + "_" + topic);
			return consumer;
		}
		// 获取内部zk地址后取得该用户的kafka配置信息，返回json
		// 获取该用户申请的kafka服务配置信息
		consumer = MsgUtil.instanceConsumer(mdsns,
				topic, msgProcessorHandler);
		consumers.put(topic, consumer);
		return consumer;
	}

	/**
	 * 获取消息队列消费端
	 * 
	 * @param ad
	 *            含服务标识等信息的身份验证对象
	 * @param topic
	 *            消息队列名称
	 * @param msgProcessorHandler
	 *            如何处理消息的对象，里面的消息处理器和分区数对应
	 * @return
	 */
	public static IMessageConsumer getClient(String mdsns, String topic,
			IMsgProcessorHandler msgProcessorHandler) {
		IMessageConsumer consumer = null;

		// 认证通过后，判断是否存在已有实例，有，直接返回
		if (null != consumers.get(mdsns + "_" + topic)) {
			consumer = consumers.get(mdsns + "_" + topic);
			return consumer;
		}
		// 传入用户描述对象，用户认证地址，服务申请号
		// 进行用户认证

		// 获取内部zk地址后取得该用户的kafka配置信息，返回json
		// 获取该用户申请的kafka服务配置信息
		consumer = MsgUtil.instanceConsumer(mdsns,
				topic, msgProcessorHandler);
		consumers.put(topic, consumer);
		return consumer;
	}

	/**
	 * 获取消息队列消费端
	 * 
	 * @param authResult
	 *            身份验证结果，主要为了获取消息队列信息
	 * @param props
	 *            队列信息 ，属性定义
	 * @param topic
	 *            消息队列名称
	 * @param msgProcessorHandler
	 *            如何处理消息的对象，里面的消息处理器和分区数对应
	 * @return
	 */
	public static IMessageConsumer getClient(String mdsns,
			Properties props, String topic,
			IMsgProcessorHandler msgProcessorHandler) {
		IMessageConsumer consumer = null;
		Assert.notNull(props,
				ResourceUtil.getMessage("com.ai.paas.ipaas.msg.cfg_null"));
		Assert.notNull(topic,
				ResourceUtil.getMessage("com.ai.paas.ipaas.msg.topic_null"));
		if (_consumers.containsKey(topic))
			return _consumers.get(topic);
		// 这里需要将topic加上
		props.put("kafka.topic", topic);
		KafkaConfig kafkaConfig = new KafkaConfig(props);
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
		_consumers.put(topic, consumer);
		return consumer;
	}
}
