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
    private static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    private static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static final String yyyy_MM_dd = "yyyy-MM-dd";
    private static final String yyyyMMdd = "yyyyMMdd";

    /**
     * 获取当前时间
     * @return
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
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
            pattern = yyyy_MM_dd_HH_mm_ss;
        }

        if (null == date) {
            throw new ServiceException("date can not is null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String defaultFormat(Date date) {
        return format(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String formatShort(Date date) {
        return format(date, yyyy_MM_dd);
    }

    /**
     * 解析日期字符串
     * @author Mabowen
     * @date 09:50 2020-04-09
     */
    public static Date parse(String str, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = yyyy_MM_dd_HH_mm_ss;
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
        return parse(str, yyyy_MM_dd_HH_mm_ss);
    }

    public static Date parseShort(String str) {
        return parse(str, yyyy_MM_dd);
    }

    /**
     * 获取当前时间戳---精确到毫秒
     * @author bowen.M
     * @param
     * @date 2020/02/20 14:57
     * @return
     */
    public static String getCurrentTimestamp() {
        return format(Calendar.getInstance().getTime(), yyyyMMddHHmmssSSS);
    }

    /**
     * 计算两个字符串日期的时间范围，返回Period
     * @author Mabowen
     * @date 10:02 2020-04-10
     * @param startTime
     * @param endTime
     * @return Period.getYears(), getMonths(), getDays()
     */
    public static Period computeTimeRange(String startTime, String endTime) {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            throw new ServiceException("传入的字符串时间不能为空");
        }
        Calendar cal = Calendar.getInstance();
        Date startDate = defaultParse(startTime);
        cal.setTime(startDate);
        LocalDate start = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

        Date endDate = defaultParse(endTime);
        cal.setTime(endDate);
        LocalDate end = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

        return Period.between(start, end);
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
     * 获取两个日期段之间的每一天
     * @author Mabowen
     * @date 2020-06-03 20:15
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<String> rangeDate(Date beginTime, Date endTime) {
        if (!endTime.after(beginTime)) {
            throw new ServiceException("now before startDate");
        }

        //两个时间段之间相差多少天
        int rangeDay = (int) ((endTime.getTime() - beginTime.getTime()) / (24*60*60*1000));
        List<String> rangeDate = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        for (int i = 0; i <= rangeDay; i++) {
            rangeDate.add(format(cal.getTime(), yyyy_MM_dd));
            cal.add(Calendar.DATE, 1);
        }

        return rangeDate;
    }
}
