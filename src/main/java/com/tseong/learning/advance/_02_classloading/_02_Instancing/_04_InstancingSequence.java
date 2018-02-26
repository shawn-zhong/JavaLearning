package com.tseong.learning.advance._02_classloading._02_Instancing;

// 这个例子关键在于说明类的初始化操作和类的实例化操作可以是并行的，也就是类的实例化操作可以在初始化操作完成之前完成
public class _04_InstancingSequence {

    public static void main(String[] args) {
        staticFunction();
    }

    // 类初始化执行顺序－1 ：这是第一句赋值语句，“在类都没有初始化完毕之前，能直接进行实例化相应的对象吗？”
    // 实例初始化嵌入到了静态初始化流程中，并且在上面的程序中，嵌入到了静态初始化的起始位置。这就导致了实例初始化完全发生在静态初始化之前
    // 也就是说执行完这一句会打印：
    // non-static block
    // In Constructor
    // a=110 b=0  // -> 因为b是静态变量，类只完成实例化，没有完成初始化，所以b的值为0
    static _04_InstancingSequence st = new _04_InstancingSequence();

    // 类初始化执行顺序－2
    static {
        System.out.println("static block");
    }

    // 类的实例化执行顺序-1
    {
        System.out.println("non-static block");
    }

    // 类的实例化执行顺序-2
    public _04_InstancingSequence() {
        System.out.println("In Constructor");
        System.out.println("a=" + a + " b=" + b);
    }

    //
    public static void staticFunction() {
        System.out.println("In staticFunction(); b=" +b);
    }

    int a = 110;

    // 类初始化执行顺序－3
    static int b = 112;

    // 类初始化执行顺序－4
    // 此时b已经完成类初始化，已经有值－2
    // 执行到这一句，打印：
    // non-static block
    // In Constructor
    // a=110 b=112
    static _04_InstancingSequence st2 = new _04_InstancingSequence();
}