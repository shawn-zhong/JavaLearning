package com.tseong.learning.unsafe;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo2 {

    static Thread _thread;

    public static void main(String[] args) throws InterruptedException {
        initThreadA();

        _thread.start();

        Thread.sleep(2000);

        _thread.interrupt();
    }

    public static void initThreadA()  {

        _thread = new Thread(()->{
            int count = 0;

            long start = System.currentTimeMillis();
            long end = 0;

            System.out.println("before 10 second count = " + count);

            while ((end - start) <= 10000) {
                count++;
                end = System.currentTimeMillis();
            }

            System.out.println("after 10 second count = " + count);

            LockSupport.park(); // 和object.wait相比：1.不需要锁，2.碰到interrupt时不会抛异常退出 3.wait搭配锁使用并会释放锁
            System.out.println("over here");

        });


    }
}
