package com.tseong.learning.basic.thread.lock.reentrantLock;

import com.tseong.learning.basic.thread.jvmTools.javap.Synchronized;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockinteruptiblyDemo {

    public static void main(String[] args) {
        LockinteruptiblyDemo instance = new LockinteruptiblyDemo();

        // demo 1
        System.out.println("below is the lock competition of 2threads");
        instance.demoLockCompetition();

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // demo2
        System.out.println("below interrupts thread-2 while thread-1 occupying the lock, but thread-2 returns immediatially:");
        instance.demoInterrupt();
    }

    void demoLockCompetition() {
        Thread thread1 = new Thread(new RunDemo());
        Thread thread2 = new Thread(new RunDemo());

        thread1.start();
        thread2.start();
    }

    void demoInterrupt() {
        Thread thread1 = new Thread(new RunDemo());

        try {
            Thread.sleep(10);
        } catch (Exception e) {


        }
        Thread thread2 = new Thread(new RunDemo());

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }

}

class RunDemo implements Runnable {

    private static Lock lock = new ReentrantLock(); // 测试用：不同线程用同一个锁

    @Override
    public void run() {
        try {
            doJob();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " Interrupted from doJob"); // 获取锁时候的异常,注意该demo没有进入这里
        }
    }

    private void doJob() throws InterruptedException {

        // 如果用lock()，要等其它线程释放锁本线程得到锁之后，才会进入执行引发异常
        // (主要说明在没有拿到锁的情况下，interrupt该线程没什么用，要等拿到锁才行)
        // 执行结果如下 :
        // Thread-0 got the lock
        // Thread-0 finished
        // Thread-1 got the lock
        // Thread-1 interrupted
         lock.lock();


        // 如果用lockInterruptibly(), 不用等到锁，直接在里面抛出异常并处理异常
        // (主要说明在没有拿到锁的情况下，interrupt该线程立即抛出异常给上层)
        // 执行结果如下 :
        // Thread-0 got the lock
        // Thread-1 Interrupted from doJob
        // Thread-0 finished

       // lock.lockInterruptibly();
       // synchronized(RunDemo.class) { // 如果用synchronized替代lock，都相当于 lock.lock, 没获取锁之前不会中断

            System.out.println(Thread.currentThread().getName() + " got the lock");

            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " finished");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
            } finally {
                //     lock.unlock();
            }
       // }
    }


}

/*
－ synchronized 代码块不能够保证进入访问等待的线程的先后顺序。(沒有公平參數)
－ 你不能够传递任何参数给一个 synchronized 代码块的入口。因此，对于 synchronized 代码块的访问等待设置超时时间是不可能的事情。
－ lockInterruptibly, readwrite lock
 */