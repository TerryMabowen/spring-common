package com.mbw.common.test.bean;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-15 13:46
 */
public class BeanTest {

    @Test
    public void f1() {
        A source = new A();
        source.setId(1);
        A copy1 = new A();
        BeanUtils.copyProperties(source, copy1);
        A copy2 = new A();
        BeanUtils.copyProperties(source, copy2);
        copy1.getMap().put(1,"1");
        copy2.getMap().put(1,"2");
        System.out.println(copy1.getMap().get(1));
        //以为是"1"，实际是"2"
        System.out.println(copy2.getMap().get(1));
        //"2"
        System.out.println(copy1.getMap() == copy2.getMap());
        //true，直接对比引用
    }
}
