package com.mbw.commons.util.date;

import com.mbw.commons.lang.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-04-08 22:07
 */
public class DateUtil {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return
     */
    public static Date now() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * TODO
     * @author Mabowen
     * @date 22:12 2020-04-08
     * @param
     * @return
     */
    public static String format(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN;
        }

        if (null == date) {
            throw new ServiceException("date can not is null");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * TODO
     * @author Mabowen
     * @date 09:50 2020-04-09
     */
    public static Date parse(String str, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN;
        }

        if (StringUtils.isBlank(str)) {
            throw new ServiceException("date str can not is empty");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("parse date str exception：" + e.getMessage(), e);
        }
    }

    /**
     * 获取当前时间戳---精确到毫秒
     * @author bowen.M
     * @param
     * @date 2020/02/20 14:57
     * @return
     */
    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        Date date = new Date();
        return sdf.format(date);
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
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
            Date startDate = sdf.parse(startTime);
            cal.setTime(startDate);
            LocalDate start = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            Date endDate = sdf.parse(endTime);
            cal.setTime(endDate);
            LocalDate end = LocalDate.of(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));

            return Period.between(start, end);
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ServiceException("解析字符串日期异常：" + exp.getMessage(), exp);
        }
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
}
