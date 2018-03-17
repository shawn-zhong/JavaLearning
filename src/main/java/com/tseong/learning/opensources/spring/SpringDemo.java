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
