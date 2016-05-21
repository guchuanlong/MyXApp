package com.myunihome.myxapp.paas.model;

import java.io.Serializable;

import redis.clients.jedis.JedisPoolConfig;

public class CacheConfigInfo implements Serializable{
	private static final long serialVersionUID = 197898012023214708L;
	private JedisPoolConfig jedisPoolConfig;
	private String jedisHostAndPorts;//示例：192.168.0.11:6379,192.168.0.12:6379,192.168.0.13:6379
    private String password;//仅用于单个redis，多个redis时忽略
	public CacheConfigInfo() {
		super();
	}
	public CacheConfigInfo(JedisPoolConfig jedisPoolConfig, String jedisHostAndPorts, String password) {
		super();
		this.jedisPoolConfig = jedisPoolConfig;
		this.jedisHostAndPorts = jedisHostAndPorts;
		this.password = password;
	}
	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}
	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
	
	public String getJedisHostAndPorts() {
		return jedisHostAndPorts;
	}
	public void setJedisHostAndPorts(String jedisHostAndPorts) {
		this.jedisHostAndPorts = jedisHostAndPorts;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
}
