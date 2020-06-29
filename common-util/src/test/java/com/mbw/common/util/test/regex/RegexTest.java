package com.mbw.common.util.test.regex;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * TODO
 * [a-zA-Z]|[0-9]|[u4e00-u9fa5]
 * ^[a-z0-9A-Z\u4e00-\u9fa5]+$ 匹配字符串中仅有大小写英文字母，数字和汉字
 * @author Mabowen
 * @date 2020-06-29 09:48
 */
public class RegexTest {

    @Test
    public void f1() {
        Pattern pattern = Pattern.compile("^[a-z0-9A-Z\u4e00-\u9fa5]+$");
        String str = "sdffef343我们";
        System.out.println(pattern.matcher(str).matches());
    }
}
