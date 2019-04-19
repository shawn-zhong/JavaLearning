package com.tseong.learning.advance._02_classloading._01_StaticAndExtends;

public class _04_Demo {



    public static void main(String[] args) {
        staticFuncClass.staticFunction();

        // 结果：
        /*
        static block
        static function
        */

        // 结论：调用类的静态方法，不会实例化这个类
    }
}


class staticFuncClass {

    static {
        System.out.println("static block");
    }

    {
        System.out.println("non-static block");
    }

    staticFuncClass() {
        System.out.println("constructor");

    }

    public static void staticFunction() {
        System.out.println("static function");
    }
}