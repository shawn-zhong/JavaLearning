package com.tseong.learning.patterns._13_decorator;

public class DecoratorPatternDemo {

    public static void main(String[] args) {

        Shape circle = new Circle();
        Shape redCircle = new RedShapeDecorator(new Circle());
        Shape redRectangle = new RedShapeDecorator( new Rectangle());

        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }
}


/*

Decorator pattern allows a user to add new functionality to an existing object without altering its structure.
This pattern creates a decorator class which wraps the original class and provides additional functionality keeping class
methods signature intact.

比如JVM中使用colletions.synchornized(hashmap)也是装修模式

 */