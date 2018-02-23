package com.tseong.learning.thread.lock.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class lockinteruptiblyDemo {

    public static void main(String[] args) {
        lockinteruptiblyDemo instance = new lockinteruptiblyDemo();
        //instance.demoLockCompetition();
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

    private void doJob() throws InterruptedException {

        // 如果用lock，要等其它线程释放锁本线程得到锁之后，才会进入执行引发异常
        // 执行结果如下 :
        // Thread-0 got the lock
        // Thread-0 finished
        // Thread-1 got the lock
        // Thread-1 interrupted
        // lock.lock();


        // 如果用lockInterruptibly, 不用等到锁，直接在里面抛出异常并处理异常
        // 执行结果如下 :
        // Thread-0 got the lock
        // Thread-1 Interrupted from doJob
        // Thread-0 finished
        lock.lockInterruptibly();



        System.out.println(Thread.currentThread().getName() + " got the lock" );

        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " finished" );
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            doJob();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " Interrupted from doJob"); // 获取锁时候的异常,注意该demo没有进入这里
        }
    }
}

/*
－ synchronized 代码块不能够保证进入访问等待的线程的先后顺序。(沒有公平參數)
－ 你不能够传递任何参数给一个 synchronized 代码块的入口。因此，对于 synchronized 代码块的访问等待设置超时时间是不可能的事情。
－ lockInterruptibly, readwrite lock
 */