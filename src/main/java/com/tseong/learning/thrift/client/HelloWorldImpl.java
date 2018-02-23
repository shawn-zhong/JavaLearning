package com.tseong.learning.thrift.client;

import com.tseong.learning.thrift.HelloWorldService;
import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

    public String sayHello(String username) throws TException {
        return "Hi " + username + " welcome to thrift world";
    }

}
