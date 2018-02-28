package com.tseong.learning.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrierDemo instance = new CyclicBarrierDemo();
        instance.test();
    }

    private void test() {
        // 最后一个障珊达到时，被这个最后达到的线程执行一次AfterRunnable
        SharedObjectForThreads sharedVal = new SharedObjectForThreads();
        CyclicBarrier barrier = new CyclicBarrier(3, new AfterRunnable(sharedVal));

        Thread thread1 = new Thread(new MyRunnable(barrier, sharedVal, 0));
        Thread thread2 = new Thread(new MyRunnable(barrier, sharedVal, 1));
        Thread thread3 = new Thread(new MyRunnable(barrier, sharedVal, 2));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyRunnable implements Runnable {

    CyclicBarrier barrier;
    SharedObjectForThreads sharedVal;
    int threadNo;

    public MyRunnable(CyclicBarrier barrier, SharedObjectForThreads value, int threadNo) {
        this.barrier = barrier;
        this.sharedVal = value;
        this.threadNo = threadNo;
    }

    @Override
    public void run() {

        //System.out.println("Is Running : " + name);

        try {
            //Thread.sleep(500);
            // do something here
            // 通常这里执行一些批量计算的前期准备工作，多个线程并发执行，其中每一个线程都只是执行自己的
            // 区域，比如10个线程执行100000000个元素的预计算，那么每个线程将得到其中的10分之1来处理，
            // 这里必须要等到所有预计算处理都完毕后才能执行后面的批量操作

            for (int i=0; i<100; i++) {
                switch (threadNo) {
                    case 0:
                        sharedVal.increVal0();
                        break;   // action of thread0
                    case 1:
                        sharedVal.increVal1();
                        break;   // action of thread1
                    case 2:
                        sharedVal.increVal2();
                        break;   // action of thread2
                    default:
                        assert (false);
                }
            }

            barrier.await();
            // 穿过CyclicBarrier的await函数，表示此时所有线程各自负责的预计算均已经处理完毕，可以从此点
            // 开始继续后续的计算任务了 (注意执行时机，下面代码的执行实际上在AfterRunnable之后)
            System.out.println("await done, now to add val3");
            sharedVal.increVal3();
            System.out.println("val3 = " + sharedVal.getValue3());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class AfterRunnable implements Runnable {
    private SharedObjectForThreads sharedVal;

    public AfterRunnable(SharedObjectForThreads sharedVal) {
        this.sharedVal = sharedVal;
    }

    // 该函数只是在最后一个障珊达到时，被这个最后达到的线程执行一次（仅一次）
    @Override
    public void run() {
        System.out.println("After-Barrier reached:");
        System.out.println("done : val0 = " + sharedVal.getValue0());
        System.out.println("done : val1 = " + sharedVal.getValue1());
        System.out.println("done : val2 = " + sharedVal.getValue2());
        System.out.println("done : val3 = " + sharedVal.getValue3());

    }
}

class SharedObjectForThreads {
    private int value0 = 0;
    private int value1 = 0;
    private int value2 = 0;
    private int value3 = 0;

    public synchronized void increVal0() {
        value0++;
    }

    public synchronized void increVal1() {
        value1++;
    }

    public synchronized void increVal2() {
        value2++;
    }

    public synchronized void increVal3() {
        value3++;
    }

    public int getValue0() {
        return value0;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }

    public int getValue3() {
        return value3;
    }
}



