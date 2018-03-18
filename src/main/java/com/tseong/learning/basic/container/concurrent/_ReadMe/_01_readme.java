package com.tseong.learning.basic.container.concurrent._ReadMe;

public class _01_readme {

    public static void main(String[] args) {

    }
}


/*

A) 同步容器类（比较旧，已经废弃）
Vector, HashTable


B) BlockingQueue/TransferQueue
    - ArrayBlockingQueue        // 由数组组成；有界；可以指定线程获取时是否公平/非公平
    - LinkedBlockingQueue       // 由链表组成；有界但默认值很大（Integer.MAX_VALUE）；不可以指定公平/非公平 (固定非公平)
    - PriorityBlockingQueue     // 一个支持优先级排序的无界阻塞队列
    - synchronousQueue          // 一个不存储元素的阻塞队列，每一个put操作必须等待一个take操作，否则不能继续添加元素
    - DelayQueue                // 一个支持延时获取元素的无界阻塞队列
    - LinkedBlockingDeque       // 由链表组成的双向阻塞队列 多了方法：addFirst addLast offerFirst offerLast peekFirst peekLast takeFirst..
    - LinkedTransferQueue       // 一个由链表结构组成的无界阻塞TransferQueue队列。相比其它，多了tryTransfer和transfer方法;

    // transfer方法：如果当前有消费者正在等待接受元素, transfer方法可以把生产者掺入的元素立刻transfer给消费者，如果没有消费者在等待接受元素，transfer方法会将元素放在队列的tail节点，
    // 并等到该元素被消费者消费了才返回。因为自旋会消耗cpu，所以自旋一定的次数后使用Thread.yield()方法来暂停当前正在执行的线程，并执行其它线程

C) Concurrent:
    - ConcurrentLinkedQueue: 是使用非阻塞的方式实现的基于链接节点的无界的线程安全队列，性能非常好
    - ConcurrentLinkedDeque
    - ConcurrentSet
    - ConcurrentHashMap
    - ConcurrentSkipListMap － Sorted容器
    - ConcurrentSkipListSet － Sorted容器

D) 非线程安全
    - ArrayQueue
    - ArrayDeque


E) 待查 （并发容器?）
    － CopyOnWriteArrayList
    － CopyOnWriteArraySet    - HashMap
 */