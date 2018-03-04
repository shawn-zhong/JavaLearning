package com.tseong.learning.unsafe;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {

        ThreadPark threadPark = new ThreadPark();
        threadPark.start();
        ThreadUnPark threadUnPark = new ThreadUnPark(threadPark);
        threadUnPark.start();

        threadUnPark.join();
        System.out.println("over");
    }

    static class ThreadPark extends Thread {

        public void run() {
            System.out.println(Thread.currentThread() + " will be blocked for 20s ..");
            LockSupport.parkNanos(1000000000l*60);
            System.out.println(Thread.currentThread() + " now resumed");
        }
    }

    static class ThreadUnPark extends Thread {

        public Thread thread = null;

        public ThreadUnPark(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            System.out.println("resume the thread in advance");
            LockSupport.unpark(thread); // unpark带了线程句柄
        }
    }
}
