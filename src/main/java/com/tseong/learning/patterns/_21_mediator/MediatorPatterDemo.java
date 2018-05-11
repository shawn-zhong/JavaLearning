package com.tseong.learning.patterns._21_mediator;

public class MediatorPatterDemo {

    public static void main(String[] args) {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi John!");
        john.sendMessage("Hello Robert!");
    }
}



/*

Mediator pattern is used to reduce communication complexity between multiple objects or classes. This pattern provides a mediator
class which normally handles all the communications between different classes and supports easy maintenance of the code by loose coupling.

 */