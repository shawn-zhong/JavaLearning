package com.tseong.learning.patterns._15_flyweight;

public class FlyweightPatternDemo {

    private static final String colors[] = { "Red", "Green", "Blue", "White", "Black" };

    public static void main(String[] args) {

        for (int i=0; i<20; ++i) {
            Circle circle = (Circle)ShapeFactory.getCircle(getRandomColor());
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }
    }

    private static String getRandomColor() {
        return colors[(int)(Math.random()*colors.length)];
    }

    private static int getRandomX() {
        return (int)(Math.random()*100);
    }

    private static int getRandomY() {
        return (int)(Math.random()*100);
    }
}


/*

Flyweight pattern is primarily used to reduce the number of objects created and to decrease memory footprint and increase performance.

Flyweight pattern tries to reuse already existing similar kind objects by storing them and creates new object when no matching object is found

 */