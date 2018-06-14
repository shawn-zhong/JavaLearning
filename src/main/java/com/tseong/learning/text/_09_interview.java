package com.tseong.learning.text;

public class _09_interview {
    /*

    1. 现在有T1，T2， T3三个线程，你怎么保证T2在T1执行完后执行，T3在T2执行完后执行？
    － 使用join
    － 使用callable和futureTask

    2. 在Java中Lock接口比synchronized块的优势是什么？如果你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保证它的完整性，你怎么实现？
    lock接口在多线程最大优势是分别为读写提供了锁（还有其他功能，如Timeout）
    参考concurrentHashMap的实现

    3. Java中wait和sleep方法的不同？
    wait会释放锁，而sleep会一直持有锁。wait通常被用于线程间交互。sleep通常被用于暂停执行

    4.用Java写代码来解决生产者——消费者问题
    与上面的问题很类似，但这个问题更经典，有些时候面试都会问下面的问题。在Java中怎么解决生产者——消费者问题，当然有很多解决方法，我已经分享了一种用阻塞队列实现的方法。
    有些时候他们甚至会问怎么实现哲学家进餐问题

    5.你将如何使用thread dump？你将如何分析Thread dump？
    在UNIX中你可以使用kill -3，然后thread dump将会打印日志，在windows中你可以使用”CTRL+Break”。非常简单和专业的线程面试问题，
    但是如果他问你怎样分析它，就会很棘手

     */
}
