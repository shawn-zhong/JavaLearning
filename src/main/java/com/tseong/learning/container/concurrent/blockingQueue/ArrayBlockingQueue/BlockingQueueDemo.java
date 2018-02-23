package com.tseong.learning.container.concurrent.blockingQueue.ArrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueueDemo instance = new BlockingQueueDemo();
        instance.arrayBlockingQueueDemo();
        instance.linkedBlockingQueueDemo();

        instance.priorityBlockingQueueDemo();
        instance.synchronousQueueDemo();
        instance.blockingDequeuDemo();
    }

    void arrayBlockingQueueDemo() {
        System.out.println("test arrayBlockingQueueDeno");

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    void linkedBlockingQueueDemo() {
        /* linkedBlockingQueue 分离了putLock和takeLock,
        LinkedBlockingQueue的优点是锁分离，那就很适合生产和消费频率差不多的场景，这样生产和消费互不干涉的执行，
        能达到不错的效率，尽量不使用remove操作，获取两把锁的效率更低，可以使用size方法（就是计数器直接返回），这个还是比较重要的，
        有些集合不适合使用size->例如ConcurrentLinkedQueue，正确应该使用isEmpty(); 对比ConcurrentLinkedQueue, LinkedBlockingQueue多了take和put方法
        */

        System.out.println("test linkedBlcokingQueueDemo");

        BlockingQueue<String> queue = new LinkedBlockingDeque<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    void priorityBlockingQueueDemo() throws InterruptedException {
        System.out.println("test priorityBlockingQueueDemo");

        // 注意，如果你从一个 PriorityBlockingQueue 获得一个 Iterator 的话，该 Iterator 并不能保证它对元素的遍历是以优先级为序的
        BlockingQueue<PriorityElememt> queue = new PriorityBlockingQueue<>();
        for (int i=0; i<10; i++) {
            queue.put(new PriorityElememt(i));
        }

        // consume
        new Thread(()->{

            int size = queue.size();

            for (int i=0; i<size; i++) {
                try {
                    PriorityElememt element = queue.take();
                    System.out.println("Priority element got : " + element.priority);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void synchronousQueueDemo() {
        // SynchronousQueue 是一个特殊的队列，它的内部同时只能够容纳单个元素。
        // 如果该队列已有一元素的话，试图向队列中插入一个新元素的线程将会阻塞，直到另一个线程将该元素从队列中抽走。
        // 同样，如果该队列为空，试图向队列中抽取一个元素的线程将会阻塞，直到另一个线程向队列中插入了一条新的元素
    }


    void blockingDequeuDemo() throws InterruptedException {
        System.out.println("test blockingDequeueDemo");

        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("1");
        deque.addLast("2");
        deque.addLast("3");

        System.out.println(deque.takeFirst());
        System.out.println(deque.takeLast());
        System.out.println(deque.takeLast());

    }

    class PriorityElememt implements Comparable {

        private int priority;

        public PriorityElememt(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(Object o) {
            PriorityElememt obj = (PriorityElememt) o;
            if (this.priority == obj.priority)
                return 0;

            return this.priority>obj.priority?1:-1;
        }
    }
}


/* blocking queue 的操作

 	    抛异常	    特定值	    阻塞	        超时
插入	    add(o)	    offer(o)	put(o)	    offer(o, timeout, timeunit)
移除	    remove(o)	poll(o)	    take(o)	    poll(timeout, timeunit)
检查	    element(o)	peek(o)

 */