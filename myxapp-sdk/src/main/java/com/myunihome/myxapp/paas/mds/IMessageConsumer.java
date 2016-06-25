package com.myunihome.myxapp.paas.mds;


public interface IMessageConsumer {
	
	
	/**
	 * 启动消费端
	 */
	public void start();
	
	/**
	 * 停止消费端
	 */
	public void stop();
}
