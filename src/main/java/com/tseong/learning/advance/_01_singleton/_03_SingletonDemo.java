package com.tseong.learning.advance._01_singleton;

// 使用静态内置类实现单例模式
class Singleton03 {

    // 静态私有内部类
    private static class InnerClass {
        private final static Singleton03 instance = new Singleton03();
    }

    private Singleton03() { }

    public static Singleton03 getInstance() {
        return InnerClass.instance;
    }
}

public class _03_SingletonDemo {

    public static void main(String[] args) {
        int hashcode = Singleton03.getInstance().hashCode();
        System.out.println(Singleton03.getInstance().hashCode());

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int code = Singleton03.getInstance().hashCode();
                if (code != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton: " + code);
            }).start();
        }
    }
}
