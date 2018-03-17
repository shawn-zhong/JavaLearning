package com.tseong.learning.others.jmx;

public class Hello implements HelloMBean {

    private String message = null;

    public Hello() {
        message = "Helloworld from canton";
    }

    public Hello(String message) {
        this.message = message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
        System.out.println("now setting the value of JMXdemo");
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
