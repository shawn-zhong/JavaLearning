package com.tseong.learning.advance._02_classloading._01_StaticAndExtends;

class Testclass {

    static Testclass st = new Testclass(); // 也就是实例化StaticTest对象，但这个时候类都没有初始化完毕啊，能直接进行实例化吗？事实上，这涉及到一个根本问题就是：实例初始化不一定要在类初始化结束之后才开始初始化

    static {
        System.out.println("static block");
    }

    {
        System.out.println("non-static block");
    }

    Testclass() {
        System.out.println("constructor");

    }

    public static void staticFunction() {
        System.out.println("static function");
    }
}

public class _03_Demo {

    public static void main(String[] args) {
        Testclass.staticFunction();

        // 结果：
        /*
        non-static block
        constructor
        static block
        static function
        */

    }
}