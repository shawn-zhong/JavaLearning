package com.tseong.learning.basic.thread.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _2_LockCondition {

    private static final int CAPACITY = 5;
    private static final Lock lock = new ReentrantLock();
    private static final Condition fullCondition = lock.newCondition();
    private static final Condition emptyConditon = lock.newCondition();

    public static class Producer extends Thread {
        private Queue<Integer> queue;
        private String name;
        private int maxSize;
        private static int i=0;

        public Producer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {

                lock.lock();
                while (queue.size() == maxSize) {

                    try {
                        System.out.println("Producer " + name + "  found queue full, waiting for consumer to take");
                        fullCondition.await();
                        System.out.println("producer awake " + name);
                    }catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                System.out.println("Producer " + name + " now producing value : " + i);
                queue.offer(i++);

                //fullCondition.signalAll();
                emptyConditon.signalAll();

                lock.unlock();


                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        private Queue<Integer> queue;
        private String name;
        private int maxSize;

        public Consumer(String name, Queue<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }


        @Override
        public void run() {
            while (true) {

                lock.lock();

                while(queue.isEmpty()) {

                    try {
                        System.out.println("Consumer" + name + " found queue is empty, waiting for producer to produce");
                        emptyConditon.await();

                        System.out.println("consumer awake " + name);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                int x = queue.poll();
                System.out.println("Consumer" + name + " consuming value: " + x);

                fullCondition.signalAll();
                //emptyConditon.signalAll();

                lock.unlock();

                try {
                    Thread.sleep(3000);
                   // Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {

        Queue<Integer> queue = new LinkedList<>();  // 读和读之间，写和写之间也会竞争锁，所以非线程安全的链表没问题

        Thread producer1 = new Producer("p-11", queue, CAPACITY);
        Thread producer2 = new Producer("p-22", queue, CAPACITY);

        Thread consumer1 = new Consumer("c-12", queue, CAPACITY);
        Thread consumer2 = new Consumer("c-23", queue, CAPACITY);



        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();
    }
}
