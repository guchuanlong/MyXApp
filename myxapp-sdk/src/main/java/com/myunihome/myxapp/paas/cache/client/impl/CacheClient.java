package com.myunihome.myxapp.paas.cache.client.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.cache.exception.CacheClientException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Cache客户端
 *
 * Date: 2015年10月2日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class CacheClient implements ICacheClient
{
  private static final transient Logger LOG = LoggerFactory.getLogger(CacheClient.class);
  private JedisPool jedisPool;
  private JedisPoolConfig jedisPoolConfig;
  private static final int TIMEOUT_KEY = 5000;
  private String jedisHostAndPort;
  private String jedisPassword;
  
  public CacheClient(JedisPoolConfig config, String hostAndPort) {
	  this.jedisPoolConfig = config;
	  this.jedisHostAndPort = hostAndPort;
	  createPoolNonPassword();
  }
  public CacheClient(JedisPoolConfig config, String hostAndPort, String pwd) {
    this.jedisPoolConfig = config;
    this.jedisHostAndPort = hostAndPort;
    this.jedisPassword = pwd;
    createPoolWithPassword();
  }
  
  /**
   * 创建JedisPool 
   * @author gucl
   */
  private synchronized void createPoolNonPassword() {
    if (!canConnection()) {
      LOG.info("Create JedisPool Begin ...");
      try {
        String[] hostAndPortArr = this.jedisHostAndPort.split(":");
        this.jedisPool = new JedisPool(this.jedisPoolConfig, hostAndPortArr[0], Integer.parseInt(hostAndPortArr[1]), TIMEOUT_KEY);
        if (canConnection())
          LOG.info("Redis Server Info:" + this.jedisHostAndPort);
        LOG.info("Create JedisPool Done ...");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  /**
   * 创建JedisPool 
   * @author gucl
   */
  private synchronized void createPoolWithPassword() {
	  if (!canConnection()) {
		  LOG.info("Create JedisPool Begin ...");
		  try {
			  String[] hostAndPortArr = this.jedisHostAndPort.split(":");
			  this.jedisPool = new JedisPool(this.jedisPoolConfig, hostAndPortArr[0], Integer.parseInt(hostAndPortArr[1]), TIMEOUT_KEY, this.jedisPassword);
			  if (canConnection())
				  LOG.info("Redis Server Info:" + this.jedisHostAndPort);
			  LOG.info("Create JedisPool Done ...");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
  }
  /**
   * 判断是否可以连接
   * @return
   * @author gucl
   */
  private boolean canConnection()
  {
    if (this.jedisPool == null)
      return false;
    Jedis jedis = null;
    try {
      jedis = getJedis();
      jedis.connect();
      jedis.get("ok");
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
    return true;
  }
  
  private Jedis getJedis() {
    return this.jedisPool.getResource();
  }
  
  private void returnResource(Jedis jedis) {
    this.jedisPool.returnResourceObject(jedis);
  }
  
  public void destroyPool() {
    if (null != this.jedisPool) {
      this.jedisPool.destroy();
    }
  }
  @Override
  public String set(String key, String value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.set(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
  }
  @Override
  public String setex(String key, int seconds, String value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.setex(key, seconds, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String get(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.get(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long del(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.del(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long del(String[] keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.del(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long expire(String key, int seconds) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.expire(key, seconds);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long ttl(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.ttl(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public boolean exists(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.exists(key).booleanValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long incr(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.incr(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long incrBy(String key, long increment) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.incrBy(key, increment);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long decr(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.decr(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long decrBy(String key, long decrement) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.decrBy(key, decrement);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long lpush(String key, String[] strings) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lpush(key, strings);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long rpush(String key, String[] strings) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.rpush(key, strings);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long llen(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.llen(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String lpop(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String rpop(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.rpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<String> lrange(String key, long start, long end) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrange(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<String> lrangeAll(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrange(key, 0L, -1L);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hset(String key, String field, String value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hset(key, field, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hsetnx(String key, String field, String value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hsetnx(key, field, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String hmset(String key, Map<String, String> hash) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hmset(key, hash);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String hget(String key, String field) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hget(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<String> hmget(String key, String[] fields) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hmget(key, fields);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Boolean hexists(String key, String field) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hexists(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hdel(String key, String[] fields) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hdel(key, fields);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hlen(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hlen(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Map<String, String> hgetAll(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hgetAll(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long sadd(String key, String[] members) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sadd(key, members);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<String> smembers(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.smembers(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long srem(String key, String[] members) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.srem(key, members);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long scard(String key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.scard(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<String> sunion(String[] keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sunion(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<String> sdiff(String[] keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sdiff(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long sdiffstore(String dstkey, String[] keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sdiffstore(dstkey, keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String set(byte[] key, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.set(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String setex(byte[] key, int seconds, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.setex(key, seconds, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public byte[] get(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.get(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long del(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.del(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long del(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.del(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long expire(byte[] key, int seconds) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.expire(key, seconds);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long ttl(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.ttl(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public boolean exists(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.exists(key).booleanValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long incr(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.incr(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long incrBy(byte[] key, long increment) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.incrBy(key, increment);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long decr(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.decr(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long decrBy(byte[] key, long decrement) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.decrBy(key, decrement);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long lpush(byte[] key, byte[]... strings) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lpush(key, strings);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long rpush(byte[] key, byte[]... strings) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.rpush(key, strings);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long llen(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.llen(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public byte[] lpop(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public byte[] rpop(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.rpop(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<byte[]> lrange(byte[] key, long start, long end) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrange(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<byte[]> lrangeAll(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrange(key, 0L, -1L);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hset(byte[] key, byte[] field, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hset(key, field, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hsetnx(byte[] key, byte[] field, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hsetnx(key, field, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public String hmset(byte[] key, Map<byte[], byte[]> hash) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hmset(key, hash);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public byte[] hget(byte[] key, byte[] field) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hget(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public List<byte[]> hmget(byte[] key, byte[]... fields) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hmget(key, fields);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Boolean hexists(byte[] key, byte[] field) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hexists(key, field);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hdel(byte[] key, byte[]... fields) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hdel(key, fields);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long hlen(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hlen(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  
  public Map<byte[], byte[]> hgetAll(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.hgetAll(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  
  public Long sadd(byte[] key, byte[]... members) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sadd(key, members);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<byte[]> smembers(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.smembers(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long srem(byte[] key, byte[]... members) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.srem(key, members);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long scard(byte[] key) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.scard(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<byte[]> sunion(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sunion(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Set<byte[]> sdiff(byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sdiff(keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null)
        returnResource(jedis);
    }
  }
  @Override
  public Long sdiffstore(byte[] dstkey, byte[]... keys) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.sdiffstore(dstkey, keys);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
  }
  @Override
  public Long lrem(String key, long count, String value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrem(key, count, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
  }
  @Override
  public Long lrem(byte[] key, long count, byte[] value) {
    Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.lrem(key, count, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
  }
@Override
public Long setnx(String key, String value) {
	Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.setnx(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
}
@Override
public String getSet(String key, String value) {
	Jedis jedis = null;
    try {
      jedis = getJedis();
      return jedis.getSet(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    } finally {
      if (jedis != null) {
        returnResource(jedis);
      }
    }
}
}

