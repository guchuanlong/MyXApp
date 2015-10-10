package com.myunihome.myxapp.paas.util;

public abstract class Assert
{
  public static void notNull(Object obj, String message)
  {
    if (obj == null)
      throw new IllegalArgumentException(message);
    if (((obj instanceof String)) && (StringUtil.isBlank(obj.toString())))
      throw new IllegalArgumentException(message);
  }
}