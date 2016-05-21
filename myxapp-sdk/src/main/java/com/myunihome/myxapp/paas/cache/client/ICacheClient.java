package com.myunihome.myxapp.paas.cache.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存客户端接口
 *
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public abstract interface ICacheClient
{
  public abstract String set(String key, String value);

  public abstract String setex(String key, int seconds, String value);

  public abstract String get(String key);

  public abstract Long del(String key);

  public abstract Long del(String[] keys);

  public abstract Long expire(String key, int seconds);

  public abstract Long ttl(String key);

  public abstract boolean exists(String key);

  public abstract Long incr(String key);

  public abstract Long incrBy(String key, long increment);

  public abstract Long decr(String key);

  public abstract Long decrBy(String key, long decrement);

  public abstract Long lpush(String key, String[] strings);

  public abstract Long rpush(String key, String[] strings);

  public abstract Long lrem(String key, long count, String value);

  public abstract Long llen(String key);

  public abstract String lpop(String key);

  public abstract String rpop(String key);

  public abstract List<String> lrange(String key, long start, long end);

  public abstract List<String> lrangeAll(String key);

  public abstract Long hset(String key, String field, String value);

  public abstract Long hsetnx(String key, String field, String value);

  public abstract String hmset(String key, Map<String, String> hash);

  public abstract String hget(String key, String field);

  public abstract List<String> hmget(String key, String[] fields);

  public abstract Boolean hexists(String key, String field);

  public abstract Long hdel(String key, String[] fields);

  public abstract Long hlen(String key);

  public abstract Map<String, String> hgetAll(String key);

  public abstract Long sadd(String key, String[] members);

  public abstract Set<String> smembers(String key);

  public abstract Long srem(String key, String[] members);

  public abstract Long scard(String key);

  public abstract Set<String> sunion(String[] keys);

  public abstract Set<String> sdiff(String[] keys);

  public abstract Long sdiffstore(String dstkey, String[] keys);

  public abstract String set(byte[] key, byte[] value);

  public abstract String setex(byte[] key, int seconds, byte[] value);

  public abstract byte[] get(byte[] key);

  public abstract Long del(byte[] key);

  public abstract Long del(byte[]... keys);

  public abstract Long expire(byte[] key, int seconds);

  public abstract Long ttl(byte[] key);

  public abstract boolean exists(byte[] key);

  public abstract Long incr(byte[] key);

  public abstract Long incrBy(byte[] key, long increment);

  public abstract Long decr(byte[] key);

  public abstract Long decrBy(byte[] key, long decrement);

  public abstract Long lpush(byte[] key, byte[]... strings);

  public abstract Long rpush(byte[] key, byte[]... strings);

  public abstract Long llen(byte[] key);

  public abstract Long lrem(byte[] key, long count, byte[] value);

  public abstract byte[] lpop(byte[] key);

  public abstract byte[] rpop(byte[] key);

  public abstract List<byte[]> lrange(byte[] key, long start, long end);

  public abstract List<byte[]> lrangeAll(byte[] key);

  public abstract Long hset(byte[] key, byte[] field, byte[] value);

  public abstract Long hsetnx(byte[] key, byte[] field, byte[] value);

  public abstract String hmset(byte[] key, Map<byte[], byte[]> hash);

  public abstract byte[] hget(byte[] key, byte[] field);

  public abstract List<byte[]> hmget(byte[] key, byte[]... fields);

  public abstract Boolean hexists(byte[] key, byte[] field);

  public abstract Long hdel(byte[] key, byte[]... fields);

  public abstract Long hlen(byte[] key);

  public abstract Map<byte[], byte[]> hgetAll(byte[] key);

  public abstract Long sadd(byte[] key, byte[]... members);

  public abstract Set<byte[]> smembers(byte[] key);

  public abstract Long srem(byte[] key, byte[]... members);

  public abstract Long scard(byte[] key);

  public abstract Set<byte[]> sunion(byte[]... keys);

  public abstract Set<byte[]> sdiff(byte[]... keys);

  public abstract Long sdiffstore(byte[] dstkey, byte[]... keys);
}