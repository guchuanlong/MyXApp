package com.myunihome.myxapp.paas.cache.client.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myunihome.myxapp.paas.cache.client.ICacheClient;
import com.myunihome.myxapp.paas.cache.exception.CacheClientException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * CacheCluster客户端
 *
 * Date: 2015年10月2日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class CacheClusterClient implements ICacheClient
{
  private static final transient Log LOG = LogFactory.getLog(CacheClusterClient.class);
  private JedisPoolConfig jedisPoolConfig;
  private String[] jedisHostAndPorts;
  private String jedisPassword;
  private JedisCluster jedisCluster;
  
  public CacheClusterClient(JedisPoolConfig config, String[] hostAndPorts) {
    this.jedisPoolConfig = config;
    this.jedisHostAndPorts = hostAndPorts;
    getCluster();
  }
  public CacheClusterClient(JedisPoolConfig config, String[] hostAndPorts, String pwd) {
	  this.jedisPoolConfig = config;
	  this.jedisHostAndPorts = hostAndPorts;
	  this.jedisPassword = pwd;
	  getCluster();
  }
  
  private void getCluster() {
    LOG.debug("-----------------------创建JedisPool------------------------begin---");
    Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    try {
      for (String address : this.jedisHostAndPorts) {
        String[] ipAndPort = address.split(":");
        jedisClusterNodes.add(new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
        LOG.debug(address);
      }
      
      this.jedisCluster = new JedisCluster(jedisClusterNodes, this.jedisPoolConfig);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      e.printStackTrace();
    }
    LOG.debug("-----------------------创建JedisPool------------------------end---");
  }
  @Override
  public String set(String key, String value) {
    try {
      String str = this.jedisCluster.set(key, value);
      return str;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String setex(String key, int seconds, String value) {
    try {
      String str = this.jedisCluster.setex(key, seconds, value);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String get(String key) {
    try {
      String str = this.jedisCluster.get(key);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long del(String key) {
    try {
      Long localLong = this.jedisCluster.del(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long del(String[] keys) {
    try {
      Long localLong = this.jedisCluster.del(keys);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long expire(String key, int seconds) {
    try {
      Long localLong = this.jedisCluster.expire(key, seconds);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long ttl(String key) {
    try {
      Long localLong = this.jedisCluster.ttl(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public boolean exists(String key) {
    try {
      boolean bool = this.jedisCluster.exists(key).booleanValue();
      return bool;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long incr(String key) {
    try {
      Long localLong = this.jedisCluster.incr(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long incrBy(String key, long increment) {
    try {
      Long localLong = this.jedisCluster.incrBy(key, increment);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long decr(String key) {
    try {
      Long localLong = this.jedisCluster.decr(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long decrBy(String key, long decrement) {
    try {
      Long localLong = this.jedisCluster.decrBy(key, decrement);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long lpush(String key, String[] strings) {
    try {
      Long localLong = this.jedisCluster.lpush(key, strings);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long rpush(String key, String[] strings) {
    try {
      Long localLong = this.jedisCluster.rpush(key, strings);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long llen(String key) {
    try {
      Long localLong = this.jedisCluster.llen(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String lpop(String key) {
    try {
      String str = this.jedisCluster.lpop(key);return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String rpop(String key) {
    try {
      String str = this.jedisCluster.rpop(key);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<String> lrange(String key, long start, long end) {
    try {
      List<String> localList = this.jedisCluster.lrange(key, start, end);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<String> lrangeAll(String key) {
    try {
      List<String> localList = this.jedisCluster.lrange(key, 0L, -1L);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hset(String key, String field, String value) {
    try {
      Long localLong = this.jedisCluster.hset(key, field, value);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hsetnx(String key, String field, String value) {
    try {
      Long localLong = this.jedisCluster.hsetnx(key, field, value);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String hmset(String key, Map<String, String> hash) {
    try {
      String str = this.jedisCluster.hmset(key, hash);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String hget(String key, String field) {
    try {
      String str = this.jedisCluster.hget(key, field);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<String> hmget(String key, String[] fields) {
    try {
      List<String> localList = this.jedisCluster.hmget(key, fields);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Boolean hexists(String key, String field) {
    try {
      Boolean localBoolean = this.jedisCluster.hexists(key, field);
      return localBoolean;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hdel(String key, String[] fields) {
    try {
      Long localLong = this.jedisCluster.hdel(key, fields);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hlen(String key) {
    try {
      Long localLong = this.jedisCluster.hlen(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Map<String, String> hgetAll(String key) {
    try {
      Map<String, String> localMap = this.jedisCluster.hgetAll(key);
      return localMap;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long sadd(String key, String[] members) {
    try {
      Long localLong = this.jedisCluster.sadd(key, members);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<String> smembers(String key) {
    try {
      Set<String> localSet = this.jedisCluster.smembers(key);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long srem(String key, String[] members) {
    try {
      Long localLong = this.jedisCluster.srem(key, members);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long scard(String key) {
    try {
      Long localLong = this.jedisCluster.scard(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<String> sunion(String[] keys) {
    try {
      Set<String> localSet = this.jedisCluster.sunion(keys);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<String> sdiff(String[] keys) {
    try {
      Set<String> localSet = this.jedisCluster.sdiff(keys);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long sdiffstore(String dstkey, String[] keys) {
    try {
      Long localLong = this.jedisCluster.sdiffstore(dstkey, keys);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String set(byte[] key, byte[] value) {
    try {
      String str = this.jedisCluster.set(key, value);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String setex(byte[] key, int seconds, byte[] value) {
    try {
      String str = this.jedisCluster.setex(key, seconds, value);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public byte[] get(byte[] key) {
    try {
      byte[] arrayOfByte = this.jedisCluster.get(key);
      return arrayOfByte;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long del(byte[] key) {
    try {
      Long localLong = this.jedisCluster.del(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long del(byte[]... keys) {
    try {
      Long localLong = this.jedisCluster.del(keys);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long expire(byte[] key, int seconds) {
    try {
      Long localLong = this.jedisCluster.expire(key, seconds);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long ttl(byte[] key) {
    try {
      Long localLong = this.jedisCluster.ttl(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public boolean exists(byte[] key) {
    try {
      boolean bool = this.jedisCluster.exists(key).booleanValue();
      return bool;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long incr(byte[] key) {
    try {
      Long localLong = this.jedisCluster.incr(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long incrBy(byte[] key, long increment) {
    try {
      Long localLong = this.jedisCluster.incrBy(key, increment);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long decr(byte[] key) {
    try {
      Long localLong = this.jedisCluster.decr(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long decrBy(byte[] key, long decrement) {
    try {
      Long localLong = this.jedisCluster.decrBy(key, decrement);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long lpush(byte[] key, byte[]... strings) {
    try {
      Long localLong = this.jedisCluster.lpush(key, strings);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long rpush(byte[] key, byte[]... strings) {
    try {
      Long localLong = this.jedisCluster.rpush(key, strings);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long llen(byte[] key) {
    try {
      Long localLong = this.jedisCluster.llen(key);return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public byte[] lpop(byte[] key) {
    try {
      byte[] arrayOfByte = this.jedisCluster.lpop(key);
      return arrayOfByte;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public byte[] rpop(byte[] key) {
    try {
      byte[] arrayOfByte = this.jedisCluster.rpop(key);
      return arrayOfByte;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<byte[]> lrange(byte[] key, long start, long end) {
    try {
      List<byte[]> localList = this.jedisCluster.lrange(key, start, end);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<byte[]> lrangeAll(byte[] key) {
    try {
      List<byte[]> localList = this.jedisCluster.lrange(key, 0L, -1L);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hset(byte[] key, byte[] field, byte[] value) {
    try {
      Long localLong = this.jedisCluster.hset(key, field, value);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hsetnx(byte[] key, byte[] field, byte[] value) {
    try {
      Long localLong = this.jedisCluster.hsetnx(key, field, value);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public String hmset(byte[] key, Map<byte[], byte[]> hash) {
    try {
      String str = this.jedisCluster.hmset(key, hash);
      return str;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public byte[] hget(byte[] key, byte[] field) {
    try {
      byte[] arrayOfByte = this.jedisCluster.hget(key, field);
      return arrayOfByte;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public List<byte[]> hmget(byte[] key, byte[]... fields) {
    try {
      List<byte[]> localList = this.jedisCluster.hmget(key, fields);
      return localList;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Boolean hexists(byte[] key, byte[] field) {
    try {
      Boolean localBoolean = this.jedisCluster.hexists(key, field);return localBoolean;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hdel(byte[] key, byte[]... fields) {
    try {
      Long localLong = this.jedisCluster.hdel(key, fields);return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long hlen(byte[] key) {
    try {
      Long localLong = this.jedisCluster.hlen(key);return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Map<byte[], byte[]> hgetAll(byte[] key) {
    try {
      Map<byte[], byte[]> localMap = this.jedisCluster.hgetAll(key);return localMap;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long sadd(byte[] key, byte[]... members) {
    try {
      Long localLong = this.jedisCluster.sadd(key, members);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<byte[]> smembers(byte[] key) {
    try {
      Set<byte[]> localSet = this.jedisCluster.smembers(key);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long srem(byte[] key, byte[]... members) {
    try {
      Long localLong = this.jedisCluster.srem(key, members);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long scard(byte[] key) {
    try {
      Long localLong = this.jedisCluster.scard(key);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<byte[]> sunion(byte[]... keys) {
    try {
      Set<byte[]> localSet = this.jedisCluster.sunion(keys);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Set<byte[]> sdiff(byte[]... keys) {
    try {
      Set<byte[]> localSet = this.jedisCluster.sdiff(keys);
      return localSet;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long sdiffstore(byte[] dstkey, byte[]... keys) {
    try {
      Long localLong = this.jedisCluster.sdiffstore(dstkey, keys);
      return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long lrem(String key, long count, String value)
  {
    try {
      Long localLong = this.jedisCluster.lrem(key, count, value);return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
  @Override
  public Long lrem(byte[] key, long count, byte[] value)
  {
    try {
      Long localLong = this.jedisCluster.lrem(key, count, value);return localLong;
    } catch (Exception e) { 
      LOG.error(e.getMessage(), e);
      throw new CacheClientException(e);
    }
    finally {}
  }
}

