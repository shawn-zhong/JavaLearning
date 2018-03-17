package com.tseong.learning.opensources.thrift.client;

import com.tseong.learning.opensources.thrift.HelloWorldService;
import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

    public String sayHello(String username) throws TException {
        return "Hi " + username + " welcome to thrift world";
    }

}
