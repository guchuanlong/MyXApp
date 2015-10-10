package com.myunihome.myxapp.paas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtil
{
  public static String sendGet(String url, String param)
    throws Exception
  {
    String result = "";
    BufferedReader in = null;
    HttpURLConnection conn = null;
    try {
      String urlNameString = url + "?" + param;
      URL realUrl = new URL(urlNameString);

      conn = (HttpURLConnection)realUrl.openConnection();
      conn.setConnectTimeout(10000);
      conn.setReadTimeout(5000);

      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      conn.connect();

      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      throw e;
    }
    finally
    {
      try {
        if (in != null)
          in.close();
      }
      catch (Exception e2) {
        e2.printStackTrace();
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
    return result;
  }

  public static String sendPost(String url, String param)
    throws RuntimeException
  {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    HttpURLConnection conn = null;
    try {
      URL realUrl = new URL(url);

      conn = (HttpURLConnection)realUrl.openConnection();
      conn.setConnectTimeout(10000);
      conn.setReadTimeout(5000);

      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      conn.setDoOutput(true);
      conn.setDoInput(true);

      out = new PrintWriter(conn.getOutputStream());

      out.print(param);

      out.flush();

      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
    finally
    {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
    return result;
  }

  public static void main(String[] args)
  {
    try
    {
      String sr = sendPost("http://10.1.228.198:14821/iPaas-UAC/service/queryUserIdByUserName", "userName=mvne@asiainfo.com");

      System.out.println(sr);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}