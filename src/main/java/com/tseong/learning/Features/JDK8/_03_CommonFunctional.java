package com.tseong.learning.Features.JDK8;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class _03_CommonFunctional {

    public static void main(String[] args) {

        _03_CommonFunctional instance = new _03_CommonFunctional();
        instance.consumerTest();
        instance.supplierTest();
        instance.functionTest();
        instance.predicateTest();
    }

    public void consumerTest() {
        // Consumer接口表示执行在单个参数上的操作 (接受一个参数，没有返回值)

        Consumer<String> myConsumer = p -> System.out.println("place " + p);

        myConsumer.accept("China");     // 注意consumer的accept是返回void
        myConsumer.accept("Europe");

        Consumer<String> myConsumer2 = n -> System.out.println("Welcome " + n + " to " );
        myConsumer2.accept("shawn");    // Welcome shawn to
        myConsumer2.andThen(myConsumer).accept("shawn"); // 打印两行如下
        // Welcome shawn to
        // place shawn

        System.out.println();
    }

    public void supplierTest() {
        // Supplier接口返回一个任意泛型的值，和function接口不同的是该接口没有任何参数
        /*Supplier<String> mySupplier = new Supplier<String>() {
            @Override
            public String get() {
                return "Just a demo";
            }
        };*/ // 简化成下面的lambda表达式

        Supplier<String> mySupplier = () -> "Just a demo";
        String result = mySupplier.get();

        System.out.println(result);
        System.out.println(mySupplier.get());

        System.out.println();
    }

    public void functionTest() {
        // Function接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）
        // Function<T, R> -> T是输入参数类型，R是返回参数类型
        Function<Integer, String> toPower = n -> String.format("%d * %d = ", n, n) + String.valueOf(n * n); // 输入一个数字，返回平方值的描述

        Function<String, String> toDescribe = n -> "having result : " + n;

        // 下面两句执行结果相同
        System.out.println(toPower.andThen(toDescribe).apply(5));
        System.out.println(toDescribe.compose(toPower).apply(5));

        System.out.println(toPower.andThen(n -> "final result : " + n).apply(5));

        // toPower的写法等同于
        /*
        toPower = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.format("%d * %d = ", integer, integer) + String.valueOf(integer * integer);
            }
        };*/

        System.out.println();
    }

    public void predicateTest() {
        // predicate接口只有一个参数，返回boolean类型。该接口包含多种默认方法将Predicate组合成其他复杂的逻辑

        Predicate<String> myPredicate = s->s.length()>0;
        boolean outcome1 = myPredicate.test("foo");             // true
        boolean outcome2 = myPredicate.negate().test("foo");    // false

        System.out.println("predicate : " + outcome1 + " " + outcome2);

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

    }
}
