package com.tseong.learning.basic.container.concurrent.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


public class _2_ThreadSafeDemo {

    class ProducerRunnable implements Runnable {
        private CountDownLatch latch;
        private int index;
        private Map<Integer, Integer> map;

        ProducerRunnable(CountDownLatch l, int index, Map<Integer, Integer> map) {
            latch = l;
            this.index = index;
            this.map = map;
        }

        public void run() {

            for (int i = index * 200000; i< (index+1)*200000; i++) {
                map.put(i, i);
            }

            // countdown --
            latch.countDown();
        }
    }

    class ConsumerRunnable implements Runnable {
        private CountDownLatch latch;
        private int index;
        private Map<Integer, Integer> map;

        ConsumerRunnable(CountDownLatch l, int index, Map<Integer, Integer> map) {
            latch = l;
            this.index = index;
            this.map = map;
        }

        public void run() {

            for (int i = index * 200000; i< (index+1)*200000; i++) {
                if (map.get(i) == i) {
                    map.remove(i);
                }
            }

            // countdown --
            latch.countDown();
        }
    }

     void testMapWithMultiThreads(Map<Integer, Integer> map, int threadCount) throws InterruptedException {

        System.out.println("Now to test " + map.getClass().toString());

        // 10 threads to produce
        CountDownLatch latchProducer = new CountDownLatch(threadCount);
        long start = System.currentTimeMillis();

        for (int i=0; i<threadCount; i++) {
            new Thread(new ProducerRunnable(latchProducer, i, map)).start();
        }

        latchProducer.await();
        long elapse = System.currentTimeMillis() - start;
        System.out.println(map.getClass().toString() + " PRODUCING costs (millis) : " + elapse);
        System.out.println("map has items : " + map.size());

        // 10 threads to consume
        CountDownLatch latchConsumer = new CountDownLatch(threadCount);
        start = System.currentTimeMillis();

        for (int i=0; i<threadCount; i++) {
            new Thread(new ConsumerRunnable(latchConsumer, i, map)).start();
        }

        latchConsumer.await();
        elapse = System.currentTimeMillis() - start;
        System.out.println(map.getClass().toString() + " CONSUMING costs (millis) : " + elapse);
        System.out.println("map remained items : " + map.size());
    }

    public static void main(String[] args) throws InterruptedException {
        _2_ThreadSafeDemo instance = new _2_ThreadSafeDemo();
        int threadsCount = 30;

        // 1) 使用Collections.synchronizedMap测试分别用10个线程写和十个线程读耗时多久
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<Integer, Integer>(threadsCount*200000));
        instance.testMapWithMultiThreads(map, threadsCount);

        // 2) 使用concurrentMap测试读写
        map = new ConcurrentHashMap<Integer, Integer>(threadsCount*200000);
        instance.testMapWithMultiThreads(map, threadsCount);

        // 3) 使用HashTable（已废弃）测试读写
        map = new Hashtable<Integer, Integer>(threadsCount*200000);
        instance.testMapWithMultiThreads(map, threadsCount);

        // 4) 使用非线程安全的HashMap测试读写 (仅作为对比用)
        map = new HashMap<Integer, Integer>(threadsCount*200000);
        instance.testMapWithMultiThreads(map, threadsCount);
    }
}

/* 测试结果 耗时 Hashtable , ConcurrentHashMap < Collections.Synchronized

Now to test class java.util.Collections$SynchronizedMap
class java.util.Collections$SynchronizedMap PRODUCING costs (millis) : 8108
map has items : 6000000
class java.util.Collections$SynchronizedMap CONSUMING costs (millis) : 1756
map remained items : 0
Now to test class java.util.concurrent.ConcurrentHashMap
class java.util.concurrent.ConcurrentHashMap PRODUCING costs (millis) : 6164
map has items : 6000000
class java.util.concurrent.ConcurrentHashMap CONSUMING costs (millis) : 309
map remained items : 0
Now to test class java.util.Hashtable
class java.util.Hashtable PRODUCING costs (millis) : 5188
map has items : 6000000
class java.util.Hashtable CONSUMING costs (millis) : 1083
map remained items : 0
Now to test class java.util.HashMap
Exception in thread "Thread-211" Exception in thread "Thread-212" Exception in thread "Thread-213" Exception in thread "Thread-214" java.lang.NullPointerException
	at com.tseong.learning.basic.container.concurrent.map.PerformanceTest2$ConsumerRunnable.run(_2_ThreadSafeDemo.java:51)
	at java.lang.Thread.run(Thread.java:745)
java.lang.NullPointerException
	at com.tseong.learning.basic.container.concurrent.map.PerformanceTest2$ConsumerRunnable.run(_2_ThreadSafeDemo.java:51)
	at java.lang.Thread.run(Thread.java:745)
java.lang.NullPointerException
	at com.tseong.learning.basic.container.concurrent.map.PerformanceTest2$ConsumerRunnable.run(_2_ThreadSafeDemo.java:51)
	at java.lang.Thread.run(Thread.java:745)
java.lang.NullPointerException
	at com.tseong.learning.basic.container.concurrent.map.PerformanceTest2$ConsumerRunnable.run(_2_ThreadSafeDemo.java:51)
	at java.lang.Thread.run(Thread.java:745)
class java.util.HashMap PRODUCING costs (millis) : 4789
map has items : 1731643

 */


/*

作者：冰封
链接：https://www.zhihu.com/question/28482635/answer/141740338
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

看完，也许对你理解有些帮助。在项目中也会经常用到ConcurrentHashMap做一些缓存。归根到底：一致性与效率之间的一种权衡选择关系！！
请看下文分解相比同步锁synchronizedMap或者HashTable，ConcurrentHashMap引入了分段锁（segmentation），无需锁住全局，不论它变得多么大，
仅仅需要锁定map的某个部分。
1、优点：体现在效率方面，ConcurrentHashMap在线程安全的基础上提供了更好的写并发能力，仅仅需要锁定map的某个部分，
而其它的线程不需要等到迭代完成才能访问map。
2、缺点：体现在一致性方面，既然这么好，为什么不能替代其他的map，比如HashTable，因为ConcurrentHashMap是弱一致！
3、解答题主的疑惑：什么是弱一致性，我举个简单的例子，例如题主说的clear方法！因为没有全局的锁，在清除完一个segments之后，正在清理下一个segments的时候，
已经清理segments可能又被加入了数据，因此clear返回的时候，ConcurrentHashMap中是可能存在数据的。因此，clear方法是弱一致的。！！总结！！
ConcurrentHashMap的弱一致性主要是为了提升效率，但是成为弱一致。Hashtable为了线程安全的强一致性，就需要全局锁，降低效率。一致性与效率之间的一种权衡选择关系

 */

/*

https://www.cnblogs.com/grefr/p/6094888.html

 */