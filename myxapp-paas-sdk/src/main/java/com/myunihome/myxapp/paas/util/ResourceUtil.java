package com.myunihome.myxapp.paas.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myunihome.myxapp.paas.i18n.MultiResourceBundle;

public class ResourceUtil
{
  private static final transient Log log = LogFactory.getLog(ResourceUtil.class);

  private static ResourceBundle rb = null;
  private static Locale locale = null;

  public static void addBundle(String baseName, Locale locale)
  {
    try
    {
      if (log.isInfoEnabled()) {
        log.info(new StringBuilder().append("Add localization file:").append(baseName).append(",locale:").append(locale).append(" to i18n!").toString());
      }

      rb = MultiResourceBundle.getBundle(baseName, locale);
    }
    catch (Exception e) {
    }
  }

  public static void addBundle(String baseName) {
    try {
      if (log.isInfoEnabled()) {
        log.info(new StringBuilder().append("Add localization file:").append(baseName).append(",locale:").append(locale).append(" to i18n!").toString());
      }

      rb = MultiResourceBundle.getBundle(baseName, locale);
    }
    catch (Exception e) {
    }
  }

  public static String getMessage(String key, String[] params) {
    String message = getMessage(key);
    if ((null != params) && (params.length > 0)) {
      StringBuilder tokens = new StringBuilder("\\{[");
      for (int i = 0; i < params.length; i++) {
        tokens.append(i).append("|");
      }
      tokens.deleteCharAt(tokens.length() - 1);
      tokens.append("]\\}");
      Pattern pattern = Pattern.compile(tokens.toString());
      Matcher matcher = pattern.matcher(message);
      StringBuffer sb = new StringBuffer();
      int i = 0;
      while (matcher.find()) {
        matcher.appendReplacement(sb, params[i]);
        i++;
      }
      matcher.appendTail(sb);
      message = sb.toString();
    }
    return message;
  }

  public static String getMessage(String key) {
    try {
      return rb.getString(key);
    } catch (Exception e) {
    }
    return key;
  }

  public static void main(String[] args)
  {
    System.out.println(getMessage("com.ai.paas.ipaas.common.srvid_null"));

    String[] values = { "sss", "ssssd", "ggggg", "hhhhh", "iiiiiiii" };
    System.out.println(getMessage("com.ai.paas.ipaas.common.test", values));
  }

  static
  {
    if (null != System.getProperty("user.locale"))
      locale = new Locale(System.getProperty("user.locale"));
    else {
      locale = Locale.CHINA;
    }
    rb = MultiResourceBundle.getBundle("com.gucl.myapp.paas.ipaas.common.ipaas-common", locale);
  }
}