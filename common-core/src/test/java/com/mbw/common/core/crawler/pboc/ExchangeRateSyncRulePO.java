package com.mbw.common.core.crawler.pboc;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-02 17:47
 */
@Data
public class ExchangeRateSyncRulePO {
    private Long id;

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
