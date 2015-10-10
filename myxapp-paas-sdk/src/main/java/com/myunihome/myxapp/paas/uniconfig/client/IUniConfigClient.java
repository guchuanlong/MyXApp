package com.myunihome.myxapp.paas.uniconfig.client;

import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ConfigWatcher;

public interface IUniConfigClient {
    String get(String path);
    String get(String path, ConfigWatcher configWatcher) throws UniConfigException;
    void add(String path, String config);
    void modify(String path, String config);
    boolean exists(String path);
}
