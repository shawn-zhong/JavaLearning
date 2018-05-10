package com.tseong.learning.patterns._08_prototype;

import java.util.Hashtable;

public class ShapeCache {

    private static Hashtable<String, Shape> shapeMap = new Hashtable<>();

    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(), circle);

        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(), square);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(), rectangle);
    }
}


/*

https://www.tutorialspoint.com/design_pattern/prototype_pattern.htm

Prototype pattern refers to creating duplicate object while keeping performance in mind
This pattern involves implementing a prototype interface which tells to create a clone of the current object.
This pattern is used when creation of object directly is costly. For example, an object is to be created after a costly
database operation. We can cache the object, returns its clone on next request and update the database as and when needed
thus reducing database calls.

 */