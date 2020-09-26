package com.tseong.learning.Features;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class _01_AnnotationDemo {

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException {

        _01_AnnotationDemo instance = new _01_AnnotationDemo();
        instance.annotationDemo();
    }

    public void annotationDemo() throws ClassNotFoundException, NoSuchMethodException {
        MyAnnotationDemo demo = new MyAnnotationDemo();
        Class<?> cls = Class.forName("com.tseong.learning.Features.MyAnnotationDemo");
        boolean flag = cls.isAnnotationPresent(MyAnnotation1.class);
        if (flag) {
            System.out.println("yes, it is an annotation1 class");
            MyAnnotation1 annotation1 = cls.getAnnotation(MyAnnotation1.class);
            System.out.println(annotation1.value());
        }

        Method method = cls.getMethod("sayHello");
        flag = method.isAnnotationPresent(MyAnnotation2.class);
        if (flag) {
            System.out.println("eys, it is an annotation2 class");
            MyAnnotation2 myAnnotation2 = method.getAnnotation(MyAnnotation2.class);
            System.out.println(myAnnotation2.description() + "\t" + myAnnotation2.isAnnotation());
        }
    }


}

@MyAnnotation1("this is annotiation1 value")
 class MyAnnotationDemo {

    @MyAnnotation2(description = "this is annotation2", isAnnotation = true)
    public void sayHello() {
        System.out.println("hello there");
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface MyAnnotation1 {
    String value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
 @interface MyAnnotation2 {
    String description();
    boolean isAnnotation();
}
