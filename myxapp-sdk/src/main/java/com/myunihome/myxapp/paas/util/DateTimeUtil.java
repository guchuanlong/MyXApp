package com.myunihome.myxapp.paas.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtil
{
  public static String getSeasonTimeInterval(int year, int season)
  {
    String time = null;
    switch (season) {
    case 1:
      time = year + "0101-" + year + "0331";
      break;
    case 2:
      time = year + "0401-" + year + "0630";
      break;
    case 3:
      time = year + "0701-" + year + "0930";
      break;
    case 0:
    case 4:
      time = year + "1001-" + year + "1231";
      break;
    }

    return time;
  }

  public static int getCurrentSeasonTime(int month)
  {
    int season = 0;
    if ((month >= 1) && (month <= 3))
      season = 1;
    else if ((month >= 4) && (month <= 6))
      season = 2;
    else if ((month >= 7) && (month <= 9))
      season = 3;
    else if ((month >= 10) && (month <= 12)) {
      season = 4;
    }
    return season;
  }

  public static String getThisSeasonTime(int month)
  {
    String quarter = "";

    if ((month >= 1) && (month <= 3)) {
      quarter = "4";
    }

    if ((month >= 4) && (month <= 6)) {
      quarter = "1";
    }

    if ((month >= 7) && (month <= 9)) {
      quarter = "2";
    }

    if ((month >= 10) && (month <= 12)) {
      quarter = "3";
    }
    return quarter;
  }

  public static java.util.Date getDateTime(String dateTime)
  {
    java.util.Date strDate = java.sql.Date.valueOf(dateTime);
    return strDate;
  }

  public static int getMonth(String dateTime)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(getDateTime(dateTime));
    int month = c.get(2) + 1;
    return month;
  }

  public static int getYear(String dateTime)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(getDateTime(dateTime));
    int year = c.get(1);
    return year;
  }

  public static String dateDiff(java.util.Date date1, java.util.Date date2)
  {
    String diff = null;
    if ((null != date1) && (null != date2)) {
      Calendar cal1 = Calendar.getInstance(Locale.CHINA);
      Calendar cal2 = Calendar.getInstance(Locale.CHINA);
      cal1.setTime(date1);
      cal2.setTime(date2);
      Calendar tmpCal1 = Calendar.getInstance(Locale.CHINA);
      tmpCal1.clear();
      tmpCal1.set(1, cal1.get(1));
      tmpCal1.set(2, cal1.get(2));
      tmpCal1.set(5, cal1.get(5));
      Calendar tmpCal2 = Calendar.getInstance(Locale.CHINA);
      tmpCal2.clear();
      tmpCal2.set(1, cal2.get(1));
      tmpCal2.set(2, cal2.get(2));
      tmpCal2.set(5, cal2.get(5));

      diff = String.valueOf(Math.abs(tmpCal1.getTimeInMillis() - tmpCal2.getTimeInMillis()) / 86400000.0D);
    }

    return diff;
  }

  public static String dateDiff(String dateStr1, String dateStr2, String dateFmt)
    throws ParseException
  {
    String diff = null;
    String tmpFmt = dateFmt;
    if (null == tmpFmt)
      tmpFmt = "yyyy-MM-dd";
    if ((null != dateStr1) && (null != dateStr2) && (dateStr1.length() >= tmpFmt.length()) && (dateStr1.length() >= tmpFmt.length()))
    {
      String tmpStr1 = dateStr1.substring(0, tmpFmt.length());
      String tmpStr2 = dateStr2.substring(0, tmpFmt.length());
      SimpleDateFormat sdf = new SimpleDateFormat(tmpFmt);
      java.util.Date date1 = sdf.parse(tmpStr1);
      java.util.Date date2 = sdf.parse(tmpStr2);
      diff = dateDiff(date1, date2);
    }
    return diff;
  }

  public static boolean isLeap(java.util.Date date)
  {
    boolean isLeap = false;
    if (null != date) {
      Calendar cal = Calendar.getInstance(Locale.CHINA);
      cal.setTime(date);
      int year = cal.get(1);
      if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
        isLeap = true;
    }
    return isLeap;
  }

  public static boolean isLeap(String dateStr, String dateFmt)
  {
    boolean isLeap = false;
    String tmpFmt = dateFmt;
    if (null == tmpFmt)
      tmpFmt = "yyyy-MM-dd";
    if ((null != dateStr) && (dateStr.length() >= tmpFmt.length())) {
      try {
        String tmpStr = dateStr.substring(0, tmpFmt.length());
        SimpleDateFormat sdf = new SimpleDateFormat(tmpFmt);
        java.util.Date date = sdf.parse(tmpStr);
        isLeap = isLeap(date);
      } catch (ParseException pe) {
        isLeap = false;
      }
    }
    return isLeap;
  }

  public static int getWorkDay()
  {
    int ret = -1;
    java.util.Date date = null;

    date = new java.util.Date();
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(date);
    ret = cal.get(7);
    return ret;
  }

  public static int getWorkDay(int diffDays, java.util.Date date)
  {
    int ret = -1;

    java.util.Date tmpDate = null;

    if (null == date)
      tmpDate = new java.util.Date();
    else
      tmpDate = date;
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(tmpDate);
    cal.add(5, diffDays);
    ret = cal.get(7);
    return ret;
  }

  public static int getWorkDay(int diffDays, String dateStr)
    throws ParseException
  {
    return getWorkDay(diffDays, dateStr, null);
  }

  public static int getWorkDay(int diffDays, String dateStr, String dateFmt)
    throws ParseException
  {
    int ret = -1;
    String dtFmt = "yyyy-MM-dd";
    java.util.Date inDate = null;
    if (null != dateFmt)
      dtFmt = dateFmt;
    if ((null != dateStr) && (dateStr.length() >= dtFmt.length())) {
      String tmpStr = dateStr.substring(0, dtFmt.length());
      SimpleDateFormat sdf = new SimpleDateFormat(dtFmt);
      inDate = sdf.parse(tmpStr);
      Calendar cal = Calendar.getInstance(Locale.CHINA);
      cal.setTime(inDate);
      cal.add(5, diffDays);
      ret = cal.get(7);
    }
    return ret;
  }

  public static String monthAdd(int diffMonths, String dateStr, String dateFmt)
    throws ParseException
  {
    String retDate = null;
    String dtFmt = "yyyy-MM-dd";
    java.util.Date inDate = null;
    if (null != dateFmt)
      dtFmt = dateFmt;
    if ((null != dateStr) && (dateStr.length() >= dtFmt.length())) {
      String tmpStr = dateStr.substring(0, dtFmt.length());
      SimpleDateFormat sdf = new SimpleDateFormat(dtFmt);
      inDate = sdf.parse(tmpStr);
      Calendar cal = Calendar.getInstance(Locale.CHINA);
      cal.setTime(inDate);
      cal.add(2, diffMonths);

      sdf = new SimpleDateFormat(dtFmt);
      retDate = sdf.format(cal.getTime());
    }
    return retDate;
  }

  public static String monthAdd(int diffMonths, String dateStr)
    throws ParseException
  {
    return monthAdd(diffMonths, dateStr, null);
  }

  public static String monthAdd(int diffMonths, java.util.Date date)
    throws ParseException
  {
    String dtFmt = "yyyy-MM-dd";
    if (null == date)
      date = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat(dtFmt);
    String dateStr = sdf.format(date);
    return monthAdd(diffMonths, dateStr, null);
  }

  public static String dateAdd(int diffDays, String dateStr, String dateFmt)
    throws ParseException
  {
    String retDate = null;
    String dtFmt = "yyyy-MM-dd";
    java.util.Date inDate = null;
    if (null != dateFmt)
      dtFmt = dateFmt;
    if ((null != dateStr) && (dateStr.length() >= dtFmt.length())) {
      String tmpStr = dateStr.substring(0, dtFmt.length());
      SimpleDateFormat sdf = new SimpleDateFormat(dtFmt);
      inDate = sdf.parse(tmpStr);
      Calendar cal = Calendar.getInstance(Locale.CHINA);
      cal.setTime(inDate);
      cal.add(5, diffDays);

      sdf = new SimpleDateFormat(dtFmt);
      retDate = sdf.format(cal.getTime());
    }
    return retDate;
  }

  public static String dateAdd(int diffDays, String dateStr)
    throws ParseException
  {
    return dateAdd(diffDays, dateStr, null);
  }

  public static String dateAdd(int diffDays, java.util.Date date)
    throws ParseException
  {
    String dtFmt = "yyyy-MM-dd";
    if (null == date)
      date = new java.util.Date();
    SimpleDateFormat sdf = new SimpleDateFormat(dtFmt);
    String dateStr = sdf.format(date);
    return dateAdd(diffDays, dateStr, null);
  }

  public static Timestamp getDefaultSysDate() throws Exception {
    Timestamp sysDate = new Timestamp(new java.util.Date().getTime());
    return sysDate;
  }

  public static Timestamp getNowTimeStamp()
  {
    java.util.Date date = new java.util.Date();
    Timestamp numTime = new Timestamp(date.getTime());
    return numTime;
  }

  public static java.sql.Date getNowDate()
  {
    java.util.Date date = new java.util.Date();
    return new java.sql.Date(date.getTime());
  }

  public static String timeStampToString(Timestamp date, String dateFmt)
  {
    String strTemp = null;
    if (date != null) {
      String dtFmt = dateFmt;
      if (null == dtFmt)
        dtFmt = "yyyy-MM-dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dtFmt);
      strTemp = formatter.format(date);
    }
    return strTemp;
  }

  public static String timeStampToString(Timestamp date)
  {
    return timeStampToString(date, null);
  }

  public static String dateToString(java.util.Date date, String dateFmt)
  {
    String strTemp = null;
    if (date != null) {
      String dtFmt = dateFmt;
      if (null == dtFmt)
        dtFmt = "yyyy-MM-dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dtFmt);
      strTemp = formatter.format(date);
    }
    return strTemp;
  }

  public static java.util.Date formatTimeString(String strTime)
  {
    String strTemp = null;
    if (strTime != null) {
      strTemp = strTime.substring(0, 4) + "-";
      strTemp = strTemp + strTime.substring(4, 6) + "-";
      strTemp = strTemp + strTime.substring(6, 8) + " ";
      strTemp = strTemp + strTime.substring(8, 10) + ":";
      strTemp = strTemp + strTime.substring(10, 12) + ":";
      strTemp = strTemp + strTime.substring(12, strTime.length());
    }
    try {
      return stringToDate(strTemp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String dateToString(java.sql.Date date)
  {
    return dateToString(date, null);
  }

  public static Timestamp stringToTimestamp(String strDate, String dateFmt)
    throws ParseException
  {
    if ((strDate != null) && (!strDate.equals(""))) {
      strDate = strDate.replaceAll("[\\u3000\\uA1A1\\u0020\\u00A0]", " ");

      String dtFmt = dateFmt;
      if (null == dtFmt)
        dtFmt = "yyyy-MM-dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dtFmt);
      java.util.Date d = formatter.parse(strDate);
      Timestamp numTime = new Timestamp(d.getTime());
      return numTime;
    }
    return null;
  }

  public static java.util.Date stringToDate(String strDate)
    throws ParseException
  {
    return stringToDate(strDate, null);
  }

  public static java.util.Date stringToDate(String strDate, String strFormat)
    throws ParseException
  {
    if ((strDate != null) && (!strDate.equals(""))) {
      String dtFmt = strFormat;
      if (null == dtFmt)
        dtFmt = "yyyy-MM-dd";
      SimpleDateFormat formatter = new SimpleDateFormat(dtFmt);
      java.util.Date d = formatter.parse(strDate);
      return d;
    }
    return null;
  }

  public static String dateTrans(String dateString)
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String toDate = "";
    try
    {
      java.util.Date date = df.parse(dateString);
      toDate = sdf.format(date);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return toDate;
  }

  public static int getCurrYear()
  {
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(getNowDate());
    return cal.get(1);
  }

  public static int getCurrMonth()
  {
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(getNowDate());
    return cal.get(2) + 1;
  }

  public static int getCurrDay()
  {
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(getNowDate());
    return cal.get(5);
  }

  public static int getCurMonthDayNumber(String strYear, String strMonth)
    throws NumberFormatException
  {
    int ret = -1;
    if ((Integer.parseInt(strMonth) > 0) && (Integer.parseInt(strMonth) <= 12)) {
      Calendar cal = Calendar.getInstance(Locale.CHINA);
      cal.clear();
      cal.set(new Integer(strYear).intValue(), new Integer(strMonth).intValue() - 1, 1);

      ret = cal.getActualMaximum(5);
    }
    return ret;
  }

  public static String lastMnthBegDate(String strDt)
    throws ParseException
  {
    java.util.Date dt = parse(strDt);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(2, -1);
    return format(cal.getTime());
  }

  public static String lastMnthBegDate(String strDt, String pattern)
    throws ParseException
  {
    java.util.Date dt = parse(strDt, pattern);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(2, -1);
    return format(cal.getTime());
  }

  public static String monthBegDate(String strDt, String pattern, int monthDiff)
    throws ParseException
  {
    java.util.Date dt = parse(strDt, pattern);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(2, monthDiff);
    return format(cal.getTime());
  }

  public static String lastMnthEndDate(String strDt)
    throws ParseException
  {
    java.util.Date dt = parse(strDt);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(13, -1);
    return format(cal.getTime());
  }

  public static String lastMnthEndDate(String strDt, String pattern)
    throws ParseException
  {
    java.util.Date dt = parse(strDt, pattern);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(13, -1);
    return format(cal.getTime());
  }

  public static String monthEndDate(String strDt, String pattern, int monthDiff)
    throws ParseException
  {
    java.util.Date dt = parse(strDt, pattern);
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setTime(dt);
    cal.set(5, 1);
    cal.set(10, 0);
    cal.set(12, 0);
    cal.set(13, 0);
    cal.add(2, monthDiff + 1);
    cal.add(13, -1);
    return format(cal.getTime());
  }

  public static String format(java.util.Date date)
  {
    return date == null ? "" : format(date, "yyyy-MM-dd");
  }

  public static String formatDateTime(java.util.Date date) {
    return format(date, "yyyy-MM-dd HH:mm:ss");
  }

  public static String format(java.util.Date date, String pattern)
  {
    return date == null ? "" : new SimpleDateFormat(pattern).format(date);
  }

  public static java.util.Date parse(String strDate)
    throws ParseException
  {
    return StringUtil.isBlank(strDate) ? null : parse(strDate, "yyyy-MM-dd");
  }

  public static java.util.Date parse(String strDate, String pattern)
    throws ParseException
  {
    if (StringUtil.isBlank(strDate))
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    sdf.setLenient(false);
    return sdf.parse(strDate);
  }

  public static String getLastMonth()
  {
    GregorianCalendar now = new GregorianCalendar();
    now.add(2, -1);
    return format(now.getTime(), "yyyyMM");
  }

  public static String formatDuring(long mss)
  {
    long days = mss / 86400000L;

    return days + "";
  }

  public static String formatTimeDuringHour(long mss)
  {
    long days = mss / 3600000L;
    return days + "";
  }

  public static String fomateTimeS2S(String oldTimeFomate, String oldFomate, String newFomate)
    throws ParseException
  {
    java.util.Date date = null;
    String dateTime = "";
    try {
      if ((!StringUtil.isBlank(oldTimeFomate)) && (!StringUtil.isBlank(oldFomate)) && (!StringUtil.isBlank(newFomate)))
      {
        date = parse(oldTimeFomate, oldFomate);
        dateTime = format(date, newFomate);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return dateTime;
  }

  public static void main(String[] args)
  {
  }

  public static boolean checkDate(String strDate, String dateFmt)
  {
    String eL = "";
    if ("yyyy-MM-dd".equals(dateFmt)) {
      eL = "^((\\d{2}(([02468][048])|([13579][26]))\\-((((0?[13578])|(1[02]))\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))\\-((((0?[13578])|(1[02]))\\-((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))\\-((0?[1-9])|([1-2][0-9])|(30)))|(0?2\\-((0?[1-9])|(1[0-9])|(2[0-8]))))))$";
    }

    Pattern p = Pattern.compile(eL);
    Matcher m = p.matcher(strDate);
    return m.matches();
  }

  public static String getPreviousDate7(java.util.Date date, String updatetype, int num, String returnType)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    if (updatetype.toLowerCase().equals("y"))
      calendar.add(1, num);
    else if (updatetype.equals("M"))
      calendar.add(2, num);
    else if (updatetype.toLowerCase().equals("d"))
      calendar.add(5, num);
    else if (updatetype.toLowerCase().equals("h")) {
      calendar.add(10, num);
    }
    return format(calendar.getTime(), returnType);
  }

  public static String formatDatemhs(java.util.Date date) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(date);
  }

  public static String getSysDate()
  {
    java.util.Date d = new java.util.Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    return df.format(d);
  }

  public static String getLastDay()
  {
    Calendar c = Calendar.getInstance();
    c.setTime(getNowDate());
    int today = c.get(5);
    c.set(5, today - 1);
    String lastDay = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    return lastDay;
  }

  public static int compareDate(String str1)
  {
    int result = 0;
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      java.util.Date date = df.parse(str1);
      result = date.getTime() - new java.util.Date().getTime() >= 0L ? 1 : -1;
    } catch (Exception e) {
      result = 0;
    }
    return result;
  }

  public static java.util.Date dateAddMinutes(java.util.Date paramDate, int minutes)
  {
    return new java.util.Date(paramDate.getTime() + minutes * 60 * 1000);
  }

  public static String HPTimeAddMinutes(String strTime, int minutes)
  {
    String hour = strTime.substring(8, 10);
    String min = strTime.substring(10, 12);
    String sec = strTime.substring(12, strTime.length());

    Calendar c = Calendar.getInstance();
    int timeSum = Integer.valueOf(hour).intValue() * 3600 * 1000;
    timeSum += Integer.valueOf(min).intValue() * 60 * 1000;
    timeSum += Integer.valueOf(sec).intValue() * 1000;
    c.setTime(formatTimeString(strTime));

    c.add(14, timeSum + minutes * 60 * 1000);
    String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());

    return result;
  }

  public static String getSysNowTime()
  {
    Calendar c = Calendar.getInstance();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
  }
}