package com.tseong.learning.advance._01_singleton;

class Singleton01 {
    private static final _01_SingletonDemo instance = new _01_SingletonDemo();

    private Singleton01() {
    }

    public static _01_SingletonDemo getInstance() {
        return instance;
    }
}

public class _01_SingletonDemo {

    public static void main(String[] args) {

        int hashcode = Singleton02.getInstance().hashCode();
        System.out.println(Singleton02.getInstance().hashCode());

        for (int i=0; i<10000; i++) {
            new Thread(()->{
                int hc = Singleton02.getInstance().hashCode();
                if ( hc != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton");
            }).start();
        }
    }
}
