package com.tseong.learning.basic.thread.daemon;

public class DaemonDemo {

    public static void main(String ... args) {

        new MyCommon().start();
        new MyDaemon().start();

        System.out.println("Main thread exiting..");
    }
}

class MyCommon extends Thread {

    @Override
    public void run() {

        for (int i=0;   i<5; i++) {
            System.out.println("MyCommon thread printing " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("MyCommon thread exiting..");
    }
}

class MyDaemon extends Thread {

    public MyDaemon() {
        setDaemon(true);
    }

    @Override
    public void run() {

        for (int i=0; i<100; i++ ){
            System.out.println("MyDaemon thread printing " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("MyDaemon thread exiting..");
    }


}

/* 输出：

MyCommon thread printing 0
MyDaemon thread printing 0
Main thread exiting..
MyCommon thread printing 1
MyDaemon thread printing 1
MyCommon thread printing 2
MyDaemon thread printing 2
MyCommon thread printing 3
MyDaemon thread printing 3
MyCommon thread printing 4
MyDaemon thread printing 4
MyCommon thread exiting..

Process finished with exit code 0


从上面的执行结果可以看出：
前台线程是保证执行完毕的，后台线程还没有执行完毕就退出了。

用个比较通俗的比如，任何一个守护线程都是整个JVM中所有非守护线程的保姆：
只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
Daemon的作用是为其他线程的运行提供便利服务，守护线程最典型的应用就是 GC (垃圾回收器)，它就是一个很称职的守护者。


 */