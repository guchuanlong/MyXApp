package com.myunihome.myxapp.paas.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellUtil
{
  private static final transient Log log = LogFactory.getLog(ShellUtil.class);

  public static boolean execCommand(String[] cmd) {
    if (log.isDebugEnabled()) {
      String cmdStr = "";
      for (int i = 0; i < cmd.length; i++) {
        cmdStr = cmdStr + " " + cmd[i];
      }
      log.debug(cmdStr);
    }
    try {
      Process process = Runtime.getRuntime().exec(cmd);

      return process.waitFor() == 0;
    }
    catch (Exception e)
    {
      log.error("", e);
    }return false;
  }

  public static boolean execCommand(String cmd)
  {
    if (log.isDebugEnabled())
      log.debug(cmd);
    try
    {
      Process process = Runtime.getRuntime().exec(cmd);

      return process.waitFor() == 0;
    }
    catch (Exception e)
    {
      log.error("", e);
    }return false;
  }

  public static void main(String[] args)
  {
    String[] cmd = { "keytool", "-genkey", "-validity", "36500", "-keysize", "1024", "-alias", "ss", "-keyalg", "RSA", "-keystore", "/Volumes/HD/Downloads/zjy.keystore", "-dname", "CN=(SS),OU=(SS),O=(SS),L=(BJ),ST=(BJ),C=(CN)", "-storepass", "123456", "-keypass", "123456", "-v" };

    execCommand(cmd);
  }
}