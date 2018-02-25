package com.tseong.learning.advance.classloading._01_StaticAndExtends;

// 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化

 class ConstClass {

    static {
        System.out.println("ConstClass init!");
    }

    public static final int CONSTANT = 1;
}

public class _02_Demo {

     public static void main(String[] args) {
         System.out.println(ConstClass.CONSTANT);   // 只输出1
     }
}
