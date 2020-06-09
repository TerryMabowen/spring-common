package com.mbw.commons.test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子性测试
 *
 * @author Mabowen
 * @date 2020-06-09 19:36
 */
public class AtomicTest {
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    private static AtomicLong atomicLong = new AtomicLong(0L);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        //1 AtomicInteger
        int count1 = atomicInteger.addAndGet(1); // 1 先加1
        System.out.println(count1);
        int count2 = atomicInteger.getAndAdd(1); // 1 先获取，获取上面加后的1
        System.out.println(count2);
    }
}
