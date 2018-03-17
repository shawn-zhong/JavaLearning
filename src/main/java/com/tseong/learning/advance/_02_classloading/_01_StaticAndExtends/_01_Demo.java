package com.tseong.learning.advance._02_classloading._01_StaticAndExtends;

public class _01_Demo {

    public static void main(String[] args) {
        // test-01:
        System.out.println(SubClass.value); // test-01 : 通过子类引用父类的静态字段，不会导致子类初始化

        // test-01 result
        /*
        SSClass-static
        SClass-static
        123
        */

        // test-02:
        SubClass instance = new SubClass();
        instance.toString();

        // test-02 result: 创建一个对象常常需要经历如下几个过程：父类的类构造器<clinit>() -> 子类的类构造器<clinit>() -> 父类的成员变量和实例代码块 -> 父类的构造函数 -> 子类的成员变量和实例代码块 -> 子类的构造函数
        /*
        SSClass-static
        SClass-static
        SubClass-static
        SSClass-nonstatic
        SSClass-constructor
        SClass-nonstatic
        SClass-constructor
        SubClass-nonstatic
        SubClass-constructor
        */

        // test-03:通过数组定义来引用类，不会触发此类的初始化
        // 案例运行之后并没有任何输出，说明虚拟机并没有初始化类SClass。但是，这段代码触发了另外一个名为[Lcn.edu.tju.rico.SClass的类的初始化
        //SClass[] sca = new SClass[0];
    }
}

class SSClass {
    static {
        System.out.println("SSClass-static");
    }

    {
        System.out.println("SSClass-nonstatic");
    }

    public SSClass() {
        System.out.println("SSClass-constructor");
    }
}


class SClass extends SSClass {

    public static int value = 123;

    static {
        System.out.println("SClass-static");
    }

    {
        System.out.println("SClass-nonstatic");
    }

    public SClass() {
        System.out.println("SClass-constructor");
    }
}

class SubClass extends SClass {
    static {
        System.out.println("SubClass-static");
    }

    {
        System.out.println("SubClass-nonstatic");
    }

    public SubClass() {
        System.out.println("SubClass-constructor");
    }
}