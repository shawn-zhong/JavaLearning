package com.tseong.learning.basic.thread.producer_consumer;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class _3_BlockingQueue {

    private static final int CAPACITY = 5;

    public static class Producer extends Thread {
        private LinkedBlockingQueue<Integer> blockingQueue;
        private String name;
        private int maxSize;
        private static int i = 0;

        public Producer(String name, LinkedBlockingQueue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    blockingQueue.put(i);
                    System.out.println("[" + name + "] Producing value : +" + i);
                    i++;

                    //暂停最多1秒
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class Consumer extends Thread {
        private LinkedBlockingQueue<Integer> blockingQueue;
        private String name;
        private int maxSize;

        public Consumer(String name, LinkedBlockingQueue<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int x = blockingQueue.take();
                    System.out.println("[" + name + "] Consuming : " + x);

                    Thread.sleep(new Random().nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(CAPACITY);

        Thread producer1 = new Producer("P-1", blockingQueue, CAPACITY);
        Thread producer2 = new Producer("P-2", blockingQueue, CAPACITY);
        Thread consumer1 = new Consumer("C1", blockingQueue, CAPACITY);
        Thread consumer2 = new Consumer("C2", blockingQueue, CAPACITY);
        Thread consumer3 = new Consumer("C3", blockingQueue, CAPACITY);

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
