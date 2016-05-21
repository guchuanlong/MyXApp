package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
/**
 * 配置监视者
 *
 * Date: 2015年10月1日 <br>
 * Copyright (c) 2015 guchuanlong@126.com <br>
 * @author gucl
 */
public abstract class ConfigWatcher implements Watcher
{
  public abstract void processEvent(ConfigWatcherEvent paramConfigWatcherEvent);

  public void process(WatchedEvent event)
  {
    processEvent(new ConfigWatcherEvent(event));
  }

  public static abstract interface Event
  {
    public static enum KeeperState
    {
      Disconnected(0), SyncConnected(3), AuthFailed(4), ConnectedReadOnly(5), SaslAuthenticated(6), Expired(-112);

      private final int intValue;

      private KeeperState(int intValue) { this.intValue = intValue; }

      public int getIntValue()
      {
        return this.intValue;
      }

      public static KeeperState fromInt(int intValue) {
        switch (intValue) {
        case 0:
          return Disconnected;
        case 3:
          return SyncConnected;
        case 4:
          return AuthFailed;
        case 5:
          return ConnectedReadOnly;
        case 6:
          return SaslAuthenticated;
        case -112:
          return Expired;
        }

        throw new RuntimeException("Invalid integer value for conversion to KeeperState");
      }
    }

    public static enum EventType
    {
      None(-1), 
      NodeCreated(1), 
      NodeDeleted(2), 
      NodeDataChanged(3), 
      NodeChildrenChanged(4);

      private final int intValue;

      private EventType(int intValue) { this.intValue = intValue; }

      public int getIntValue()
      {
        return this.intValue;
      }

      public static EventType fromInt(int intValue) {
        switch (intValue) {
        case -1:
          return None;
        case 1:
          return NodeCreated;
        case 2:
          return NodeDeleted;
        case 3:
          return NodeDataChanged;
        case 4:
          return NodeChildrenChanged;
        case 0:
        }
        throw new RuntimeException("Invalid integer value for conversion to EventType");
      }
    }
  }
}