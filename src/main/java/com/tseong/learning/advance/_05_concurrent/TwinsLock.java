package com.tseong.learning.advance._05_concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/*
 来自<<Java并发编程的艺术>>
 实现TwinsLock在同一时刻允许之多两个线程的访问，如果其它线程再对同步状态进行获取，该线程只能被阻塞
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);

    // 使用私有内部静态类实现 AQS
    private static final class Sync extends AbstractQueuedSynchronizer{

        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large then zero.");
            }
            setState(count);
        }

        @Override
        // 在acquireShared(int arg)方法中，同步器调用tryAcquireShared(int arg)方法尝试获取同步状态，当tryAcquireShared方法返回值为int类型大于等于0时，表示能够获取到同步状态。
        protected int tryAcquireShared(int reduceCount) {
            for (;;) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {    // 已经小于0的时候不用交换了，肯定不能获取锁，直接返回
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }


    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
