package com.mbw.common.core.crawler.boc;

import com.mbw.commons.util.date.DateUtil;
import us.codecraft.webmagic.Spider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 汇率爬虫任务
 *
 * @author Mabowen
 * @date 2020-06-03 20:15
 */
public class ExchangeRateCrawlerJob {
    /**
     * 1. 查询数据库汇率表某个币种的最新时间，如果没有则从2020-01-01开始，有就从获取的时间+1天开始
     * 2. 每次都只爬取一个月的汇率，结束时间为当前时间，如果获取的最新时间+1天为当前时间，则只爬取当天一次的汇率就可以
     *    爬取一个月的汇率一是要获取历史汇率，二是防止同一IP单位时间内请求次数过多导致IP被封，同时每次请求间隔3秒
     * 3. 由于当天的汇率只有一个，所以只爬取第一页，解析html后只拿取一个汇率数据即可
     * 4. 获取数据后入库保存到汇率表中，每个币种每天都有汇率，汇率：1外币金额 = xxx 人民币金额
     * 5. 外币换算人民币计算公式为：人民币金额 = 外币金额 * 该币种当天的汇率
     */
    public void executeExchangeRateCrawlerTask() {
        try {
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            //货币对枚举
            EnumCurrencyPair[] values = EnumCurrencyPair.values();
            for (EnumCurrencyPair value : values) {
                String currency = value.getValue();
                //模拟查询数据库数据
                ExchangeRatePO po = simulateData(currency);
                //爬取数据
                Date beginTime = null;
                //在获取的发布时间上+1天，如果po为null，则设置默认时间为2020-01-01
                if (po != null) {
                    beginTime = DateUtil.getAfterDate(po.getPubTime(), 1);
                } else {
                    //默认时间应是 2020-01-01
                    beginTime = DateUtil.parse("2020-06-01", "yyyy-MM-dd");
                }
                //每次爬取一个月的，防止相同IP请求太多导致被封IP
                Date endTime = DateUtil.getAfterDate(beginTime, 30);
                //爬取到最后就是每天爬取一次
                if (endTime.getTime() > now.getTime()) {
                    endTime = now;
                }
                //时间段
                List<String> rangeDate = rangeDate(beginTime, endTime);
                String currencyName = URLEncoder.encode(currency, "UTF-8");
                rangeDate.forEach(dateStr -> {
                    String url = "https://srh.bankofchina.com/search/whpj/search_cn.jsp?erectDate" +
                            dateStr +
                            "&nothing=" +
                            dateStr +
                            "&pjname=" +
                            currencyName;
                    Spider.create(new ExchangeRatePageProcessor()) //ExchangeRatePageProcessor 这里进行爬取和解析html获取汇率数据
                            .addUrl(url)
                            .addPipeline(new ExchangeRateCrawlerPipeline()) //ExchangeRateCrawlerPipeline 在这里进行入库操作
                            .thread(1) //单线程爬取
                            .run();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExchangeRatePO simulateData(String currencyName) {
        ExchangeRatePO po = new ExchangeRatePO();
        po.setCurrencyName(currencyName);
        po.setPubTime(DateUtil.parse("2020-06-01", "yyyy-MM-dd"));
        return po;
    }

    /**
     * TODO 应该放在工具类
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<String> rangeDate(Date beginTime, Date endTime) {
        if (!endTime.after(beginTime)) {
            throw new RuntimeException("now before startDate");
        }

        //两个时间段之间相差多少天
        int rangeDay = (int) ((endTime.getTime() - beginTime.getTime()) / (24*60*60*1000));
        List<String> rangeDate = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginTime);
        for (int i = 0; i <= rangeDay; i++) {
            rangeDate.add(DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
            cal.add(Calendar.DATE, 1);
        }

        return rangeDate;
    }
}
