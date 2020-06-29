package com.mbw.common.util.test.enums;

import com.mbw.commons.lang.enums.BaseEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-24 13:09
 */
@Getter
@AllArgsConstructor
public enum EnumGroupTest implements BaseEnumStatus<Integer> {
    EXPENSES_CLAIMS(1, "费用报销"),
    PAYMENT_APPLY(2, "付款申请"),
    MONEY_RETURN(3, "直营退费"),
    ADVANCES_PAYMENT_FOR_REVERSE(4, "借支/付款申请(冲账专用)"),
    ADVANCES_PAYMENT_APPLY(5, "借支/付款申请"),
    ADVANCES_FOR_EMPLOYEE(6, "员工借支"),
    ADVANCES_FOR_PERSONNAL(7, "个人借支"),
    ADVANCES_FOR_PERSONNAL_REVERSE(8, "个人借支(冲账专用)"),
    PAYMENT_FOR_REVERSE(9, "付款申请(冲账专用)"),
    ADVANCES_PAYMENT_REVERSE(10, "借支/付款(冲账专用)"),
    ADVANCES_APPLY(11, "借支申请"),
    PURCHASE_APPLY(12, "采购申请");

    private Integer value;

    private String desc;

    public static EnumGroupTest[] getGroup1() {
        return new EnumGroupTest[] {EXPENSES_CLAIMS, PAYMENT_APPLY, MONEY_RETURN,};
    }

    public static EnumGroupTest[] getGroup2() {
        return new EnumGroupTest[] {ADVANCES_PAYMENT_REVERSE, ADVANCES_APPLY, PURCHASE_APPLY,};
    }
}
