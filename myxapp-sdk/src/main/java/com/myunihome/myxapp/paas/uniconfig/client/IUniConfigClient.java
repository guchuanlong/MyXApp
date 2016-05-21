package com.myunihome.myxapp.paas.uniconfig.client;

import java.util.List;

import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ConfigWatcher;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.MutexLock;
import com.myunihome.myxapp.paas.uniconfig.zookeeper.ZKAddMode;

public interface IUniConfigClient {
	MutexLock getMutexLock(String path) throws UniConfigException;
    String get(String path) throws UniConfigException;
    String get(String path, ConfigWatcher configWatcher) throws UniConfigException;
    byte[] readBytes(String path) throws UniConfigException;
    byte[] readBytes(String path, ConfigWatcher configWatcher) throws UniConfigException;
    void add(String path, String config) throws UniConfigException;
    void add(String path, String config, ZKAddMode zkAddMode) throws UniConfigException;
    void add(String path, byte[] bytes) throws UniConfigException;
	void add(String path, byte[] bytes, ZKAddMode zkAddMode) throws UniConfigException;
    void modify(String path, String config) throws UniConfigException;
    void modify(String path, byte[] bytes) throws UniConfigException;
    boolean exists(String path) throws UniConfigException;
    boolean exists(String path, ConfigWatcher configWatcher) throws UniConfigException;
    List<String> listSubPath(String path) throws UniConfigException;
	List<String> listSubPath(String path, ConfigWatcher configWatcher)	throws UniConfigException;
	void remove(String path) throws UniConfigException;
}
