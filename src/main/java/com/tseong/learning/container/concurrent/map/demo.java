package com.tseong.learning.container.concurrent.map;


import com.tseong.learning.container.concurrent.blockingQueue.DelayQueue.DelayedElement;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class demo {
    public static void main(String[] args) {
        int a,b;
        b=10;
        int c = (a=b)*a;
        System.out.println(a+" " + b + " " +c);

        // blocking queue
        java.util.concurrent.ArrayBlockingQueue<Integer> ca = new java.util.concurrent.ArrayBlockingQueue<>(10, true);  // 数组组成；有界；可以指定公平/非公
        java.util.concurrent.LinkedBlockingQueue<Integer> ch = new LinkedBlockingQueue<>();         // 链表组成；有界但默认值很大（Integer.MAX_VALUE）；不可以指定公平/非公平 (固定非公平)
        java.util.concurrent.LinkedBlockingDeque<Integer> cg = new LinkedBlockingDeque<>();         // 由链表组成的双向阻塞队列 多了方法：addFirst addLast offerFirst offerLast peekFirst peekLast takeFirst..
        java.util.concurrent.PriorityBlockingQueue<Integer> cj = new PriorityBlockingQueue<>();     // 一个支持优先级排序的无界阻塞队列
        java.util.concurrent.DelayQueue<DelayedElement>     cc = new DelayQueue<>();                // 一个支持延时获取元素的无界无界阻塞队列
        java.util.concurrent.SynchronousQueue<Integer> ck;      // 一个不存储元素的阻塞队列，每一个put操作必须等待一个take操作，否则不能继续添加元素
        java.util.concurrent.LinkedTransferQueue<Integer> ci;   // 一个由链表结构组成的无界阻塞TransferQueue队列。相比其它，多了tryTransfer和transfer方法;

        // transfer方法：如果当前有消费者正在等待接受元素, transfer方法可以把生产者掺入的元素立刻transfer给消费者，如果没有消费者在等待接受元素，transfer方法会将元素放在队列的tail节点，
        // 并等到该元素被消费者消费了才返回。因为自旋会消耗cpu，所以自旋一定的次数后使用Thread.yield()方法来暂停当前正在执行的线程，并执行其它线程

        // 同步容器
        java.util.concurrent.ConcurrentLinkedQueue<Integer> cb = new java.util.concurrent.ConcurrentLinkedQueue<>();
        java.util.concurrent.ConcurrentHashMap<Integer, Integer> ccdd = new java.util.concurrent.ConcurrentHashMap<>();
        java.util.concurrent.ConcurrentLinkedDeque<Integer> cd = new java.util.concurrent.ConcurrentLinkedDeque<>();
        // interface : java.util.concurrent.ConcurrentMap<Integer, Integer>
        // interface : java.util.concurrent.ConcurrentNavigableMap
        java.util.concurrent.ConcurrentSkipListMap<Integer, Integer> ce;
        java.util.concurrent.ConcurrentSkipListSet<Integer> cf;


        java.util.concurrent.CopyOnWriteArrayList<Integer> cl;



    }
}
