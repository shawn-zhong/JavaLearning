package com.tseong.learning.basic.thread.basic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class _5_ThreadConcurrent {

    public static void main(String[] args) throws InterruptedException {

        Runnable taskWrong = new MyRunnableWrong();
        demoConcurrent(taskWrong);

        Runnable taskByLock = new MyRunnableByRLock();
        demoConcurrent(taskByLock);


        Runnable taskBySynchronized = new MyRunnableBySychronized();
        demoConcurrent(taskBySynchronized);

        Runnable taskByAtomicRefence = new MyRunnableByAtomicReference();
        demoConcurrent(taskByAtomicRefence);

        Runnable taskByCompareAndSwap = new MyRunnableByCompareAndSwap(new SharedValueContainer());
        demoConcurrent(taskByCompareAndSwap);

    }

    public static void demoConcurrent(Runnable task) throws InterruptedException {

        // 我们创建三个线程去竞争同一个任务，期待的最后结构是3*1000000
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.setName("sub-thread 1");
        t2.setName("sub-thread 2");
        t3.setName("sub-thread 3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    static class MyRunnableWrong implements Runnable {

        private static int counter = 0; // 即使加了volatile没有用锁，还是不正确，因为volatile只保证可见性

        @Override
        public void run() {
            for (int i=0; i<1000000; ++i ) {
                //++counter;        // 非原子性操作
                counter += 1;       // 非原子性操作，线程间会冲突，不能期待最后结果是2000000, 如果给这一步加锁则正确, 见下个例子
            }

            System.out.println("MyRunnableWrong : " + Thread.currentThread().getName() + " : i = " + counter);
        }
    }

    static class MyRunnableByRLock implements Runnable {

        private static int counter = 0;
        private static ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            for (int i=0; i<1000000; ++i) {
                lock.lock();
                    ++counter;          // 结果是n*1000000 correct
                lock.unlock();
            }

            System.out.println("MyRunnableByRLock : " + Thread.currentThread().getName() + " : counter = " + counter);
        }
    }

    static class MyRunnableBySychronized implements Runnable {

        private static int counter = 0;
        private static final Object lock = new Object();

        @Override
        public void run() {
            for (int i=0; i<1000000; i++) {
                synchronized (lock) {
                    ++counter;      // 结果是n*1000000 correct
                }
            }

            System.out.println("MyRunnableBySynchronized : " + Thread.currentThread().getName() + " : counter = " + counter);
        }
    }

    static class MyRunnableByAtomicReference implements Runnable {

        private static int counter = 0;
        private static AtomicReference<Integer> counterRef = new AtomicReference<Integer>(counter);

        @Override
        public void run() {
            for (int i=0; i<1000000; i++) {
                counterRef.accumulateAndGet(1, (a,b)-> a+b);    // correct
            }

            System.out.println("MyRunnableByCas : " + Thread.currentThread().getName() + " : counter = " + counterRef.get());
        }
    }


    static class MyRunnableByCompareAndSwap implements Runnable {

        SharedValueContainer container;
        private static Unsafe unsafe;
        private static long counteroffset;

        MyRunnableByCompareAndSwap(SharedValueContainer container) {    // 传入container, 多个线程共享container变量, 然后使用unsafe实现CAS一致性
            this.container = container;
        }

        static {
            try{
                // 通过反射实例化Unsafe
                Field f = null;
                f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe)f.get(null);

                Field field = SharedValueContainer.class.getDeclaredField("counter");
                counteroffset = unsafe.objectFieldOffset(field);
                System.out.println("counterOffset = " + counteroffset);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {

            for (int i=0; i<1000000; i++) {

                boolean done = false;
                while (!done) {             // 相当于乐观锁
                    // 取出值（保证可见性）
                    int val = unsafe.getIntVolatile(container, counteroffset);

                    // 修改内存偏移地址为xx的值；返回true，说明修改成功 （原子性）
                    done = unsafe.compareAndSwapInt(container, counteroffset, val, val + 1);
                }
            }

            System.out.println("MyRunnableByCompareAndSwapWrongway : " + Thread.currentThread().getName() + " : counter = " + container.counter);

        }
    }

    static class MyRunnableByCompareAndSwapWrongway implements Runnable {

        private static int counter = 0 ;
        private static Unsafe unsafe;
        private static long counteroffset;

        static {
            try{
                // 通过反射实例化Unsafe
                Field f = null;
                f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe)f.get(null);

                Field field = MyRunnableByCompareAndSwapWrongway.class.getDeclaredField("counter");
                counteroffset = unsafe.objectFieldOffset(field); // 不能这样用，因为unsafe取不到static counter的值；需要用的话则在多个线程共享一个'对象'而非static

                System.out.println("counterOffset = " + counteroffset);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {

            for (int i=0; i<1000000; i++) {

                boolean done = false;
                while (!done) {             // 相当于乐观锁
                    // 取出值（保证可见性）
                    int val = unsafe.getIntVolatile(this, counteroffset);

                    // 修改内存偏移地址为xx的值；返回true，说明修改成功 （原子性）
                    done = unsafe.compareAndSwapInt(this, counteroffset, val, val + 1);
                }
            }

            System.out.println("MyRunnableByCompareAndSwapWrongway : " + Thread.currentThread().getName() + " : counter = " + counter);

        }
    }

    static class SharedValueContainer {
        private int counter = 0;
    }

}


// // http://www.cnblogs.com/stephen-liu74/archive/2011/08/22/2138032.html