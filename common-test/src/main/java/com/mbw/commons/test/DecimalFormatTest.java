package com.mbw.commons.test;

import java.text.DecimalFormat;

/**
 * Decimal格式化
 * 如金额千分位
 *
 * @author Mabowen
 * @date 2020-06-08 16:56
 */
public class DecimalFormatTest {

    public static void main(String[] args) {
        //示例一：最多保留两位小数
        System.out.println("--------###,###.##---------------");
        System.out.println(new DecimalFormat("###,###.##").format(1231111.58880));//多的四舍五入
        System.out.println(new DecimalFormat("###,###.##").format(01111.58880));//多的四舍五入
        System.out.println(new DecimalFormat("###,###.##").format(01111.7844));//多的四舍五入
        System.out.println(new DecimalFormat("###,###.##").format(01112.98));
        System.out.println(new DecimalFormat("###,###.##").format(01113.80));
        System.out.println(new DecimalFormat("###,###.##").format(01114.8));
        System.out.println(new DecimalFormat("###,###.##").format(01115.00));
        System.out.println(new DecimalFormat("###,###.##").format(0.686));
        System.out.println(new DecimalFormat("###,###.##").format(0.784));
        System.out.println(new DecimalFormat("###,###.##").format(0.88));
        System.out.println(new DecimalFormat("###,###.##").format(0.8));
        System.out.println(new DecimalFormat("###,###.##").format(0));
        //首末有零自动舍去-小数位多的四舍五入

        //示例二：默认保留两位小数（注意：整数为0的小数问题）
        System.out.println("--------###,###.00---------------");
        System.out.println(new DecimalFormat("###,###.00").format(1111.5888));//多的四舍五入
        System.out.println(new DecimalFormat("###,###.00").format(1111.7844));//多的四舍五入
        System.out.println(new DecimalFormat("###,###.00").format(1112.98));
        System.out.println(new DecimalFormat("###,###.00").format(1113.80));
        System.out.println(new DecimalFormat("###,###.00").format(1114.8));//不足的末尾补零
        System.out.println(new DecimalFormat("###,###.00").format(1115.00));
        System.out.println(new DecimalFormat("###,###.00").format(0.686));
        System.out.println(new DecimalFormat("###,###.00").format(0.784));
        System.out.println(new DecimalFormat("###,###.00").format(0.88));
        System.out.println(new DecimalFormat("###,###.00").format(0.8));
        System.out.println(new DecimalFormat("###,###.00").format(0));
        //注意：整数为0的小数,会出现整数部分不存在的问题。

        //示例三：默认保留两位小数(解决示例二中，整数为0的小数格式化后整数0消失问题)
        System.out.println("--------###,##0.00---------------");
        System.out.println(new DecimalFormat("###,##0.00").format(1111.5888));
        System.out.println(new DecimalFormat("###,##0.00").format(1111.7844));
        System.out.println(new DecimalFormat("###,##0.00").format(1112.98));
        System.out.println(new DecimalFormat("###,##0.00").format(1113.80));
        System.out.println(new DecimalFormat("###,##0.00").format(1114.8));
        System.out.println(new DecimalFormat("###,##0.00").format(1115.00));
        System.out.println(new DecimalFormat("###,##0.00").format(0.686));
        System.out.println(new DecimalFormat("###,##0.00").format(0.784));
        System.out.println(new DecimalFormat("###,##0.00").format(0.88));
        System.out.println(new DecimalFormat("###,##0.00").format(0.8));
        System.out.println(new DecimalFormat("###,##0.00").format(0));
        //在格式化的个位上设置0，保证整数部分为0的时候自动补0。

        //示例四：默认保留两位小数，整数部分设置最小显示位数
        System.out.println("--------000,000.00---------------");
        System.out.println(new DecimalFormat("000,000.00").format(12222111.5888));
        System.out.println(new DecimalFormat("000,000.00").format(1111.5888));
        System.out.println(new DecimalFormat("000,000.00").format(1111.7844));
        System.out.println(new DecimalFormat("000,000.00").format(1112.98));
        System.out.println(new DecimalFormat("000,000.00").format(1113.80));
        System.out.println(new DecimalFormat("000,000.00").format(1114.8));
        System.out.println(new DecimalFormat("000,000.00").format(1115.00));
        System.out.println(new DecimalFormat("000,000.00").format(0.686));
        System.out.println(new DecimalFormat("000,000.00").format(0.784));
        System.out.println(new DecimalFormat("000,000.00").format(0.88));
        System.out.println(new DecimalFormat("000,000.00").format(0.8));
        System.out.println(new DecimalFormat("000,000.00").format(0));
        //在格式化的时候保证位数不足的时候自动补0，小数多的时候四舍五入

        String format = new DecimalFormat("###,##0.00").format(1114.8);
    }
}
