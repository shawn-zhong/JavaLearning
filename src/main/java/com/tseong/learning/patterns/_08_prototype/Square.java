package com.tseong.learning.patterns._08_prototype;

public class Square extends Shape {

    public Square() {
        type = "Squrare";
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
