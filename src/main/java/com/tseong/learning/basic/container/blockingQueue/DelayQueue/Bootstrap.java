package com.tseong.learning.container.blockingQueue.DelayQueue;

import java.util.concurrent.DelayQueue;

public class Bootstrap {

    public static void main(String[] args) throws InterruptedException {

        // DelayQueue内部使用了PriorityQueue和锁来实现
        DelayQueue<DelayedElement> delayQueue = new DelayQueue<>();

        DelayedElement element1 = new DelayedElement(5000, "5secsElement");
        DelayedElement element2 = new DelayedElement(2000, "2secsElement");
        DelayedElement element3 = new DelayedElement(6000, "6secsElement");
        DelayedElement element4 = new DelayedElement(3000, "3secsElement");

        delayQueue.offer(element1);
        delayQueue.offer(element2);
        delayQueue.offer(element3);
        delayQueue.offer(element4);

        System.out.println("size : " + delayQueue.size());

        int size = delayQueue.size();
        for (int i=0; i< size; i++) {

            long timestamp = System.currentTimeMillis();

            DelayedElement val = delayQueue.take(); // 只有到期的元素才会被取出（getDelay<＝0）内部使用了PriorityQueue并根据compare计算优先级

            long elapse = System.currentTimeMillis() - timestamp;

            System.out.println("Cost " + elapse + " mills to Took elememnt : " + val.toString());
        }

    }
}
