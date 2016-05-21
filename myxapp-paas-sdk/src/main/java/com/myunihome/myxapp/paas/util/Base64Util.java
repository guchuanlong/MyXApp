package com.myunihome.myxapp.paas.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
  
  
  
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	public static void main(String[] args) throws Exception {
		String ming="accountId=1&password=ec4c9e0e78f76a69ec4c9e0e78f76a69";
		String mi=encryptBASE64(ming.getBytes());
		String ming2=String.valueOf(decryptBASE64(mi));
		System.out.println("ming="+ming);
		System.out.println("mi="+mi);
		System.out.println("ming2="+ming2);
	}

}