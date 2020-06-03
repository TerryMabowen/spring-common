package com.mbw.common.core.crawler.test2;

import com.mbw.common.core.crawler.boc.ExchangeRateCrawlerJob;
import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 10:27
 */
public class Test2 {

    @Test
    public void f1() {
        ExchangeRateCrawlerJob job = new ExchangeRateCrawlerJob();
        job.ExecuteExchangeRateCrawlerTask();
    }
}
