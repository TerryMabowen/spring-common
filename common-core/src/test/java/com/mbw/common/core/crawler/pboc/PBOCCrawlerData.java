package com.mbw.common.core.crawler.pboc;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 中国人民银行爬虫数据
 *
 * @author Mabowen
 * @date 2020-06-02 16:44
 */
@Data
@ToString
public class PBOCCrawlerData {
    /**
     * 货币对
     */
    private String currencyPair;

    /**
     * 货币名称
     */
    private String currencyName;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 发布日期
     */
    private Date pubTime;
}
