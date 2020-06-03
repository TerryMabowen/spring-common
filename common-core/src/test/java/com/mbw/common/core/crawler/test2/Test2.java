package com.mbw.common.core.crawler.test2;

import com.mbw.commons.util.date.DateUtil;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 10:27
 */
public class Test2 {

    @Test
    public void f1() {
        try {
            Date now = Calendar.getInstance().getTime();
            String[] currencies = new String[] {"美元", "日元", "欧元"};
            Date begin = DateUtil.parse("2020-01-01", "yyyy-MM-dd");
            Date end = DateUtil.getAfterDate(begin, 30);
            if (end.getTime() > now.getTime()) {
                end = now;
            }

            List<String> rangeDate = test1(begin, end);
            for (String currency : currencies) {
                String currencyName = URLEncoder.encode(currency, "UTF-8");
                for (String dateStr : rangeDate) {
                    String url = "https://srh.bankofchina.com/search/whpj/search_cn.jsp?erectDate" +
                            dateStr +
                            "&nothing=" +
                            dateStr +
                            "&pjname=" +
                            currencyName;
                    Spider.create(new TestRepoPageProcessor())
                            .addUrl(url)
                            .addPipeline(new BOCCrawlerPipeline())
                            .thread(1)
                            .run();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> test1(Date beginTime, Date endTime) {
        if (!endTime.after(beginTime)) {
            throw new RuntimeException("now before startDate");
        }

        //两个时间段之间相差多少天
        int rangeDay = (int) ((endTime.getTime() - beginTime.getTime()) / (24*60*60*1000));
        List<String> rangeDate = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        System.out.println(rangeDay);
        for (int i = 0; i <= rangeDay; i++) {
            rangeDate.add(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
            cal.add(Calendar.DATE, 1);
        }

        return rangeDate;
    }
}
