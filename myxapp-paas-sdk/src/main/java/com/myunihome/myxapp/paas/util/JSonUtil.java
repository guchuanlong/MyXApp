package com.myunihome.myxapp.paas.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;

public class JSonUtil
{
  private static final transient Log log = LogFactory.getLog(JSonUtil.class);

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