package com.tseong.learning.thread.basic;

public class _1_JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建了一个线程对象, 同時创建了一个匿名内部类Runnable的实现类
        // Runnable实现类中的run方法是线程执行时的方法体
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10000; ++i) {
                    System.out.println(i);
                }
            }
        });

        // 启动线程
        t1.start();

        // // join方法将阻塞主线程，等待工作者线程的结束
        // 由于主线程和工作者线程是并行的两个执行体，因此如果主线程不join等待工作者线程的结束，那么主线程
        // 将会直接退出,如果工作者线程未被设置为后台线程,即便他仍需要继续执行,但是由于主线程的退出,工作者线程也强行退出 (－>实验证明不正确)
        t1.join();


        // 使用lambda表达式创建并运行一个线程：
        Thread t2 = new Thread(()->{

            for (int i=0; i<10; i++) {
                System.out.println("Thread 2 alive");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        t2.start();
        t2.join();


        // 使用一个继承Thread的子类实现一个线程：
        demoThread t3 = new demoThread();
        t3.start();
        t3.join();
    }
}


class demoThread extends Thread {

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            System.out.println("DemoThread alive");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}