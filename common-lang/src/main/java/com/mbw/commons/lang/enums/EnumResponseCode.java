package com.mbw.commons.lang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-25 19:58
 */
@Getter
@AllArgsConstructor
public enum EnumResponseCode implements BaseEnumStatus<Integer>{
    /**
     * 未知状态
     */
    UNKNOWN(40000, "未知错误");


    private Integer value;

    private String desc;
}
