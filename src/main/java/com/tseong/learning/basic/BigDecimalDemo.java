package com.tseong.learning.basic;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalDemo {

    public static void main(String[] args) {
        BigDecimal decimalA = new BigDecimal("3.1415926");  //
        //BigDecimal decimalWrong = new BigDecimal(3.1415926);   // 这样可能会有精度丢失，建议上面格式或者 BigDecimal.valueof
        BigDecimal decimalB = decimalA;
        decimalB.subtract(new BigDecimal("3")); // 做减法

        System.out.println( new DecimalFormat("#.00").format(decimalA));    // 看起来是简单的四舍五入
        System.out.println( new DecimalFormat("#.00").format(decimalB));

        //

        BigDecimal decimal = new BigDecimal("1.12345");
        System.out.println(decimal);
        BigDecimal setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        setScale = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        System.out.println(setScale);


        // 引用传递
        int[] arrayA = new int[10];
        modifyArray(arrayA);
        arrayA[0] = 1;

       System.out.println("length of ArrayA: " + arrayA.length);
       for (int i : arrayA) {
           System.out.println(i);
       }

    }

    private static void modifyArray(int[] arrayB) {
        int[] b = new int[20];
        b[0] = 10;
        b[1] = 20;

      //  arrayB = b;
    }
}
