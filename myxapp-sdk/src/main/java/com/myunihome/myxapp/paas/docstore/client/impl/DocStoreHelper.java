package com.myunihome.myxapp.paas.docstore.client.impl;

import com.mongodb.ServerAddress;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class DocStoreHelper
{
  public static List<ServerAddress> Str2ServerAddressList(String hosts)
  {
    String[] hostsArray = hosts.split(",");
    List<ServerAddress> saList = new ArrayList<ServerAddress>();
    String[] address = null;
    for (String host : hostsArray) {
      address = host.split(":");
      try {
        saList.add(new ServerAddress(address[0], Integer.parseInt(address[1])));
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return saList;
  }

  public static String getFileType(String fileName) {
    String[] fileInfo = fileName.split("\\.");
    return fileInfo[1];
  }

  public static long getFileSize(File file) {
    return Long.parseLong(file.length() + "");
  }

  public static long getFileSize(byte[] file) {
    return Long.parseLong(file.length + "");
  }

  public static long okSize(long a, long b) {
    return Long.parseLong(a - b + "");
  }

  public static byte[] toByteArray(InputStream input)
    throws Exception
  {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
    return output.toByteArray();
  }

  public static double byte2M(Long size) {
    BigDecimal bd = new BigDecimal(size.longValue());
    return bd.divide(new BigDecimal(1048576), 3, 4).doubleValue();
  }

  public static long M2byte(double size)
  {
    BigDecimal sizeBD = new BigDecimal(size);
    double result = sizeBD.multiply(new BigDecimal(1048576)).doubleValue();

    return Math.round(result);
  }

  public static JedisCluster getRedis(String redisHost) {
    Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    for (String address : redisHost.split(",")) {
      String[] ipAndPort = address.split(":");
      jedisClusterNodes.add(new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
    }

    JedisCluster jc = new JedisCluster(jedisClusterNodes);
    return jc;
  }
}