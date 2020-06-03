package com.mbw.common.core.crawler.boc;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 19:47
 */
@Data
@ToString
public class ExchangeRatePO {
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
