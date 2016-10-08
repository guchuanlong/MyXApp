package com.myunihome.myxapp.paas.lock.redislock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.myunihome.myxapp.paas.exception.PaasRuntimeException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class SingleRedisInterProcessLock {

		private static final Logger LOGGER = LoggerFactory.getLogger(SingleRedisInterProcessLock.class);

		private static final int DEFAULT_SINGLE_EXPIRE_TIME = 3;
		
		private static final int DEFAULT_BATCH_EXPIRE_TIME = 6;

		private final JedisPool jedisPool;
		
		private final ConcurrentMap<Thread, LockData> threadData = Maps.newConcurrentMap();
		
		private static class LockData
	    {
	        final Thread owningThread;
	        final String lockPath;
	        final AtomicInteger lockCount = new AtomicInteger(1);

	        private LockData(Thread owningThread, String lockPath)
	        {
	            this.owningThread = owningThread;
	            this.lockPath = lockPath;
	        }
	    }
		/**
		 * 构造
		 * @author gucl
		 */
		public SingleRedisInterProcessLock(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
		}

		/**
		 * 获取锁  如果锁可用   立即返回true，  否则返回false
		 * @author gucl
		 * @param key
		 * @return
		 */
		public boolean tryLock(String key) {
			return tryLock(key, 0L, null);
		}

		/**
		 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
		 * @author gucl
		 * @param key
		 * @param timeout
		 * @param unit
		 * @return
		 */
		public boolean tryLock(String key, long timeout, TimeUnit unit) {
			Jedis jedis = null;
			Thread currentThread = Thread.currentThread();
			LockData lockData = threadData.get(currentThread);
	        if ( lockData != null )
	        {
	            // re-entering
	            lockData.lockCount.incrementAndGet();
	            return true;
	        }
			try {
				jedis = getResource();
				long nano = System.nanoTime();
				do {
					LOGGER.debug("try lock key: " + key);
					Long i = jedis.setnx(key, key);
					if (i == 1) { 
						jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
						LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
						LockData newLockData = new LockData(currentThread, key);
				        threadData.put(currentThread, newLockData);
						return Boolean.TRUE;
					} else { // 存在锁
						if (LOGGER.isDebugEnabled()) {
							String desc = jedis.get(key);
							LOGGER.debug("key: " + key + " locked by another business：" + desc);
						}
					}
					if (timeout == 0) {
						break;
					}
					Thread.sleep(300);
				} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
				return Boolean.FALSE;
			} catch (JedisConnectionException je) {
				LOGGER.error(je.getMessage(), je);
				returnBrokenResource(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				returnResource(jedis);
			}
			return Boolean.FALSE;
		}

		/**
		 * 如果锁空闲立即返回   获取失败 一直等待
		 * @author gucl
		 * @param key
		 */
		public void lock(String key) {
			Jedis jedis = null;
			Thread currentThread = Thread.currentThread();
			LockData lockData = threadData.get(currentThread);
	        if ( lockData != null )
	        {
	            // re-entering
	            lockData.lockCount.incrementAndGet();
	            return;
	        }
			try {
				jedis = getResource();
				do {
					LOGGER.debug("lock key: " + key);
					Long i = jedis.setnx(key, key);
					if (i == 1) { 
						jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
						LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
						LockData newLockData = new LockData(currentThread, key);
				        threadData.put(currentThread, newLockData);
						return;
					} else {
						if (LOGGER.isDebugEnabled()) {
							String desc = jedis.get(key);
							LOGGER.debug("key: " + key + " locked by another business：" + desc);
						}
					}
					Thread.sleep(300); 
				} while (true);
			} catch (JedisConnectionException je) {
				LOGGER.error(je.getMessage(), je);
				returnBrokenResource(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				returnResource(jedis);
			}
		}

		/**
		 * 释放锁
		 * @author gucl
		 * @param key
		 */
		public void unLock(String key) {
	        Thread currentThread = Thread.currentThread();
	        LockData lockData = threadData.get(currentThread);
	        if ( lockData == null )
	        {
	            throw new PaasRuntimeException("You do not own the lock: " + key);
	        }

	        int newLockCount = lockData.lockCount.decrementAndGet();
	        if ( newLockCount > 0 )
	        {
	            return;
	        }
	        if ( newLockCount < 0 )
	        {
	            throw new PaasRuntimeException("Lock count has gone negative for lock: " + key);
	        }
	        
			List<String> list = new ArrayList<String>();
			list.add(key);
			unLock(list);
		}

		/**
		 * 批量获取锁  如果全部获取   立即返回true, 部分获取失败 返回false
		 * @author gucl
		 * @param keyList
		 * @return
		 */
		/*public boolean tryLock(List<String> keyList) {
			return tryLock(keyList, 0L, null);
		}*/
		
		/**
		 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
		 * @author gucl
		 * @param keyList
		 * @param timeout
		 * @param unit
		 * @return
		 */
		/*public boolean tryLock(List<String> keyList, long timeout, TimeUnit unit) {
			Jedis jedis = null;
			try {
				List<String> needLocking = new CopyOnWriteArrayList<String>();	
				List<String> locked = new CopyOnWriteArrayList<String>();	
				jedis = getResource();
				long nano = System.nanoTime();
				do {
					// 构建pipeline，批量提交
					Pipeline pipeline = jedis.pipelined();
					for (String key : keyList) {
						needLocking.add(key);
						pipeline.setnx(key, key);
					}
					LOGGER.debug("try lock keys: " + needLocking);
					// 提交redis执行计数
					List<Object> results = pipeline.syncAndReturnAll();
					for (int i = 0; i < results.size(); ++i) {
						Long result = (Long) results.get(i);
						String key = needLocking.get(i);
						if (result == 1) {	// setnx成功，获得锁
							jedis.expire(key, DEFAULT_BATCH_EXPIRE_TIME);
							locked.add(key);
						} 
					}
					needLocking.removeAll(locked);	// 已锁定资源去除
					
					if (CollectionUtils.isEmpty(needLocking)) {
						return true;
					} else {	
						// 部分资源未能锁住
						LOGGER.debug("keys: " + needLocking + " locked by another business：");
					}
					
					if (timeout == 0) {	
						break;
					}
					Thread.sleep(500);	
				} while ((System.nanoTime() - nano) < unit.toNanos(timeout));

				// 得不到锁，释放锁定的部分对象，并返回失败
				if (!CollectionUtils.isEmpty(locked)) {
					jedis.del(locked.toArray(new String[0]));
				}
				return false;
			} catch (JedisConnectionException je) {
				LOGGER.error(je.getMessage(), je);
				returnBrokenResource(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				returnResource(jedis);
			}
			return true;
		}
*/
		/**
		 * 批量释放锁
		 * @author gucl
		 * @param keyList
		 */
		public void unLock(List<String> keyList) {
			List<String> keys = new CopyOnWriteArrayList<String>();
			for (String key : keyList) {
				keys.add(key);
			}
			Jedis jedis = null;
			try {
				jedis = getResource();
				jedis.del(keys.toArray(new String[0]));
				LOGGER.debug("release lock, keys :" + keys);
			} catch (JedisConnectionException je) {
				LOGGER.error(je.getMessage(), je);
				returnBrokenResource(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				returnResource(jedis);
			}
		}
		
		/**
		 * @author gucl
		 * @return
		 */
		private Jedis getResource() {
			return jedisPool.getResource();
		}
		
		/**
		 * 销毁连接
		 * @author gucl
		 * @param jedis
		 */
		private void returnBrokenResource(Jedis jedis) {
			if (jedis == null) {
				return;
			}
			try {
				//容错
				jedisPool.returnResourceObject(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		/**
		 * @author gucl
		 * @param jedis
		 */
		private void returnResource(Jedis jedis) {
			if (jedis == null) {
				return;
			}
			try {
				jedisPool.returnResourceObject(jedis);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		public boolean isAcquiredInThisProcess() {
			 return (threadData.size() > 0);
		}
		boolean isOwnedByCurrentThread()
	    {
	        LockData lockData = threadData.get(Thread.currentThread());
	        return (lockData != null) && (lockData.lockCount.get() > 0);
	    }
}