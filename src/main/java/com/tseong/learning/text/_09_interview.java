package com.tseong.learning.text;

import org.springframework.context.annotation.Conditional;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class _09_interview {

    public void a() {
    }
    /*

    1. 现在有T1，T2， T3三个线程，你怎么保证T2在T1执行完后执行，T3在T2执行完后执行？
    － 使用join
    － 使用callable和futureTask
    － Object.Notify/Wait, LockSupport.park/unpark, Lock.Condition.await/Signal, (Object.wait是native代码。join的原理实际上也是object.wait,每个线程完成时会自身object.notifyall, Condition.await底层是LockSupport.part)
    － BlockingQueue
    － 线程池（实际上也是BlockingQueue）

    （https://www.cnblogs.com/james0/p/9280144.html）
    （https://blog.csdn.net/hellorichen/article/details/71107594）
    （https://blog.csdn.net/u010185035/article/details/81172767）

    2. 在Java中Lock接口比synchronized块的优势是什么？如果你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保证它的完整性，你怎么实现？
    lock接口在多线程最大优势是分别为读写提供了锁（还有其他功能，如Timeout,如果用lockInterruptibly, lock/unlock分离, 公平性，condition）
    参考concurrentHashMap的实现

    3. Java中wait和sleep方法的不同？
    wait会释放锁，而sleep会一直持有锁。wait通常被用于线程间交互。sleep通常被用于暂停执行

    4.用Java写代码来解决生产者——消费者问题
    与上面的问题很类似，但这个问题更经典，有些时候面试都会问下面的问题。在Java中怎么解决生产者——消费者问题，当然有很多解决方法，我已经分享了一种用阻塞队列实现的方法。
    有些时候他们甚至会问怎么实现哲学家进餐问题

    5.你将如何使用thread dump？你将如何分析Thread dump？
    在UNIX中你可以使用kill -3，然后thread dump将会打印日志，在windows中你可以使用”CTRL+Break”。非常简单和专业的线程面试问题，
    但是如果他问你怎样分析它，就会很棘手


    linkedBlockingQueue 分离了putLock和takeLock, ArrayBlockingQueue可以指定消费线程的公平/非公平消费
    LinkedBlockingQueue的优点是锁分离，那就很适合生产和消费频率差不多的场景，这样生产和消费互不干涉的执行，
    能达到不错的效率，尽量不使用remove操作，获取两把锁的效率更低，可以使用size方法（就是计数器直接返回）

     */
}
