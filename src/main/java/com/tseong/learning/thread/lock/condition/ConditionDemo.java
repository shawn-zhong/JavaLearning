package com.tseong.learning.thread.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new MyConditionRunnable(1));
        Thread t2 = new Thread(new MyConditionRunnable(2));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static  class MyConditionRunnable implements Runnable {

        private int threadId;
        private static Condition cond;
        private static ReentrantLock lock;
        private static int counter;

        static {
            lock = new ReentrantLock();

            // 这里的条件变量cond必须通过ReentrantLock变量来创建，因为在之后的同步中，需要他们的紧密协作才能完成
            cond = lock.newCondition();
        }

        public MyConditionRunnable(int id) { this.threadId = id;}

        @Override
        public void run() {

            try {
                int NUMBER = 10000000;
                for (int i=0; i< NUMBER; i++) {

                    lock.lock();    // 1. 获取锁
                    if (threadId == 1) {            // 线程1进入
                        if (counter < NUMBER / 2) { // (在线程2没有将counter加到指定数量时，线程1一直等待中)
                            // 2. await会释放已经获得的锁并进入等待状态 (原子性完成)
                            cond.await();
                            // 3. 被signalAll()唤醒并尝试重新获得锁（原子性完成）
                            // 4. 获得锁后，继续执行后面的代码
                        }
                    } else {                        // 线程2进入
                        if (counter == NUMBER / 2) {
                            // 唤醒所有等待该条件变量cond的线程，即await阻塞函数。和signal()相比，signal由于只是随机
                            // 的唤醒其中一个等待线程，所以效率较高，如果在确认当前最多只有一个线程在等待时，可以考虑使用
                            // signal代替signalAll, 否则最好还是使用SignalAll更加安全，避免了程序整体挂起事件发生。
                            // 在使用signalAll的时候，由于有多个线程均被唤醒，因此被唤醒的每一个线程均需要重新验证一下代码
                            // 逻辑，确认一下是否想要做的事情已经被同事唤醒的某一个线程做完了
                            cond.signalAll();
                        }
                    }

                    ++counter;
                    lock.unlock();
                }

                System.out.println("counter = " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
