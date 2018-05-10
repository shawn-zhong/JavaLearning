package com.tseong.learning.patterns._16_proxy;

public class ProxyPatternDemo {

    public static void main(String[] args) {
        Image image = new ProxyImage("test.jpg");

        // image will be loaded from disk
        image.display();

        // image will not be loaded from disk
        image.display();
    }
}


/*

In proxy pattern, a class represents functionality of another class.
In proxy pattern, we create object having original object to interface its functionality to outer world.

 */