package com.tseong.learning.advance._06_proxy;

/*
上面的静态代理和动态代理模式都是要求目标对象是实现一个接口的目标对象,但是有时候目标对象只是一个单独的对象,并没有实现任何的接口,这个时候就可以使用以目标对象子类的方式类实现代理,这种方法就叫做:Cglib代理
Cglib代理,也叫作子类代理,它是在内存中构建一个子类对象从而实现对目标对象功能的扩展.
*/

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// 被代理类
class HelloServiceImpl {

    public void sayHello() {
        System.out.println("Hello ShawnZHONG");
    }

    public void sayGoodbye() { System.out.println("Good bye");}
}

class HelloMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before:" + method.getName());
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("After:" + method.getName());
        return object;
    }
}

public class ProxyDemo3 {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);     // 继承被代理类
        enhancer.setCallback(new HelloMethodInterceptor()); // 设置回调
        HelloServiceImpl helloService = (HelloServiceImpl) enhancer.create();   // 生成代理对象

        helloService.sayHello();    // 在调用代理类中方法时会被我们实现的方法拦截器进行拦截
        helloService.sayGoodbye();
    }
}


// http://ifeve.com/jdk动态代理代理与cglib代理原理探究/