package com.tseong.learning.basic.thread.Atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author: Shawn ZHONG @date: 2020-09-23 13:57
 */
public class _03_AtomicIntegerArrayDemo {

    public static void main(String[] args) {

        int[] value = {1,2,3,6,7,8};
        AtomicIntegerArray atomicArray = new AtomicIntegerArray(value);

        boolean succeed = atomicArray.compareAndSet(0, 1, 11);
        System.out.println("succeed: " +succeed);   // true

        System.out.println("getAndAdd: " + atomicArray.getAndAdd(0, 10));   // 11

        System.out.println("addAndGet: " + atomicArray.addAndGet(0, 20));   // 41

    }
}
