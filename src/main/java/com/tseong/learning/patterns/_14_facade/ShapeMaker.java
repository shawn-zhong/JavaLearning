package com.tseong.learning.patterns._14_facade;

public class ShapeMaker {

    private Shape circle;
    private Shape rectange;
    private Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectange = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectange.draw();
    }

    public void drawSquare() {
        square.draw();
    }
}
