package com.tseong.learning.thread.happensBefore;

/**
 * 一个简单的展示Happen-before的例子
 *
 */
public class SimpleHappenBefore {

    private static int a = 0;
    private static boolean flag = false;

    public static void main(String ... args) throws InterruptedException {

        SimpleHappenBefore instance = new SimpleHappenBefore();
        instance.funcTest();

    }


    public void funcTest() throws InterruptedException {
        // 由于多线程情况下未必会试出重排序的结论，所以多试一些次
        for (int i=0; i<100000; i++) {
            ThreadA threadA = new ThreadA();
            ThreadB threadB = new ThreadB();

            threadA.start();
            threadB.start();

            // 这里等待线程结束后，重置共享变量，以便验证结果的工作
            threadA.join();
            threadB.join();

            //Thread.sleep(10);

            a = 0;
            flag = false;
        }
    }


    class ThreadA extends Thread {

        @Override
        public void run() {
            a = 3;
            flag = true;
        }
    }

    class ThreadB extends Thread {

        @Override
        public void run() {

            while (true) {
                if (flag) {
                    if (a == 0) {
                        System.out.println("jvm reordered");    // 事实证明，一次也没出新，但理论可能出现
                    }
                    break;
                }
            }
        }
    }

}
