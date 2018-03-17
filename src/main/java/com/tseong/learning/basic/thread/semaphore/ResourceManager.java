package com.tseong.learning.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManager {

    private final Semaphore semaphore;
    private boolean resourceArray[];
    private final ReentrantLock lock;

    public ResourceManager() {
        this.resourceArray = new boolean[10];   // 存放厕所状态
        this.semaphore = new Semaphore(10, true);   // 控制10个共享资源。公平信号量，FIFO; 强制公平会影响到并发性能，所以除非你确实需要它否则不要启用它。
        this.lock = new ReentrantLock(true);    // 公平模式的锁，先进先出

        for (int i=0; i<10; i++) {
            resourceArray[i] = true;
        }
    }

    public void userResource(int userId) {

        try {
            semaphore.acquire();
            int id = getResourceId();
            System.out.println("userID; " + userId + " using toilet id " + id);
            Thread.sleep(1000);     // simulating using
            resourceArray[id] = true;      // 感觉这里要加锁

        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int getResourceId() {
        int id = -1;
        lock.lock();
        try {
            for (int i=0; i<10; i++) {
                if (resourceArray[i]) {
                    resourceArray[i] = false;
                    id = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return id;
    }

}
