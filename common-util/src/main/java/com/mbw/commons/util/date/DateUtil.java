package com.mbw.commons.util.date;

import com.mbw.commons.lang.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author Mabowen
 * @date 2020-04-08 22:07
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String SHORT_TIME_PATTERN = "HH:mm:ss";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String HHmmss = "HHmmss";
    public static final String BEGIN_DAY_SUFFIX = " 00:00:00";
    public static final String END_DAY_SUFFIX = " 23:59:59";

    /**
     * 获取当前时间
     * @return
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前时间戳---精确到毫秒
     * @author bowen.M
     * @date 2020/02/20 14:57
     * @return
     */
    public static String getCurrentTimestamp() {
        return format(Calendar.getInstance().getTime(), TIMESTAMP_PATTERN);
    }

    /**
     * 格式化时间
     * @author Mabowen
     * @date 22:12 2020-04-08
     * @param
     * @return
     */
    public static String format(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }

        if (null == date) {
            throw new ServiceException("date can not is null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String defaultFormat(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String formatShort(Date date) {
        return format(date, SHORT_DATE_PATTERN);
    }

    /**
     * 解析日期字符串
     * @author Mabowen
     * @date 09:50 2020-04-09
     */
    public static Date parse(String str, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }

        if (StringUtils.isBlank(str)) {
            throw new ServiceException("date str can not is empty");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            logger.error("parse date str exception：" + e.getMessage(), e);
            throw new ServiceException("parse date str exception：" + e.getMessage(), e);
        }
    }

    public static Date defaultParse(String str) {
        return parse(str, DEFAULT_PATTERN);
    }

    public static Date parseShort(String str) {
        return parse(str, SHORT_DATE_PATTERN);
    }

    /**
     * 获取传入时间多少天之前的日期
     * @author Mabowen
     * @date 10:16 2020-04-15
     * @param current
     * @param days
     * @return
     */
    public static Date getBeforeDate(Date current, int days) {
        if (current == null) {
            throw new ServiceException("传入的日期不能为null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        if (days > 0) {
            days = -days;
        }

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * 获取传入时间多少天之后的日期
     * @author Mabowen
     * @date 10:16 2020-04-15
     * @param current
     * @param days
     * @return
     */
    public static Date getAfterDate(Date current, int days) {
        if (current == null) {
            throw new ServiceException("传入的日期不能为null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);

        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /**
     * 获取一天的开始时间
     * @author Mabowen
     * @date 17:11 2020-06-24
     * @param date
     * @return
     */
    public static String getBeginDay(Date date) {
        if (date != null) {
            return format(date, SHORT_DATE_PATTERN) + BEGIN_DAY_SUFFIX;
        }

        throw new ServiceException("date can not is null");
    }

    /**
     * 获取一天的结束时间
     * @author Mabowen
     * @date 17:11 2020-06-24
     * @param date
     * @return
     */
    public static String getEndDay(Date date) {
        if (date != null) {
            return format(date, SHORT_DATE_PATTERN) + END_DAY_SUFFIX;
        }

        throw new ServiceException("date can not is null");
    }

    /**
     * 判断两个时间是否是同一天
     * @author Mabowen
     * @date 16:44 2020-06-24
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            return isSameDay(cal1, cal2);
        }

        throw new ServiceException("date1 or date2 can mot is null");
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 计算两个字符串日期的时间范围，返回Period
     * @author Mabowen
     * @date 10:02 2020-04-10
     * @param startTime
     * @param endTime
     * @return Period.getYears(), getMonths(), getDays()
     */
    public static Period computeTimeRange(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            LocalDate start = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            cal.setTime(endTime);
            LocalDate end = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            return Period.between(start, end);
        }

        throw new ServiceException("startTime or endTime can not is null");
    }

    /**
     * 获取两个日期段之间的每一天
     * @author Mabowen
     * @date 2020-06-03 20:15
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<String> rangeDate(Date beginTime, Date endTime) {
        if (beginTime != null && endTime != null) {
            if (!endTime.after(beginTime)) {
                throw new ServiceException("now before startDate");
            }

            //两个时间段之间相差多少天
            int rangeDay = (int) ((endTime.getTime() - beginTime.getTime()) / (24 * 60 * 60 * 1000));
            List<String> rangeDate = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(beginTime);
            for (int i = 0; i <= rangeDay; i++) {
                rangeDate.add(format(cal.getTime(), SHORT_DATE_PATTERN));
                cal.add(Calendar.DATE, 1);
            }

            return rangeDate;
        }

        throw new ServiceException("beginTime or endTime can not is null");
    }
}
