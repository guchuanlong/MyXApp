package com.myunihome.myxapp.paas.constants;

public class MyXAppPaaSConstant
{
  public static final String CHARSET_UTF8 = "UTF-8";
  public static final String CHARSET_GBK = "GBK";
  public static final String UNIX_SEPERATOR = "/";
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String TAB = "\t";
  public static final String DEFAULT = "default";

  public static final class ExceptionCode
  {
	// 处理成功[业务处理成功]
      public static final String SUCCESS = "000000";
      // 系统级异常[其它系统级异常，未知异常]
      public static final String SYSTEM_ERROR = "999999";
      //用户授权失败
      public static final String USER_AUTH_ERROR = "000001";
      //传入的参数为空
      public static final String PARAM_IS_NULL = "000002";
      //参数类型不正确
      public static final String PARAM_TYPE_NOT_RIGHT = "000003";
      //参数取值不正确
      public static final String PARAM_VALUE_NOT_RIGHT = "000004";        
      // 未查询到记录[查询无记录]
      public static final String NO_RESULT = "000005";
      // 未配置系统参数或未刷新缓存
      public static final String NO_DATA_OR_CACAE_ERROR = "000006";
  }
}