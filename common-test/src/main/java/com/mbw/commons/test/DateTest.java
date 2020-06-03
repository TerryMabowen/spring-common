package com.mbw.commons.test;

import cn.hutool.core.date.DateUtil;

import java.util.*;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 14:47
 */
public class DateTest {
    public static void main(String[] args) {
        test1();
    }


    private static void test1() {
        String beginTime = "2020-01-01";
        Date startDate = DateUtil.parse(beginTime, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        if (!now.after(startDate)) {
            throw new RuntimeException("now before startDate");
        }
//        List<DateTime> dateTimes = DateUtil.rangeToList(startDate, now, DateField.DAY_OF_YEAR);
//        for (DateTime dateTime : dateTimes) {
//            System.out.println(dateTime);
//        }
        //两个时间段之间相差多少天
        int rangeDay = (int) ((now.getTime() - startDate.getTime()) / (24*60*60*1000));
        List<Date> rangeDate = new ArrayList<>();
        cal.setTime(startDate);
        System.out.println(rangeDay);
        for (int i = 0; i <= rangeDay; i++) {
            rangeDate.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }

        rangeDate.forEach(date -> System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss")));
    }
}
