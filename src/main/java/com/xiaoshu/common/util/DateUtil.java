package com.xiaoshu.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fugl
 * @Description:日期工具类
 * @date 2018年4月10日
 */
public class DateUtil<main> {
    //用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;
    private static int MaxDate;//一月最大天数
    private static int MaxYear;//一年最大天数

    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_TIME = "HH:mm:ss";
    public final static String FORMAT_MIN = "HH:mm";

    public final static String FORMAT_DATE_ZH = "yyyy年MM月dd日";
    public final static String FORMAT_DATE_ZH_MD = "MM月dd日";
    public final static String FORMAT_DATETIME_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

    public final static String TYPE_DATE = "date";
    public final static String TYPE_DATETIME = "datetime";

    /**
     * 日期排序类型-升序
     */
    public final static int DATE_ORDER_ASC = 0;

    /**
     * 日期排序类型-降序
     */
    public final static int DATE_ORDER_DESC = 1;



    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return
     */
    public static String getCurrDate() {
        return DateToStringYYMMDD(new Date());
    }

    /**
     * 时间转字符串日期
     *
     * @param date
     * @return
     */
    public static String DateToStringYYMMDD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        String result = null;
        result = format.format(date);
        return result;
    }

    public static String DateToStringByF(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME);
        String result = null;
        result = format.format(date);
        return result;
    }

    /**
     * 字符串转换成日期
     *
     * @return
     */
    public static Date stringToDateByF(String date) {
        if (date == null || date.equals(""))
            return null;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME);
        Date result = null;
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换成日期
     * @return
     */
    public static Date stringToDateYYMMDD(String date) {
        if (date == null || date.equals(""))
            return null;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        Date result = null;
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将日期加上某些天或减去天数)返回字符串
     *
     * @param date 待处理日期
     * @param to   加减的天数
     * @return 日期
     */
    public static Date dateAdd(String date, int to) {
        Date d = null;
        try {
            d = java.sql.Date.valueOf(date);
        } catch (Exception e) {
            e.printStackTrace();
            d = new Date();
        }
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(d);
        strDate.add(Calendar.DATE, to); // 日期加
        return strDate.getTime();
    }

    public static Date addMinutes(Date date, int to) {
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(date);
        strDate.add(Calendar.MINUTE, to);
        return strDate.getTime();
    }

    /**
     * 将日期加上某些天或减去天数)返回字符串
     *
     * @param date 待处理日期
     * @param to   加减的天数
     * @return 日期
     */
    public static java.sql.Date dateAdd(java.sql.Date date, int to) {
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(date);
        strDate.add(Calendar.DATE, to); // 日期减 如果不够减会将月变动
        return new java.sql.Date(strDate.getTime().getTime());
    }

    /**
     * 将日期加上某些天或减去天数)返回字符串
     *
     * @param date 待处理日期
     * @param to   加减的天数
     * @return 日期
     */
    public static String datePlus(Date date, int to) {
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(date);
        strDate.add(Calendar.DATE, to); // 日期减 如果不够减会将月变动
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        Date dt1=strDate.getTime();
        String result = format.format(dt1);
        return result;
    }

    /**
     * 获取中文格式当前日期
     *
     * @param pattern "long":1984年10月9日
     *                "full":1984年10月9日 星期二
     * @return
     */
    public static String getCurrDateCN(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if ("short".equals(pattern)) {
            return month + "月" + day + "日";
        }
        if ("long".equals(pattern)) {
            return year + "年" + month + "月" + day + "日";
        }
        if ("full".equals(pattern)) {
            return year + "年" + month + "月" + day + "日 " + getDayOfWeek(calendar.getTime());
        }
        return "";
    }

    /**
     * @param date
     * @return
     * @MethodName: getDayOfWeek
     * @Description: 获取中文星期
     */
    public static String getDayOfWeek(Date date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;

        return dayNames[dayOfWeek];
    }

    /**
     * 获取周一的日期
     *
     * @param date
     * @return
     */
    public static String getMonday(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime()); //周一时间
        return imptimeBegin;
    }

    //*****************************************************************************************************************************//

    /**
     * 比较时间大小  timeStr0 <= timeStr1 返回true
     *
     * @param timeStr0 HH:mm
     * @param timeStr1 HH:mm
     * @return
     */
    public static Boolean compareTime(String timeStr0, String timeStr1) {
        DateFormat df = new SimpleDateFormat("HH:mm");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date dt0 = df.parse(timeStr0);//将字符串转换为date类型
            Date dt1 = df.parse(timeStr1);
            return !dt0.after(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较时间大小  timeStr0 <= timeStr1 返回true
     *
     * @param timeStr0 HH:mm
     * @param timeStr1 HH:mm
     * @return
     */
    public static Boolean compareDateTime(String timeStr0, String timeStr1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date dt0 = df.parse(timeStr0);//将字符串转换为date类型
            Date dt1 = df.parse(timeStr1);
            return !dt0.after(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     * @MethodName: compare
     * @Description: 比较2个字符型时间大小，true小于,false大于
     * @author Fuguolin
     * @date 2017年5月9日 下午3:26:48
     */
    public static boolean compare(String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date a;
        Date b;
        try {
            a = sdf.parse(time1);
            b = sdf.parse(time2);
            if (a.before(b))
                return true;
            else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date stringToDate(String dbdate, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        Date date = df.parse(dbdate);
        return date;
    }

    public static String dateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String datestr = df.format(date);
        return datestr;
    }

    /***
     * 获取当天
     * @return
     */
    public static String curDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /***
     * 获取当天
     * @return
     */
    public static String curDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    /***
     * 昨天
     * @return
     */
    public static String beforDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return format.format(date);
    }

    /***
     * 明天
     * @return
     */
    public static String afterDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return format.format(date);
    }

    /***
     * 明天
     * @return
     */
    public static String afterDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date newDate = format.parse(date, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date1 = calendar.getTime();
        return format.format(date1);
    }

    //由出生日期获得年龄
    public static int getAge(String birthDay) {
        Date bitrhDate = parseDate(birthDay);

        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(bitrhDate);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getAgeToDay(String birthDay) {
        String result = "";
        Date birthday = parseDate(birthDay);
        Calendar now = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        b.setTime(birthday);
        int year = now.get(Calendar.YEAR) - b.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) - b.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH) - b.get(Calendar.DAY_OF_MONTH);
        if (month < 0) {
            month = 12 - b.get(Calendar.MONTH) + now.get(Calendar.MONTH);
            year -= 1;
        }
        if (day < 0) {
            day = b.getMaximum(Calendar.DAY_OF_MONTH) - b.get(Calendar.DAY_OF_MONTH) + now.get(Calendar.DAY_OF_MONTH);
            month -= 1;
        }
        if (year == 0 && month == 0) {
            result = day + "天";
        } else if (year == 0 && month != 0) {
            result = month + "个月" + day + "天";
        } else {
            result = year + "岁" + month + "个月" + day + "天";
        }
        // year + "岁" + month + "个月" + day + "天";
        return result;
    }

    /**
     * 获得出生日期
     *
     * @param age
     * @return
     */
    public static String getBirthDate(String age) {
        String y = "";
        String m = "";
        String d = "";

        int index = -1;
        if (age.contains("岁")) {
            y = age.substring(index + 1, age.indexOf("岁"));
            index = age.indexOf("岁");
        }
        if (age.contains("月")) {
            m = age.substring(index + 1, age.indexOf("月"));
            index = age.indexOf("月");
        }
        if (age.contains("天")) {
            d = age.substring(index + 1, age.indexOf("天"));
            index = age.indexOf("天");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        //减去天
        if (StringUtils.isNotEmpty(d)) {
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 0 - Integer.parseInt(d));
            date = calendar.getTime();
        }
        if (StringUtils.isNotEmpty(m)) {
            //减去月
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 0 - Integer.parseInt(m));
            date = calendar.getTime();
        }
        if (StringUtils.isNotEmpty(y)) {
            //减去年
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 0 - Integer.parseInt(y));
            date = calendar.getTime();
        }

        return format.format(date);
    }

    /**
     * 获取星期几
     * @return
     */
    public static String getWeekDay(String sourceDate) {
        String weekDay = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(sourceDate));
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case 1:
                    weekDay = "星期日";
                    break;
                case 2:
                    weekDay = "星期一";
                    break;
                case 3:
                    weekDay = "星期二";
                    break;
                case 4:
                    weekDay = "星期三";
                    break;
                case 5:
                    weekDay = "星期四";
                    break;
                case 6:
                    weekDay = "星期五";
                    break;
                case 7:
                    weekDay = "星期六";
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekDay;
    }

    /**
     * 指定日期 指定间隔天数
     */
    public static String getDate(String str, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            calendar.setTime(format.parse(str));
            calendar.add(Calendar.DAY_OF_MONTH, day);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }

    public static String getDate(String str, int day, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            calendar.setTime(format.parse(str));
            calendar.add(Calendar.DAY_OF_MONTH, day);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }

    public static String convertMondayByDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        try {
            Date time = sdf.parse(date);
            return convertMondayByDate(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertMondayByDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); //输出要计算日期

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        String imptimeBegin = sdf.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);

        return imptimeBegin;
    }

    public static String convertSundayByDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); //输出要计算日期

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        cal.add(Calendar.DATE, 6);

        String imptimeEnd = sdf.format(cal.getTime());

        System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeEnd;
    }

    public static String convertSundayByDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        try {
            Date time = sdf.parse(date);
            return convertSundayByDate(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到指定月后的日期
     */

    public static String getAfterMonth(int month) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse("2009-11-04");//初始日期
            c.setTime(date);//设置日历时间
            c.add(Calendar.MONTH, month);//在日历的月份上增加6个月
            String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
            return strDate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 得到二个日期间的间隔秒数
     */
    public static String getTwoDateHaveSecond(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        try {
            Date oldDate = myFormatter.parse(sj1);
            Date newDate = myFormatter.parse(sj2);
            day = (newDate.getTime() - oldDate.getTime()) / 1000;
        } catch (Exception e) {
            return "error";
        }
        return day + "";
    }

    /**
     * 得到二个日期间的间隔分钟数
     */
    public static String getTwoDateHaveMin(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        try {
            Date oldDate = myFormatter.parse(sj1);
            Date newDate = myFormatter.parse(sj2);
            day = (newDate.getTime() - oldDate.getTime()) / 1000 / 60;
        } catch (Exception e) {
            return "error";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour 中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 两个时间之间的秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getSeconds(String date1, Date date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat(FORMAT_DATETIME);
        Date firstDate = null;
        try {
            firstDate = myFormatter.parse(date1);
        } catch (Exception e) {
        }
        long secondes = (date2.getTime() - firstDate.getTime()) / 1000;
        return secondes;
    }

    // 计算当月最后一天,返回字符串
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1 号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1 号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 上月第一天
    public static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1 号
        lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1 号
        //lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取当月第一天
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1 号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取输入月第一天
    public static String getFirstDayOfMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            cale.add(Calendar.MONTH, 0);
            cale.set(Calendar.DAY_OF_MONTH, 1);
            return sdf.format(cale.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取输入月最后一天
    public static String getLastDayOfMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            cale.add(Calendar.MONTH, 1);
            cale.set(Calendar.DAY_OF_MONTH, 0);
            return sdf.format(cale.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    public static int getDaysOfMonth(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 =  sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
            return 30;
        }
    }

    // 获得本周星期日的日期
    public static String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        //DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        String preMonday = df.format(monday);
        return preMonday;
    }

    //获取当天时间
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    //获得本周一的日期
    public static String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        //DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        String preMonday = df.format(monday);
        return preMonday;
    }

    //获得相应周的周六的日期
    public static String getSaturday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        //DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期日的日期
    public static String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期一的日期
    public static String getPreviousWeekday() {
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期一的日期
    public static String getNextMonday() {
        weeks++;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获取下星期整个星期的字符串数组
    public static List<String> getNextWeekList(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        try {
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            // 获得当前日期是一个星期的第几天
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cal.getTime());
            calendar.add(Calendar.DATE, 7);
            String monday = sdf.format(calendar.getTime());
            String Tuesday =addDay(monday,1);
            String Wednesday =addDay(monday,2);
            String Thursday =addDay(monday,3);
            String Friday =addDay(monday,4);
            String Saturday =addDay(monday,5);
            String Sunday =addDay(monday,6);
            dateList.add(monday);
            dateList.add(Tuesday);
            dateList.add(Wednesday);
            dateList.add(Thursday);
            dateList.add(Friday);
            dateList.add(Saturday);
            dateList.add(Sunday);
            return dateList;
        } catch (Exception e) {
            e.printStackTrace();
            return dateList;
        }
    }

    //加一天时间
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }

    }


    // 获得输入日期的下周星期一的日期
    public static String getNextMonday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            // 获得当前日期是一个星期的第几天
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            // 获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cal.getTime());
            calendar.add(Calendar.DATE, 7);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取下周对应的日期（如2018-09-11，返回2018-09-18）
    public static String getNextWeekDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DATE, 7);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取下月对应的日期（如2018-09-11，返回2018-09-18）
    public static String getNextMonthDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 1);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String monthPlus(String datetime,int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, num);
        date = cl.getTime();
        return sdf.format(date);
    }

    public static void main(String[] args) {
       // System.out.println(getNextWeekDay("2019-04-15"));
        System.out.println(datePlus(new Date(),0));
        System.out.println(monthPlus("2019-01",-1));
        System.out.println(curDate("yyyy-MM"));
        System.out.println(getDaysOfMonth("2019-05-29"));
        int a=120;
        int b=1000;
        double f1 = new BigDecimal((float)a/b).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        f1= f1*100;
        String r = f1+"%";
        System.out.println("ddd==="+r);
        System.out.println("本周的星期一是："+getMondayOFWeek());


    }

    //获取输入月之后的输入月数对应的日期（如2018-09-11，1，返回2018-10-11）
    public static String getMonthDay(String date, int monthAge) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, monthAge);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    // 获得下周星期日的日期
    public static String getNextSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);//把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    //获得上月最后一天的日期
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得下个月第一天的日期
    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得输入月下个月第一天的日期
    public static String getNextMonthFirst(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            cale.add(Calendar.MONTH, 1);//减一个月
            cale.set(Calendar.DATE, 1);//把日期设置为当月第一天
            return sdf.format(cale.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取输入月的天数
    public static int getMonthDays(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar cale = Calendar.getInstance();
            cale.setTime(date1);
            return cale.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //获得下个月最后一天的日期
    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);//加一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得明年最后一天的日期
    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);//加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得明年第一天的日期
    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);//加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得本年有多少天
    @SuppressWarnings("unused")
    private static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    //获得本年第一天的日期
    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    //获得本年最后一天的日期 *
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    //获得上年第一天的日期 *
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    //获得上年最后一天的日期
    public static String getPreviousYearEnd() {
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    //获得本季度
    public static String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    //************************************************************************************************************************//

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return formatDate(new Date(), "yyyyMMdd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getMsTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.isNotEmpty(pattern)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            formatDate = sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formatDate = sdf.format(date);
        }
        return formatDate;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description:(日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (parseDate(s) == null || parseDate(e) == null) {
            return false;
        }
        return parseDate(s).getTime() >= parseDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            SimpleDateFormat sdf = null;
            if (StringUtils.isEmpty(pattern)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                sdf = new SimpleDateFormat(pattern);
            }
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        return formatDate(date, pattern);
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s, String pattern) {
        return parse(s, pattern) != null;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    //判断两个日期是否处于同一周
    public static boolean isSameWeek(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);//西方周日为一周的第一天，咱得将周一设为一周第一天
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0)// subYear==0,说明是同一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) //subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)//subYear==-1,说明cal比cal2小一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    public static boolean isSameMonth(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(d1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(d2);
//		int year1 = calendar1.get(Calendar.YEAR);
//		int year2 = calendar2.get(Calendar.YEAR);
//		int month1 = calendar1.get(Calendar.MONTH);
//		int month2 = calendar2.get(Calendar.MONTH);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }



    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateChar = sim.format(date);
        return dateChar;
    }

    //把一种格式的日期，转化成另一种格式的日期字符串
    public static String formatDate(String strDate, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);

        try {
            Date date = sdf.parse(strDate);
            result = sdf1.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String formatDateByStringDate(String strDate, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);

        try {
            Date date = sdf.parse(strDate);
            result = sdf1.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //获取两个时间间隔的分钟数
    public static long getCount(String startTime, String endTime) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            long result = (endDate.getTime() - startDate.getTime()) / 1000 / 60;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //获取两个时间间隔的分钟数
    public static String getAfterHalfHour(String date) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        try {
            Date date1 = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            cal.add(Calendar.MINUTE, 30);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取输入周之后的周对应的日期（如2018-09-11，1，返回2018-09-18）
    public static String getWeekDay(String date, int weekNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            weekNum *= 7;
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_WEEK, weekNum);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取输入周所在星期的星期几所在的日期
    public static String getDayByWeekDay(String date, int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_WEEK, num - 1);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    //获取当前的时间
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //获取输入日期的前一天晚上21:00的具体时间，AppVaccinationProcessService专用
    public static String getCorrectTime(String crrrDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(crrrDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - 1);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return crrrDate;
        }

    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second); //正常展示时分秒
                //timeStr = unitFormat(minute) + ":" + unitFormat(second); //这里因为剧情需要把小时直接扔了
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String getAfterMinDateTime(String dateTime, int min) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        long afterTime = millis + min * 60 * 1000;
        Date newdateTime = new Date(afterTime);
        String afterDateTime = "";
        afterDateTime = format.format(newdateTime);
        return afterDateTime;
    }


    public static String getCurrentDateStr(String pattern) {
        return GeneralHelper.date2Str(getCurrentDate(), pattern);
    }

}
