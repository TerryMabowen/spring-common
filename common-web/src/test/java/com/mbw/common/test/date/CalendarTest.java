package com.mbw.common.test.date;

import com.mbw.commons.util.date.DateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-19 09:19
 */
public class CalendarTest {
    private static Logger logger = LoggerFactory.getLogger(CalendarTest.class);

    @Test
    public void f1() {
        Date date = DateUtil.defaultParse("2020-06-19 23:59:59");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        logger.info("当前小时是{}", hour);
    }
}
