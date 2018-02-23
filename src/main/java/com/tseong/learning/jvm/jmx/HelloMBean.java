package com.tseong.learning.jvm.jmx;

public interface HelloMBean {
    void setMessage(String message);
    String getMessage();
    void sayHello();
}
