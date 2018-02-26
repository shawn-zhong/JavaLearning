package com.tseong.learning.advance._01_singleton;

class ClassFactory {

    private enum MyEnumSingleton{
        singletonFactory;

        private Singleton06 instance;

        private MyEnumSingleton() {
            instance = new Singleton06();
        }

        public Singleton06 getInstance() {
            return instance;
        }
    }

    public static Singleton06 getInstance() {
        return MyEnumSingleton.singletonFactory.getInstance();
    }
}

class Singleton06 {
    public Singleton06(){}
}

public class _06_SingletonDemo {
    public static void main(String[] args) {
        int hashcode = ClassFactory.getInstance().hashCode();
        System.out.println(ClassFactory.getInstance().hashCode());

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int code = ClassFactory.getInstance().hashCode();
                if (code != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton: " + code);
            }).start();
        }
    }
}
