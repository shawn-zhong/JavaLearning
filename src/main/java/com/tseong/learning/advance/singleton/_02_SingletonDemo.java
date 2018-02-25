package com.tseong.learning.advance.singleton;

class Singleton02 {

    // 使用volatile关键字保证其可见性/防止指令重排序
    private volatile static Singleton02 instance = null;

    private Singleton02() {}

    public static Singleton02 getInstance() {
        // 第一次检查
        if (instance == null) {
            synchronized (Singleton02.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new Singleton02();
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
                    System.out.println("ouch! created more than 2 instances for singleton");
            }).start();
        }
    }
}
