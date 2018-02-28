package com.tseong.learning.thread.basic;

class MyThread extends Thread {

    // 这里的volatile关键字是推荐要有的，它表示该变量为容易改变地变量，是告诉JVM，
    // 对该变量值的读取每次都要从主存中读取,而不是从cpu的chache中读取上次从主存读取之后的快照,
    // 读取快照可以提升程序的执行效率
    // 另一个原因是固定执行执行的order，JVM／cpu为了优化速度，有些指令不一定按代码的顺序执行
    private volatile boolean stop = false;

    public void stopMe() { stop = true; }

    public void run() {

        int i=0;

        while (!stop) {             // 通过一个自定义的boolean变量来判断, 效率会提升很多
            if (i++ > 100000000)
                break;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("result : i = " + i);
    }
}

public class _4_InterruptDemo {

    public static void main(String[] args) {

        MyThread t1 = new MyThread();
        t1.start();

        try {
            Thread.sleep(100);
            System.out.println("Thread.sleep over.");

            // t1.stop(); // -> 不要使用，该方法已经废弃，并且有问题，破坏代码逻辑与原子性；使用这个方法不会打印 result : i =
            // stop() 方法已经是一个 废弃的 方法，它是一个 不安全的 方法。因为调用 stop() 方法会直接终止run方法的调用，并且会抛出一个ThreadDeath错误，如果线程持有某个对象锁的话，会完全释放锁，导致对象状态不一致。所以， stop() 方法基本是不会被用到的。

            t1.stopMe();
            System.out.println("r.stop over");
            t1.join();
            System.out.println("t1.join over.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


/*
* 实测50mseconds时b3例子与b1例子的执行效率差别:
    // b3_threadInterruptTest
    Thread.sleep over.
    r.stop over
    i = 91924360
    t1.join over.

    // b1_threadInterruptTest
    Thread.sleep over.
    t1.interrupt over.
    i = 45978297
    t1.join over.
* */