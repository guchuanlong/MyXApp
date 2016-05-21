package com.myunihome.myxapp.paas.util;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;  
  
public class EncrypDES {  
      
    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法  
    private KeyGenerator keygen;  
    //SecretKey 负责保存对称密钥  
    private SecretKey deskey;  
    //Cipher负责完成加密或解密工作  
    private Cipher c;  
    //该字节数组负责保存加密的结果  
    private byte[] cipherByte;  
      
    public EncrypDES() throws NoSuchAlgorithmException, NoSuchPaddingException{  
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)  
        keygen = KeyGenerator.getInstance("DES");  
        //生成密钥  
        deskey = keygen.generateKey();  
        //生成Cipher对象,指定其支持的DES算法  
        c = Cipher.getInstance("DES");  
    }  
      
    /** 
     * 对字符串加密 
     *  
     * @param str 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public byte[] Encrytor(String str) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式  
        c.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] src = str.getBytes();  
        // 加密，结果保存进cipherByte  
        cipherByte = c.doFinal(src);  
        return cipherByte;  
    }  
  
    /** 
     * 对字符串解密 
     *  
     * @param buff 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public byte[] Decryptor(byte[] buff) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
        c.init(Cipher.DECRYPT_MODE, deskey);  
        cipherByte = c.doFinal(buff);  
        return cipherByte;  
    }  
  
    /** 
     * @param args 
     * @throws NoSuchPaddingException  
     * @throws NoSuchAlgorithmException  
     * @throws BadPaddingException  
     * @throws IllegalBlockSizeException  
     * @throws InvalidKeyException  
     */  
    public static void main(String[] args) throws Exception {  
        EncrypDES de1 = new EncrypDES();  
        String msg ="[{\"property_id\":102,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":1,\"support_order_type\":1,\"is_display\":1,\"order_level\":1,\"property_name\":\"渠道编码\",\"property_code\":\"CHNL_CODE\"},{\"property_id\":103,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":2,\"support_order_type\":2,\"is_display\":1,\"property_name\":\"渠道名称\",\"property_code\":\"CHNL_NAME\"},{\"property_id\":121,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":4,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道级别\",\"property_code\":\"CHNL_LEVEL\"},{\"property_id\":105,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":5,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道状态\",\"property_code\":\"STATE\"},{\"property_id\":101,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":6,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道ID\",\"property_code\":\"CHNL_ID\"},{\"property_id\":126,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":17,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"地域类型\",\"property_code\":\"CHNL_AREA_KIND_ID\"},{\"property_id\":145,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":21,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"管理部门\",\"property_code\":\"manager_dept_id\"},{\"property_id\":146,\"custom_type\":1,\"translate_type\":1,\"display_type\":1,\"display_order\":22,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道管理员\",\"property_code\":\"manager_staff_id\"},{\"property_id\":107,\"custom_type\":2,\"translate_type\":1,\"display_type\":1,\"display_order\":3,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道类型\",\"property_code\":\"CHNL_KIND_ID\"},{\"property_id\":117,\"custom_type\":2,\"translate_type\":1,\"display_type\":1,\"display_order\":13,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道所属省分\",\"property_code\":\"PROVINCE_CODE\"},{\"property_id\":118,\"custom_type\":2,\"translate_type\":1,\"display_type\":1,\"display_order\":14,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道所属地市\",\"property_code\":\"CITY_CODE\"},{\"property_id\":119,\"custom_type\":2,\"translate_type\":1,\"display_type\":1,\"display_order\":15,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"归属区域\",\"property_code\":\"MANAGER_AREA_CODE\"},{\"property_id\":280,\"custom_type\":2,\"translate_type\":2,\"translate_detail\":\"select depart_name from td_m_depart where depart_id=t1.manager_dept_id\",\"display_type\":1,\"display_order\":32,\"support_order_type\":1,\"is_display\":1,\"property_name\":\"渠道管理部门\",\"property_code\":\"manager_dept_id\"}]";  
        byte[] encontent = de1.Encrytor(msg);  
        byte[] decontent = de1.Decryptor(encontent);  
        System.out.println("明文是:" + msg);  
        System.out.println("加密后:" + new String(encontent));  
        System.out.println("解密后:" + new String(decontent));  
    }  
  
} 