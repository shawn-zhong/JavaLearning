package com.tseong.learning.advance._01_singleton;

// 枚举enum和静态代码块的特性相似，在使用枚举时，构造方法会被自动调用，利用这一特性也可以实现单例：

enum EnumFactory {  // public in another file

    singletonFactory;

    private Singleton05 instance;

    private EnumFactory() { // //枚举类的构造方法在类加载是被实例化
        instance = new Singleton05();
    }

    public Singleton05 getInstance() {
        return instance;
    }
}

class Singleton05 {
    public Singleton05(){}
}

public class _05_SingletonDemo {
    public static void main(String[] args) {
        int hashcode = EnumFactory.singletonFactory.getInstance().hashCode();
        System.out.println(EnumFactory.singletonFactory.getInstance().hashCode());

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                int code = EnumFactory.singletonFactory.getInstance().hashCode();
                if (code != hashcode)
                    System.out.println("ouch! created more than 2 instances for _01_singleton: " + code);
            }).start();
        }
    }
}

// 运行结果表明单例得到了保证，但是这样写枚举类被完全暴露了，据说违反了“职责单一原则”，那我们来看看怎么进行改造呢 -> 06