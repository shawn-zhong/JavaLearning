package com.tseong.learning.basic.thread.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class _1_ObjectNotify {

    private static final int CAPACITY = 5;

    // 生产者
    public static class Producer extends Thread {
        private Queue<Integer> queue;
        String name;
        int  maxSize;
        int i=0;

        public Producer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == maxSize){    // while:防止该线程没有收到notify()调用也从wait()中返回（也称作虚假唤醒），
                                                        // 关于虚假唤醒的案例，可以看这：https://blog.csdn.net/zhangheliang2010/article/details/44890103
                                                        // 主要是notifyAll会造成所有线程都拿得到锁
                        try {
                            System.out.println("Producer " + name + "  found queue full, waiting for consumer to take");
                            queue.wait();   // 可以像lockCondition一样分离读写锁

                            System.out.println("producer awake " + name);

                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    System.out.println("Producer " + name + " now producing value : " + i);
                    queue.offer(i++);
                    queue.notifyAll();
                    //queue.notify();


                }

                // 这段不要放在synchoronzied段比较好 （不会释放锁）
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 消费者
    public static class Consumer extends Thread {
        private Queue<Integer> queue;
        String name;
        int maxSize;

        public Consumer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("Consumer" + name + " found queue is empty, waiting for producer to produce");
                            queue.wait();

                            System.out.println("consumer awake " + name);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    int x = queue.poll();
                    System.out.println("Consumer" + name + " consuming value: " + x);
                    queue.notifyAll();


                }

                // 这段不要放在synchoronzied段比较好
                try {
                    // Thread.sleep(new Random().nextInt(1000));
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {

        Queue<Integer> queue = new LinkedList<>();

        Thread producer1 = new Producer("p-1", queue, CAPACITY);
        Thread producer2 = new Producer("p-2", queue, CAPACITY);

        Thread consumer1 = new Consumer("c-1", queue, CAPACITY);
        Thread consumer2 = new Consumer("c-2", queue, CAPACITY);
        Thread consumer3 = new Consumer("c-2", queue, CAPACITY);



        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();
        consumer3.start();
    }
}
