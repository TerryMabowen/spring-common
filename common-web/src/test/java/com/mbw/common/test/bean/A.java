package com.mbw.common.test.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-15 13:46
 */
@Data
public class A {
    private Integer id;
    private Map<Integer,String> map = new HashMap<>();
}
