//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yc.sandfactory.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {
  private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
  public static final int ONE_DAY_TIME = 86400000;

  public DateTimeUtil() {
  }

  public static int compare(Date date1, Date date2) {
    long compareResult = date1.getTime() - date2.getTime();
    return compareResult > 0L?1:(compareResult == 0L?0:-1);
  }

  public static Date getPrevDate() {
    return getPrevDate(DateTime.now().toDate());
  }

  public static Date getPrevDate(Date date) {
    if(null == date) {
      throw new IllegalArgumentException("参数date不能为null");
    } else {
      return (new DateTime(date)).minusDays(1).toDate();
    }
  }

  public static Date getNextDate() {
    return getNextDate(DateTime.now().toDate());
  }

  public static Date getNextDate(Date date) {
    if(null == date) {
      throw new IllegalArgumentException("参数date不能为null");
    } else {
      return (new DateTime(date)).plusDays(1).toDate();
    }
  }

  public static String getWeek(Date date) {
    return (new SimpleDateFormat("EEEE", Locale.CHINESE)).format(date);
  }

  public static Date dateStrToDate(String dateStr) {
    return DateTime.parse(dateStr).toDate();
  }

  public static Date longToDate(long time) {
    return (new DateTime(time)).toDate();
  }

  public static String longToDateStr(long time) {
    return longToDateStr(time, (String)null);
  }

  public static String longToDateStr(long time, String pattern) {
    return time == 0L?"－－":dateToStr(longToDate(time), pattern);
  }

  public static String dateToStr(Date date, String pattern) {
    return (new SimpleDateFormat(StringUtils.isNotBlank(pattern)?pattern:"yyyy-MM-dd HH:mm:ss")).format(date);
  }

  public static String dateToStr(Date date) {
    return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
  }

  public static long dateTimeStrToLong(String dateTimeStr, boolean isEndTime) {
    long result = dateTimeStrToLong(dateTimeStr);
    return isEndTime?result + 999L:result;
  }

  public static long dateTimeStrToLong(String dateTimeStr) {
    return DateTime.parse(dateTimeStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis();
  }

  public static long getStartTime(String startTime) {
    return dateTimeStrToLong(startTime + " 00:00:00");
  }

  public static long getEndTime(String endTime) {
    return dateTimeStrToLong(endTime + " 23:59:59", true);
  }

  public static Long getCurrentTime() {
    return Long.valueOf(DateTimeUtils.currentTimeMillis());
  }

  public static Date intToDate(int dateTime, String formatStr) {
    SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr);
    Date date = null;

    try {
      date = simpledateformat.parse(String.valueOf(dateTime));
    } catch (ParseException var5) {
      logger.error("转换8位int（20110101）的时间值为日期型出错", var5);
    }

    return date;
  }
}
