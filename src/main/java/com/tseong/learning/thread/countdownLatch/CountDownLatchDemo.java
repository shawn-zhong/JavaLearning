package com.tseong.learning.thread.countdownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatchDemo instance = new CountDownLatchDemo();
        instance.test();
    }

    private void test() {
        CountDownLatch latch = new CountDownLatch(10);

        for (int i=0; i<10; i++) {
            Thread t = new Thread(new MyRunnable(latch));
            t.setName("Thread-" + i);
            t.start();
        }

        System.out.println("main thread : waiting for threads to complete");
        try {
            // 在栓锁latch被减到0之前，函数将挂起主函数
            // 直到latch的值为0, 函数将正常返回
            latch.await();  // 注意不要写成Object类的wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main thread : all threads exited");
    }

    static class MyRunnable implements Runnable {

        private CountDownLatch latch;

        public MyRunnable(CountDownLatch c) { latch = c; }

        @Override
        public void run() {

            try{
                System.out.println(Thread.currentThread().getName() + " is going to sleep 1000 mills");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " is done and about to leave");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 这里每一个线程在退出时都会讲栓锁的值减1
                // 直到最后一个退出时将栓锁的值减到0
                latch.countDown();
            }
        }
    }
}
