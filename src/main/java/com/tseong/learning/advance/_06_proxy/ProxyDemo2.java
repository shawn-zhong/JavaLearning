package com.tseong.learning.advance._06_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/*
在动态代理中，代理类及其实例是程序自动生成的，因此我们不需要手动去创建代理类。
在Java的动态代理机制中，InvocationHandler(Interface)接口和Proxy(Class)类是实现我们动态代理所必须用到的。
事实上，Proxy通过使用InvocationHandler对象生成具体的代理代理对象

1.代理对象,不需要实现接口
2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
3.动态代理也叫做:JDK代理,接口代理


 */
interface Subject {
    public void doSomething();
}

class RealSubject implements Subject {

    @Override
    public void doSomething() {
        System.out.println("call doSomething()");
    }
}

class ProxyHandler implements InvocationHandler {

    private Object proxied;

    public ProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 在调用目标对象之前，可以执行一些功能处理
        System.out.println("前置增强处理: yoyoyo ...");

        // 调用目标对象的方法（三要素: 实例对象 + 实例方法 + 实例方法的参数）
        Object obj = method.invoke(proxied, args);

        // 在调用目标对象之后，可以执行一些功能处理
        System.out.println("后置增强处理: hahaha...");

        return obj;
    }
}

public class ProxyDemo2 {

    public static void main(String ... args) {

        // 真实对象real
        Subject real = new RealSubject();

        // 生成real的代理对象
        /*
        Subject proxySubject = (Subject) Proxy.newProxyInstance(
                Subject.class.getClassLoader(), new Class[] { Subject.class },
                new ProxyHandler(real));*/

        Subject proxySubject = (Subject) Proxy.newProxyInstance(
                real.getClass().getClassLoader(), real.getClass().getInterfaces(),
                new ProxyHandler(real)
        );

        proxySubject.doSomething();
        System.out.println("代理对象的类型: " + proxySubject.getClass().getName());
        System.out.println("代理对象所在类的父类型: " + proxySubject.getClass().getGenericSuperclass());
    }
}


/*

JDK代理的实现过程：

1. 获取Subject上到所有接口列表
2. 确定要生成的代理类的类名
3. 根据需要实现的接口信息，在代码中动态创建该Proxy类的字节码
4. 将对应的字节码转换为对应的class对象
5. 创建InvocationHandler实例handler，用来处理Proxy所有方法调用
6. Proxy的class对象以创建的handler对象为参数，实例化一个proxy对象

http://blog.csdn.net/xiaokang123456kao/article/details/77679848

 */