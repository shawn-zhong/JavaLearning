package com.tseong.learning.basic.thread.Atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author: Shawn ZHONG @date: 2020-09-23 14:00
 */
public class _06_AtomicStampedReferenceDemo {

    public static void main() {
        AtomicStampedReference<Foo> atomicStampedReference = new AtomicStampedReference<>(null, 1); //持有FOO对象的引用，初始值为null，版本号为1
        int[] stampHolder = new int[1];

        Foo oldRef = atomicStampedReference.get(stampHolder);
        int oldStamp = stampHolder[0];

        // 中间可能有其他线程造成ABA问题，但是stamp也会改变

        Foo newRef= null;
        atomicStampedReference.compareAndSet(oldRef, newRef, oldStamp, oldStamp+1); // 携带了stamp，所以stamp变化后会失败
    }

    class Foo {

    }
}
