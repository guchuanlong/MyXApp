package com.myunihome.myxapp.paas.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CiperUtil
{
  private static final transient Logger log = LoggerFactory.getLogger(CiperUtil.class);
  public static final String KEY_ALGORITHM = "DES";
  public static final String DES_ECB_ALGORITHM = "DES/ECB/PKCS5Padding";
  public static final String DES_CBC_ALGORITHM = "DES/CBC/PKCS5Padding";
  public static final String DES_CBC_NOPADDING = "DES/CBC/NoPadding";
  public static String SECURITY_KEY = "byjsy7!@#bjwqt7!";

  public static final byte[] DES_CBC_IV = { 0, 0, 0, 0, 0, 0, 0, 0 };

  public static void setSECURITY_KEY(String sECURITY_KEY)
  {
    SECURITY_KEY = sECURITY_KEY;
  }

  public static byte[] genSecretKey() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
      keyGenerator.init(56);
      SecretKey secretKey = keyGenerator.generateKey();
      return AsciiUtil.hex2Ascii(secretKey.getEncoded());
    } catch (Exception e) {
      log.error("exception:", e);
    }
    return null;
  }

  private static Key toKey(byte[] key) {
    try {
      DESKeySpec des = new DESKeySpec(key);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);

      SecretKey secretKey = keyFactory.generateSecret(des);
      return secretKey;
    } catch (Exception e) {
      log.error("exception:", e);
    }
    return null;
  }

  public static byte[] encrypt(byte[] data, byte[] key, String algorithm) {
    try {
      Key k = toKey(key);
      Cipher cipher = Cipher.getInstance(algorithm);
      if ((DES_CBC_ALGORITHM.equals(algorithm)) || (DES_CBC_NOPADDING.equals(algorithm)))
      {
        IvParameterSpec spec = new IvParameterSpec(DES_CBC_IV);
        cipher.init(1, k, spec);
      } else {
        cipher.init(1, k);
      }
      return cipher.doFinal(data);
    } catch (Exception e) {
      log.error("exception:", e);
    }
    return null;
  }

  public static byte[] decrypt(byte[] data, byte[] key, String algorithm) {
    try {
      Key k = toKey(key);
      Cipher cipher = Cipher.getInstance(algorithm);
      if ((DES_CBC_ALGORITHM.equals(algorithm)) || (DES_CBC_NOPADDING.equals(algorithm)))
      {
        IvParameterSpec spec = new IvParameterSpec(DES_CBC_IV);
        cipher.init(2, k, spec);
      } else {
        cipher.init(2, k);
      }
      return cipher.doFinal(data);
    } catch (Exception e) {
      log.error("exception:", e);
    }
    return null;
  }

  public static String encrypt(String securityKey, String data) {
    byte[] aa = encrypt(data.getBytes(), AsciiUtil.ascii2Hex(securityKey.getBytes()), DES_ECB_ALGORITHM);

    return new String(AsciiUtil.hex2Ascii(aa));
  }

  public static String decrypt(String securityKey, String data) {
    byte[] aa = AsciiUtil.ascii2Hex(data.getBytes());
    return new String(decrypt(aa, AsciiUtil.ascii2Hex(securityKey.getBytes()), DES_ECB_ALGORITHM));
  }

  public static String encrypt(String securityKey, String data, String algorithm)
  {
    byte[] aa = encrypt(data.getBytes(), AsciiUtil.ascii2Hex(securityKey.getBytes()), algorithm);

    return new String(AsciiUtil.hex2Ascii(aa));
  }

  public static String decrypt(String securityKey, String data, String algorithm)
  {
    byte[] aa = AsciiUtil.ascii2Hex(data.getBytes());
    return new String(decrypt(aa, AsciiUtil.ascii2Hex(securityKey.getBytes()), algorithm));
  }

  public static byte[] paddingZero(byte[] in)
  {
    if ((in == null) || (in.length == 0)) {
      return null;
    }
    int inLen = in.length;
    int m = inLen % 8;
    byte[] out = null;
    if (m == 0)
      out = new byte[inLen];
    else {
      out = new byte[inLen + 8 - m];
    }
    int outLen = out.length;
    for (int i = 0; i < outLen; i++) {
      if (i < inLen)
        out[i] = in[i];
      else {
        out[i] = 0;
      }
    }
    return out;
  }

  public static void main(String[] args)
  {
    String bb = decrypt("1@3^$aGH;._|$!@#", "ec4c9e0e78f76a69");
    String bb2 = decrypt(SECURITY_KEY, "47e396b90225d8abb68c3cf029529c3e4a59efe425440cbc88a599bddbb025651baa86637a3d840a88a599bddbb025654b2ed7a3423f0e6f");
    System.out.println("bb:" + bb);
    System.out.println("bb2:" + bb2);
    System.out.println(encrypt(SECURITY_KEY, "admin"));
    System.out.println(encrypt(SECURITY_KEY, "accountId=1&password=ec4c9e0e78f76a69ec4c9e0e78f76a69"));
  }
}