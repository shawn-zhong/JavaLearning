package com.tseong.learning.advance._01_singleton;

class Singleton02 {

    // 使用volatile关键字保证其可见性/防止指令重排序
    private volatile static Singleton02 instance = null;

    /*

    volatiel 主要是禁止重排序，初始化一个实例（SomeType st = new SomeType()）在java字节码中会有4个步骤，
    申请内存空间，
    初始化默认值（区别于构造器方法的初始化），
    执行构造器方法
    连接引用和实例。

    下面2和3的顺序不定：

    1. inst = allocat()；       // 分配内存
    2. sSingleton = inst;      // 赋值
    3. constructor(inst);      // 真正执行构造函数
    4. 初次访问

    加入一个线程运行到上面到2，还没执行3，另一个线程进入下面代码4，此时instance不为null，将返回一个没有初始化到类！ 加上volatile没事
     */

    private Singleton02() {}

    public static Singleton02 getInstance() {       // 3
        // 第一次检查
        if (instance == null) {                     // 4
            synchronized (Singleton02.class) {      // 5
                // 第二次检查
                if (instance == null) {             // 6
                    instance = new Singleton02();   // 7
                }
            }
        }

        return instance;
    }
}

public class _02_SingletonDemo {
    public static void main(String[] args) {

        int hashcode = Singleton02.getInstance().hashCode();
        System.out.println(Singleton02.getInstance().hashCode());

        for (int i=0; i<1000; i++) {
            new Thread(()->{
                if (Singleton02.getInstance().hashCode() != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton");
            }).start();
        }
    }
}
