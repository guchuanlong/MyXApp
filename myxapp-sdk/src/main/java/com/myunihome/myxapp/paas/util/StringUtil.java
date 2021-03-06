package com.myunihome.myxapp.paas.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  public static boolean isBlank(String str)
  {
    if (null == str) {
      return true;
    }

    return "".equals(str.trim());
  }

  public static String toString(Object obj)
  {
    if (obj == null) {
      return "";
    }
    return obj.toString();
  }

  public static String trim(String strSrc, int iMaxLength) {
    if (strSrc == null) {
      return null;
    }
    if (iMaxLength <= 0) {
      return strSrc;
    }
    String strResult = strSrc;
    byte[] b = null;
    int iLength = strSrc.length();
    if (iLength > iMaxLength) {
      strResult = strResult.substring(0, iMaxLength);
      iLength = iMaxLength;
    }
    while (true) {
      b = strResult.getBytes();
      if (b.length <= iMaxLength) {
        break;
      }
      iLength--;
      strResult = strResult.substring(0, iLength);
    }
    return strResult;
  }

  public static String genRandom(int length) {
    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < length; i++) {
      int num = random.nextInt(str.length());
      buf.append(str.charAt(num));
    }

    return buf.toString();
  }

  public static String lPad(String target, String fix, int length)
  {
    if ((target == null) || (fix == null) || (target.length() >= length))
      return target;
    StringBuffer newStr = new StringBuffer();
    for (int i = 0; i < length - target.length(); i++) {
      newStr.append(fix);
    }
    return newStr.append(target).toString();
  }

  public static String rPad(String target, String fix, int length)
  {
    if ((target == null) || (fix == null) || (target.length() >= length))
      return target;
    StringBuffer newStr = new StringBuffer();
    newStr.append(target);
    for (int i = 0; i < length - target.length(); i++) {
      newStr.append(fix);
    }
    return newStr.toString();
  }

  public static String join(String[] strs, String spi)
  {
    StringBuffer buf = new StringBuffer();
    int step = 0;
    for (String str : strs) {
      buf.append(str);
      if (step++ < strs.length - 1)
        buf.append(spi);
    }
    return buf.toString();
  }

  public static String toString(Object obj, String defaultStr)
  {
    if (obj == null) {
      return defaultStr;
    }
    return obj.toString();
  }

  public static String replace(String str, String search, String replace)
  {
    String dest = "";
    if (str != null) {
      Pattern p = Pattern.compile(search);
      Matcher m = p.matcher(str);
      dest = m.replaceAll(replace);
    }
    return dest;
  }

  public static boolean checkSrcExist(String des, String splitChar, String src)
  {
    if ((src == null) || (src.length() == 0)) {
      return false;
    }
    if ((des == null) || (des.length() == 0)) {
      return false;
    }
    String[] tmps = des.split(splitChar);
    for (String tmp : tmps) {
      if (src.equalsIgnoreCase(tmp)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args)
  {
    System.out.println(checkSrcExist("2", ",", "5"));

    Pattern p1 = Pattern.compile("^1\\d{4}(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}\\d$");

    Matcher m1 = p1.matcher("18602456789");
    System.out.println("ABCDEF---" + m1.find());

    Pattern p2 = Pattern.compile("^1\\d{4}([\\d])\\1{5,}$");
    Matcher m2 = p2.matcher("18602888888");
    System.out.println("AAAAAA---" + m2.find());

    Pattern p3 = Pattern.compile("^1\\d{4}([\\d])\\1{2,}([\\d])\\2{2,}$");
    Matcher m3 = p3.matcher("18602888999");
    System.out.println("AAABBB---" + m3.find());

    Pattern p4 = Pattern.compile("^1\\d{4}(\\d\\d)\\1{2,}$");
    Matcher m4 = p4.matcher("18602565656");
    System.out.println("ABABAB---" + m4.find());

    Pattern p5 = Pattern.compile("^1\\d{6}(\\d)\\1{1,}(\\d)\\2{1,}$");
    Matcher m5 = p5.matcher("18602516677");
    System.out.println("AABB---" + m5.find());
  }
}