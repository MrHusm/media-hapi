package com.yxsd.kanshu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hushengmeng
 * @date 2017/8/16.
 */
public class DateUtil {
    public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     *
     * Description: 将日期字符串解析为Date对象
     *
     * @Version1.0 2014年12月17日 上午11:08:26 by 吕翔 (lvxiang@dangdang.com) 创建
     * @param dateStr
     * @return
     */
    public static Date parseStringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static Date getDateByFormat(String date, String format) {
        Date result = null;
        if (date != null && !date.trim().equals("")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String format1(Date date) {
        try {
            return format(date, "yyyy-MM-dd");
        } catch (Exception e) {
            return null;
        }

    }

    public static Date getOnlyDay(Date date) {
        try {
            String pattern = "yyyy-MM-dd";
            String formatted = format(date, pattern);
            Date onlyDay = getDateByFormat(formatted, pattern);
            return onlyDay;
        } catch (Exception e) {
            return null;
        }
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getDate(Date date, String formatStr) {
        try {
            return format(date, formatStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTime(Date date) {
        try {
            return format(date, "HH:mm:ss");
        } catch (Exception e) {
            return null;
        }

    }

    public static String getDateTime(Date date) {
        try {
            return format(date, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 日期相加
     *
     * @param date
     *            Date
     * @param day
     *            int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     *
     * Description:
     *
     * @Version1.0 2014年12月2日 下午3:15:50 by 魏嵩（weisong@dangdang.com）创建
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minute) * 60 * 1000);
        return c.getTime();
    }

    /**
     *
     * Description:
     *
     * @Version1.0 2014年12月2日 下午3:15:50 by 魏嵩（weisong@dangdang.com）创建
     * @param date
     * @param minute
     * @return
     */
    public static Date addMillSecond(Date date, long millSecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + millSecond);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date
     *            Date
     * @param date1
     *            Date
     * @return int
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减(返回秒值)
     *
     * @param date
     *            Date
     * @param date1
     *            Date
     * @return int
     * @author
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
    }

    public static Date getdate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resultDate = null;
        try {
            resultDate = sdf.parse(date);
        } catch (Exception e) {
        }
        return resultDate;
    }

    public static Date getdateFromString(String dateString, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    public static Date getPayDate(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (Exception e) {
            try {
                sdf = new SimpleDateFormat("yyyyMMdd");
                date1 = sdf.parse(date);
            } catch (Exception e1) {
                try {
                    date1 = getdateFromString(date, DATE_PATTERN);
                } catch (Exception e2) {

                }
            }
        }
        return date1;
    }

    public static Date getMaxTimeByStringDate(String date) throws Exception {
        String maxTime = date + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(maxTime);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = DateUtil.getDateTime(date);
        try {
            return sdf.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getCurrentDateTimeToStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(getCurrentDateTime());
    }

    public static String getCurrentDateTimeToStr2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getCurrentDateTime());
    }

    /**
     * Description: 获取 yyyy-MM-dd
     *
     * @Version1.0 2014年12月24日 下午4:12:02 by 周伟洋（zhouweiyang@dangdang.com）创建
     * @return
     */
    public static String getCurrentSimpleDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getCurrentDateTime());
    }

    public static Long getWmsupdateDateTime() {
        Calendar cl = Calendar.getInstance();

        return cl.getTimeInMillis();
    }

    public static Integer getLeftSeconds(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date condition = sdf.parse(date);
        long n = condition.getTime();
        long s = sdf.parse(getCurrentDateTimeToStr2()).getTime();
        return (int) ((s - n) / 1000);
    }

    /**
     * Description:当前天数+days
     *
     * @Version1.0 2014年12月4日 下午4:04:58 by 周伟洋（zhouweiyang@dangdang.com）创建
     * @return
     */
    public static Date addDaysOnToday(Date date, int days) {
        Calendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        return date;
    }

    /**
     * Description: 计算当前时间与当天结束时间相差的秒数
     *
     * @Version1.0 2014年12月24日 下午3:54:31 by 周伟洋（zhouweiyang@dangdang.com）创建
     * @return
     * @throws Exception
     */
    public static int getSecondsToNextDay() throws Exception {
        String simpleDate = getCurrentSimpleDate();
        Date maxDate = getMaxTimeByStringDate(simpleDate);
        return (int) ((maxDate.getTime() - System.currentTimeMillis()) / 1000);

    }

    /**
     *
     * Description: 获取中国版星期几 星期一到星期日为 1到7
     *
     * @Version1.0 2015年4月7日 上午11:57:29 by 许文轩（xuwenxuan@dangdang.com）创建
     * @param dayOfWeek
     * @return
     */
    public static int getDayOfWeekChina(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return 7;
        }
        return dayOfWeek - 1;
    }

    /**
     *
     * Description: 将中国版星期几转化为国际版
     *
     * @Version1.0 2015年4月7日 下午2:06:31 by 许文轩（xuwenxuan@dangdang.com）创建
     * @param dayOfWeekChina
     * @return
     */
    public static int getDayOfWeekWest(int dayOfWeekChina) {
        if (dayOfWeekChina == 7) {
            return 1;
        }
        return dayOfWeekChina + 1;
    }
}
