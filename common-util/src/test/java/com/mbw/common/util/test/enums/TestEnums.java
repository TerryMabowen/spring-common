package com.mbw.common.util.test.enums;

import org.junit.Test;

import java.util.EnumSet;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-24 13:12
 */
public class TestEnums {

    @Test
    public void f1() {

    }

    @Test
    public void f2() {
        EnumSet<EnumGroupTest> group1 = EnumSet.noneOf(EnumGroupTest.class);
        group1.add(EnumGroupTest.EXPENSES_CLAIMS);
        group1.add(EnumGroupTest.PAYMENT_APPLY);
        group1.add(EnumGroupTest.MONEY_RETURN);
        group1.add(EnumGroupTest.ADVANCES_PAYMENT_FOR_REVERSE);
        for (EnumGroupTest enumGroupTest : group1) {
            System.out.println(enumGroupTest.getDesc());
        }
    }
}
