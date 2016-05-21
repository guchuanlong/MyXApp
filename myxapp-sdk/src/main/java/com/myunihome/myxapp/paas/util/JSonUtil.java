package com.myunihome.myxapp.paas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class JSonUtil
{
  private static final transient Logger log = LoggerFactory.getLogger(JSonUtil.class);

  private static Gson gson = new Gson();

  public static String toJSon(Object obj)
  {
    String json = gson.toJson(obj);
    if (log.isInfoEnabled()) {
      log.info(obj + " trasform into json:" + json);
    }
    return json;
  }

  public static <T> T fromJSon(String json, Class<T> clazz) {
    T t = gson.fromJson(json, clazz);
    if (log.isInfoEnabled()) {
      log.info(json + " trasform into class:" + clazz + ",object:" + t);
    }
    return t;
  }
}