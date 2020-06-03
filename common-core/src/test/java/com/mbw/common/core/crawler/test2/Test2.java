package com.mbw.common.core.crawler.test2;

import com.mbw.common.core.crawler.boc.ExchangeRateCrawlerPipeline;
import com.mbw.common.core.crawler.boc.ExchangeRatePageProcessor;
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
            //币种咱们可以定死，就咱们常用的那几个
            String[] currencies = new String[] {"美元", "日元", "欧元"};
            //去数据库查询每个币种最新的时间，如果没有，则从2020-01-01开始爬取
            Date begin = DateUtil.parse("2020-06-01", "yyyy-MM-dd");
            //每次爬取一个月的，防止相同IP请求太多导致被封IP
            Date end = DateUtil.getAfterDate(begin, 30);
            //爬取到最后就是每天爬取一次
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
                    Spider.create(new ExchangeRatePageProcessor()) //TestRepoPageProcessor 这里进行爬取和解析html获取汇率数据
                            .addUrl(url)
                            .addPipeline(new ExchangeRateCrawlerPipeline()) //BOCCrawlerPipeline 在这里进行入库操作
                            .thread(1) //单线程爬取
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
