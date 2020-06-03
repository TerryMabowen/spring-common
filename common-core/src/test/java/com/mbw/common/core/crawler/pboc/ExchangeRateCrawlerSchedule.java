package com.mbw.common.core.crawler.pboc;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * 汇率爬虫定时任务
 *
 * @author Mabowen
 * @date 2020-06-02 17:16
 */
public class ExchangeRateCrawlerSchedule {

    @Scheduled(cron = "0 0 10 * * ?")
    public void exchangeRateCrawlerTask() {

    }
}
