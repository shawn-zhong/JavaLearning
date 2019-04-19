package com.tseong.learning.advance._02_classloading._02_Instancing;

public class _03_InstancingSequence {

    public static void main(String[] args) {
        SubClass instance = new SubClass();
        System.out.println("_01_Api : " + instance.getValue());    // running sequence : step-7
    }
}

class BaseClass {
    int i = 1;   // step-3.1

    // running sequence : step-4
    BaseClass() {
        System.out.println("BaseClass Constructor: i = " + i);          // 经过step-3, 此时i=2
        int x = getValue();                                             // 调用的是子类重载的getValue方法，此时j还没有初始化，所以返回0
        System.out.println("BaseClass Constructor: getValue() = " + x);
    }

    // running sequence : step-3.2
    {
        System.out.println("BaseClass non-static block, i=" +i);
        i = 2;
        System.out.println("BaseClass non-static block");
    }

    // running sequence : step-1
    static {
        System.out.println("BaseClass static block");
    }

    protected int getValue() {

        System.out.println("BaseClass getValue() , i = " + i);
        return i;
    }
}

class SubClass extends BaseClass {
    int j = 1;  // step-5.1

    // running sequence : step-6
    SubClass () {
        System.out.println("SubClass Constructor: j = " + j);                       // 经过step-6, 此时j的值为3
        System.out.println("SubClass Constructor: getValue() = " + getValue());     // 经过step-6, 此时j的值为3
    }

    // running sequence : step-5.2
    {
        j = 3;
        System.out.println("SubClass non-static block");
    }

    // running sequence : step-2
    static {
        System.out.println("SubClass static block");
    }

    @Override
    protected int getValue() {
        System.out.println("SubClass getValue() , i=" + i + " j=" + j);
        return j;
    }
}



// 输出:
/*

BaseClass static block
SubClass static block
BaseClass non-static block
BaseClass Constructor: i = 2
SubClass getValue() , i=2 j=0
BaseClass Constructor: getValue() = 0
SubClass non-static block
SubClass Constructor: j = 3
SubClass getValue() , i=2 j=3
SubClass Constructor: getValue() = 3
SubClass getValue() , i=2 j=3
_01_Api : 3

 */