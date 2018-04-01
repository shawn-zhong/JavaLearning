package com.tseong.learning.basic.thread.Atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDemo {

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
