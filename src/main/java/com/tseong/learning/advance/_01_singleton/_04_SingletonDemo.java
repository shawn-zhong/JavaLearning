package com.tseong.learning.advance._01_singleton;

// 静态代码块中的代码在使用类的时候就已经执行了，所以可以应用静态代码块的这个特性的实现单例设计模式。
class Singleton04 {

    private static Singleton04 instance = null;

    private Singleton04() {}

    static {
        instance = new Singleton04();
    }

    public static Singleton04 getInstance() {
        return instance;
    }
}

public class _04_SingletonDemo {
    public static void main(String[] args) {
        int hashcode = Singleton04.getInstance().hashCode();
        System.out.println(Singleton04.getInstance().hashCode());

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int code = Singleton04.getInstance().hashCode();
                if (code != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton: " + code);
            }).start();
        }
    }
}
