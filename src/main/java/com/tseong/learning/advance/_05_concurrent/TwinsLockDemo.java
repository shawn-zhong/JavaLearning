package com.tseong.learning.advance._05_concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

public class TwinsLockDemo {

    @Test
    public void test() throws InterruptedException {

        final Lock lock = new TwinsLock();

        Runnable runnable = () ->{
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        };

        // 启动10个线程
        for (int i=0; i<10; i++) {
            Thread t = new Thread(runnable, "Thread-" + i);
          //  t.setDaemon(true);
            t.start();
        }

        // 每隔1秒换行
        for (int i=0; i<10; i++) {
            Thread.sleep(1000);
            System.out.println();
        }

    }
}
