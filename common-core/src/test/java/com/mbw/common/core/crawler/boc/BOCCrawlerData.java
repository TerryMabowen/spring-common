package com.mbw.common.core.crawler.boc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 中国银行数据爬取
 *
 * @author Mabowen
 * @date 2020-06-02 13:57
 */
@Data
@EqualsAndHashCode
@ToString
public class BOCCrawlerData {
    /**
     * 货币名称
     */
    private String currencyName;

    /**
     * 中行折算价
     */
    private BigDecimal middleRate;

    /**
     * 发布时间
     */
    private Date pubTime;

    /**
     * 现汇买入价
     */
    private BigDecimal buyingRate;

    /**
     * 现钞买入价
     */
    private BigDecimal cashBuyingRate;

    /**
     * 现汇卖出价
     */
    private BigDecimal sellingRate;

    /**
     * 现钞卖出价
     */
    private BigDecimal cashSellingRate;

}
