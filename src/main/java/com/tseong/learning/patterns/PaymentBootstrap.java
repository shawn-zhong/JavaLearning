package com.tseong.learning.patterns;

import com.tseong.learning.patterns.templateMethod.AliPayProcessor;

public class PaymentBootstrap {

    public static void main(String[] args) {

        AliPayProcessor processor = new AliPayProcessor(); // set state

        processor.process();


    }
}
