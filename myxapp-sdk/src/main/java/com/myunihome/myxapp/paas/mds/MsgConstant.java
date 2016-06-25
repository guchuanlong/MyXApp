package com.myunihome.myxapp.paas.mds;

import com.myunihome.myxapp.paas.constants.MyXAppPaaSConstant;

public class MsgConstant extends MyXAppPaaSConstant {
	/**
	 * 消息的根路径
	 */
	public final static String MSG_CONFIG_ROOT = "/MDS/";

	/**
	 * 最大生产者
	 */
	public final static String PROP_MAX_PRODUCER = "maxProducer";

	/**
	 * 调整后的消费位置
	 */
	public final static String CONSUMER_ADJUSTED_OFFSET = "adjusted_offset";
}
