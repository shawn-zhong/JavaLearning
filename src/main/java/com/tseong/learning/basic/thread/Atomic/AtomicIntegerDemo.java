package com.tseong.learning.thread.Atomic;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicIntegerDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(3); // 無參數傳入，默認值為0

        System.out.println("Get init value of atomicInteger : " + atomicInteger.get());

        atomicInteger.set(5);

        System.out.println("Get value of atomicInteger : " + atomicInteger.get());

        int expectedVal = 5;
        int newVal = 8;

        Boolean succeed = atomicInteger.compareAndSet(expectedVal, newVal);
        System.out.println("setting succeed : " + succeed);
        System.out.println("Get value of atomicInteger : " + atomicInteger.get());


        int oldVal = atomicInteger.getAndAdd(1);
        System.out.println("before: " + oldVal + " new val: " + atomicInteger.get());

        newVal = atomicInteger.addAndGet(1);
        System.out.println("new val : " + newVal);

        newVal = atomicInteger.incrementAndGet();
        System.out.println("new val : " + newVal);

        newVal = atomicInteger.decrementAndGet();
        System.out.println("new val : " + newVal);


        AtomicLong atomicLong = new AtomicLong();
        long longVal = atomicLong.decrementAndGet();
        System.out.println("long val : " + longVal);
    }

}
