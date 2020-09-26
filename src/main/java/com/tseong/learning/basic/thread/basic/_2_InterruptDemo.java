package com.tseong.learning.basic.thread.basic;

public class _2_InterruptDemo {

    public static void main(String[] args) {

        // 使用匿名类实现Runable接口
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                // 每个线程都自行维护一个标志位，即为interrupted, 当主线程通过线程对象调用interrupt方法时,
                // 就已经将该线程的该标志位设为true,这里工作者线程将会在每次执行完一次循环体内的工作后都会检查线程
                // 是否被中断(判断t1.interrupt()方法置位的boolean变量)。如果中断标志位true，则推出当前循环
                // Thread.currentThread()通过该静态方法获取执行体所在线程的对象

                int i=0;
                while (!Thread.currentThread().isInterrupted()) {   // 这种控制方法的缺陷：这里判断要耗时. (如果不加这个判断，线程不会退出)；
                                                                    // 在sleep期间主线程调用interrupt()时，sleep函数将退出同时抛出interruptedException， 此时isInterrupted为false
                    if (i++>100000000) {
                        break;
                    }
                }

                System.out.println("thread runnable over. i = " + i);
            }
        };

        // 创建线程并测试上面的isInterrupted语句
        Thread t1 = new Thread(myRunnable);
        t1.start();

        try{

            Thread.sleep(50);   // 这里让主线程强制休眠1000毫秒，好让工作者线程执行一会儿
            System.out.println("Thread.sleep over.");

            t1.interrupt();             // 主线程主动中断工作者线程
            System.out.println("t1.interrupt over.");

            // 主线程主动等待工作者线程的执行结束
            t1.join();
            System.out.println("t1.join over.");

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
