package com.mbw.common.core.crawler.boc;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 中国银行爬虫管道，将爬取的数据入库保存
 *
 * @author Mabowen
 * @date 2020-06-03 14:21
 */
public class ExchangeRateCrawlerPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if ("tds".equals(entry.getKey())) {
                ExchangeRateCrawlerData crawlerData = (ExchangeRateCrawlerData)entry.getValue();
                //设置PO,入库操作
                ExchangeRatePO po = new ExchangeRatePO();
                po.setCurrencyName(crawlerData.getCurrencyName());
                if (EnumCurrencyPair.MEI_YUAN.getValue().equals(po.getCurrencyName())) {
                    po.setCurrencyPair(EnumCurrencyPair.MEI_YUAN.getDesc());
                } else if (EnumCurrencyPair.OU_YUAN.getValue().equals(po.getCurrencyName())) {
                    po.setCurrencyPair(EnumCurrencyPair.OU_YUAN.getDesc());
                } else if (EnumCurrencyPair.RI_YUAN.getValue().equals(po.getCurrencyName())) {
                    po.setCurrencyPair(EnumCurrencyPair.RI_YUAN.getDesc());
                }
                BigDecimal exchangeRate = crawlerData.getMiddleRate().divide(BigDecimal.valueOf(100), 8, BigDecimal.ROUND_HALF_UP);
                po.setExchangeRate(exchangeRate);
                po.setPubTime(crawlerData.getPubTime());

                System.out.println(po.toString());
            }
        }
    }
}
