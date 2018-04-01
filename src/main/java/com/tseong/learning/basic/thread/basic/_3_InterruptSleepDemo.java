package com.tseong.learning.basic.thread.basic;

public class _3_InterruptSleepDemo {

    public static void main(String[] args) {

        // 实现一个自定义runnable
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                int i =0;

                try {
                    while(!Thread.currentThread().isInterrupted()) {

                        if (++i > 10000000)
                            break;

                        // 在sleep期间主线程调用interrupt()时，sleep函数将退出同时抛出interruptedException
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException is raised here");
                    System.out.println("i = " + i);
                } finally {
                    System.out.println("i = " + i);
                }


                while (true) {
                    System.out.println("i = " + i);
                    if (++i > 1000)
                        break;

                    try {
                        Thread.sleep(10);   // 第二次sleep不会触发异常
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException 2 is raised here");
                    }
                }

                System.out.println("i = " + i);
            }
        };

        // 实现线程
        Thread t1 = new Thread(myRunnable);
        t1.start();

        try {
            Thread.sleep(300);
            System.out.println("Thread.sleep over.");
            t1.interrupt();
            System.out.println("t1.inerrupt over");
            t1.join();
            System.out.println("t1.join over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
