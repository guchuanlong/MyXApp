package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
/**
 * 分布式锁
 *
 * Date: 2015年10月1日 <br>
 * Copyright (c) 2015 guchuanlong@126.com <br>
 * @author gucl
 */
public class MutexLock
{
  private InterProcessLock processLock;

  public MutexLock(InterProcessLock processLock)
  {
    this.processLock = processLock;
  }

  public void acquire() throws Exception {
    this.processLock.acquire();
  }

  public boolean acquire(long time, TimeUnit unit) throws Exception {
    return this.processLock.acquire(time, unit);
  }

  public void release() throws Exception {
    this.processLock.release();
  }

  public boolean isAcquiredInThisProcess() {
    return this.processLock.isAcquiredInThisProcess();
  }
}