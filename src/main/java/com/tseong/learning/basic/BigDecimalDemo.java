package com.tseong.learning.basic;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalDemo {

    public static void main(String[] args) {
        BigDecimal decimalA = new BigDecimal("3.1415926");
        BigDecimal decimalB = decimalA;
        decimalB.subtract(new BigDecimal("3"));

        System.out.println( new DecimalFormat("#.00").format(decimalA));    // 看起来是简单的四舍五入
        System.out.println( new DecimalFormat("#.00").format(decimalB));

        //

        BigDecimal decimal = new BigDecimal("1.12345");
        System.out.println(decimal);
        BigDecimal setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        System.out.println(setScale);

    }
}
