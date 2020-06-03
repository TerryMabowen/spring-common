package com.mbw.common.core.crawler.boc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货币对枚举
 *
 * @author Mabowen
 * @date 2020-06-03 19:51
 */
@Getter
@AllArgsConstructor
public enum EnumCurrencyPair {
    MEI_YUAN("美元", "美元/人民币"),

    OU_YUAN("欧元", "欧元/人民币"),

    RI_YUAN("日元", "日元/人民币");

    private String value;

    private String desc;
}
