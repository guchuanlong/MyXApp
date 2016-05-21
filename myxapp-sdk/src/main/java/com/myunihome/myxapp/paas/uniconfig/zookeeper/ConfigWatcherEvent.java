package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 配置监视事件
 *
 * Date: 2015年10月1日 <br>
 * Copyright (c) 2015 guchuanlong@126.com <br>
 * @author gucl
 */
@SuppressWarnings("unused")
public class ConfigWatcherEvent
{
  private KeeperState keeperState;
  private EventType eventType;
  private String path;
  private WatchedEvent event;

  public ConfigWatcherEvent(EventType eventType, KeeperState keeperState, String path)
  {
    this.keeperState = keeperState;
    this.eventType = eventType;
    this.path = path;
  }

  public ConfigWatcherEvent(WatchedEvent event) {
    this.event = event;
    this.keeperState = KeeperState.fromInt(event.getState().getIntValue());
    this.eventType = EventType.fromInt(event.getType().getIntValue());
    this.path = event.getPath();
  }

  public ConfigWatcher.Event.KeeperState getState() {
    return ConfigWatcher.Event.KeeperState.fromInt(this.event.getState().getIntValue());
  }

  public ConfigWatcher.Event.EventType getType() {
    return ConfigWatcher.Event.EventType.fromInt(this.event.getType().getIntValue());
  }

  public String getPath() {
    return this.event.getPath();
  }

  public ConfigWatcherEvent getWrapper() {
    return new ConfigWatcherEvent(EventType.fromInt(this.event.getType().getIntValue()), KeeperState.fromInt(this.event.getState().getIntValue()), this.event.getPath());
  }
}