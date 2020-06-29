package com.mbw.common.util.test.enums;

import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-24 14:07
 */
public class GroupTest {

    @Test
    public void f1() {
        EnumGroupTest[] group1 = EnumGroupTest.getGroup1();
        for (EnumGroupTest enumGroupTest : group1) {
            System.out.println(enumGroupTest.getValue());
            System.out.println(enumGroupTest.getDesc());
        }
    }

    @Test
    public void f2() {
        EnumGroupTest[] group2 = EnumGroupTest.getGroup2();
        for (EnumGroupTest enumGroupTest : group2) {
            System.out.println(enumGroupTest.getValue());
            System.out.println(enumGroupTest.getDesc());
        }
    }
}
