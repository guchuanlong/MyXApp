package com.myunihome.myxapp.paas.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util
{
  public static String encode(String rawPass)
  {
    MessageDigest messageDigest = getMessageDigest();

    byte[] digest = messageDigest.digest(rawPass.getBytes());

    return Base64Util.encode(digest).toLowerCase();
  }

  protected static final MessageDigest getMessageDigest() throws IllegalArgumentException
  {
    try {
      return MessageDigest.getInstance("MD5"); } catch (NoSuchAlgorithmException e) {
    }
    throw new IllegalArgumentException("No such algorithm [MD5]");
  }

  public static boolean isPasswordValid(String encPass, String rawPass)
  {
    String pass1 = "" + encPass;
    String pass2 = encode(rawPass);

    return pass1.equals(pass2);
  }

  public static void main(String[] args) {
    String result = encode("123456");
    System.out.println("password=" + result);

    boolean isGood = isPasswordValid("E10ADC3949BA59ABBE56E057F20F883E", "111111");

    System.out.println("isGood=" + isGood);
  }
}