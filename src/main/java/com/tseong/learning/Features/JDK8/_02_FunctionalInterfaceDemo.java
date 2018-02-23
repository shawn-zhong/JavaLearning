package com.tseong.learning.Features.JDK8;

public class _02_FunctionalInterfaceDemo {

    public static void main(String[] args) {
        _02_FunctionalInterfaceDemo instance = new _02_FunctionalInterfaceDemo();
        instance.testDemoA();
        instance.testDemoB();
    }

    public void testDemoA() {

        MyInterface myInterface = System.out::println;  // 或者下面这种写法复杂些：
        // MyInterface myInterface =  (String s) -> {System.out.println(s);};

        myInterface.me("hello from shawn");
    }

    public void testDemoB() {

        MyInterface2 myInterface2 = (a,b)-> a * b;

        double a = myInterface2.funA(10, 3);
        double b = myInterface2.funB(12, 4);

        System.out.println("a, b : " + a + " " + b);
    }
}

@FunctionalInterface
interface MyInterface {
    public void me(String s);   // 实现函数式接口，只能有一个非default方法
}


// 示意在接口中实现一个默认方法:
@FunctionalInterface
interface MyInterface2 {
    double funA(int a, int b);

    default double funB(int a, int b) {
        return Math.sqrt(a + b);
    }
}
