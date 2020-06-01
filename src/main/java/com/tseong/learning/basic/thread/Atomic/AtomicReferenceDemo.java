package com.tseong.learning.basic.thread.Atomic;

import java.util.concurrent.atomic.AtomicReference;

// 赋值操作不是线程安全的。若想不用锁来实现，可以用AtomicReference<V>这个类，实现对象引用的原子更新
// 使用场景：一个线程使用student对象，另一个线程负责定时读表，更新这个对象。那么就可以用AtomicReference这个类
// 常用方法：get() : 返回當前的引用
// compareAndSet : 如果当前值与给定的expect相等，（注意是引用相等而不是equals()相等），更新为指定的update值
// getAndSet : 原子地设为给定值并返回旧值
// set : 注意此方法不是原子的。不明白为什么要提供这个方法，很容易误用
public class AtomicReferenceDemo {

    public void typicalDemo() {
        String initialVal = "initial value referenced";

        AtomicReference<String> atomicReference = new AtomicReference<String>(initialVal);

        String newVal = "new value referenced";
        boolean exchanged = atomicReference.compareAndSet(initialVal, newVal);
        System.out.println("exchanged : " + exchanged);                 // exchanged : true
        System.out.println("now value : " + atomicReference.get());     // now value : new value referenced

        exchanged = atomicReference.compareAndSet("new value referenced", "new value referenced2"); // final string 用的是同一塊地址，故而判等
        System.out.println("exchanged: " + exchanged);                  // exchanged: true
        System.out.println("now value : " + atomicReference.get());     // now value : new value referenced2

        StringBuilder builder = new StringBuilder();
        builder.append("new value ");
        builder.append("referenced2");
        exchanged = atomicReference.compareAndSet(builder.toString(), "new value referenced3"); // 說明是引用判等，而非值判等
        System.out.println("exchanged: " + exchanged);                  // exchanged: false
        System.out.println("now value : " + atomicReference.get());     // now value : new value referenced2

        //atomicReference.set();


        /////////
        // 对于int没有这个问题，因为int是放在常量池里面的 (>127不行)
        int val = 100;
        AtomicReference<Integer> atomicReferenceInt = new AtomicReference<>(val);

        boolean exchangedInt = atomicReferenceInt.compareAndSet(100, 12);
        System.out.println("int exchanged : " + exchangedInt);
        System.out.println("int now value : " + atomicReferenceInt.get());

    }

    public static void main(String[] args) {
        AtomicReferenceDemo demo = new AtomicReferenceDemo();
        demo.typicalDemo();
    }
}
