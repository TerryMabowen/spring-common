package com.mbw.common.core.crawler.boc;

import com.mbw.commons.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 中国银行汇率页面数据爬取处理器
 * 每个币种每天只爬取一页数据，当天的汇率一致，所以只需要一条数据即可，
 * 需要注意的是中国银行当天汇率是在早上9点半更新，所以爬虫任务最好在10点后执行
 * @author Mabowen
 * @date 2020-06-03 10:27
 */
public class ExchangeRatePageProcessor implements PageProcessor {

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(3000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable trs = html.$("div[class='wrapper'] > div[class='BOC_main publish'] > table > tbody > tr");
        if (trs != null) {
            List<Selectable> trNodes = trs.nodes();
            if (trNodes != null && trNodes.size() > 1) {
                Selectable trSelectable = trNodes.get(1);
                Selectable tdSelectable = trSelectable.$("td");
                List<String> tds = tdSelectable.all();
                if (tds != null && tds.size() == 7) {
                    ExchangeRateCrawlerData bocCrawlerData = new ExchangeRateCrawlerData();
                    String currencyName = parseTd(tds.get(0));
                    String buyingRate = parseTd(tds.get(1));
                    String cashBuyingRate = parseTd(tds.get(2));
                    String sellingRate = parseTd(tds.get(3));
                    String cashSellingRate = parseTd(tds.get(4));
                    String middleRate = parseTd(tds.get(5));
                    String pubTime = parseTd(tds.get(6));
                    bocCrawlerData.setCurrencyName(currencyName);
                    bocCrawlerData.setBuyingRate(convertStrToBigDecimal(buyingRate));
                    bocCrawlerData.setCashBuyingRate(convertStrToBigDecimal(cashBuyingRate));
                    bocCrawlerData.setSellingRate(convertStrToBigDecimal(sellingRate));
                    bocCrawlerData.setCashSellingRate(convertStrToBigDecimal(cashSellingRate));
                    bocCrawlerData.setMiddleRate(convertStrToBigDecimal(middleRate));
                    bocCrawlerData.setPubTime(DateUtil.parse(pubTime, "yyyy.MM.dd"));
                    page.putField("tds", bocCrawlerData);
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String parseTd(String td) {
        if (StringUtils.isBlank(td)) {
            return StringUtils.EMPTY;
        }
        return td.replace("<td>", "").replace("</td>", "");
    }

    private BigDecimal convertStrToBigDecimal(String str) {
        if (StringUtils.isBlank(str)) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(str);
    }
}
