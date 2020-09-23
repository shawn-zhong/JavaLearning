package com.tseong.learning.basic.thread.Atomic;

import java.util.concurrent.atomic.*;

public class _01_AtomicBooleanDemo {

    public static void main(String[] args) {

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean val1 = atomicBoolean.getAndSet(false);

        System.out.println("atomicBoolean.getAndSet val : " + val1);

        boolean expectedValue = false;
        boolean newValue = true;

        boolean setSucceed = atomicBoolean.compareAndSet(expectedValue, newValue);

        System.out.println("atomicBoolean.compareAndSet : " + setSucceed);
    }
}


/*
    经常用的Atomic包下的类有：AtomicBoolean, AtomicInteger, AtomicLong, AtomicIntegerArray, AtomicLongArray

 */