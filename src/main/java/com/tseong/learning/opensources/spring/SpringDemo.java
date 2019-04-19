package com.tseong.learning.opensources.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
        HelloWorldInterface helloworld = (HelloWorldInterface) context.getBean("greeting");
        System.out.println(helloworld.sayHello());
    }
}

// spring的bean加载过程：https://www.cnblogs.com/yxh1008/p/6012230.html

// https://www.jianshu.com/p/3b63885f61bb