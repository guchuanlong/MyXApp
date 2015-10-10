package com.myunihome.myxapp.paas.util;

public class Base64Util
{
  private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String encode(byte[] data)
  {
    int l = data.length;

    char[] out = new char[l << 1];

    int i = 0; for (int j = 0; i < l; i++) {
      out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
      out[(j++)] = DIGITS[(0xF & data[i])];
    }
    return new String(out);
  }
}