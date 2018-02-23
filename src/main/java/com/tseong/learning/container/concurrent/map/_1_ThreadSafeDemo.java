package com.tseong.learning.container.concurrent.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _1_ThreadSafeDemo {

    static void produceAndConsume(Map<Integer, Integer> map) throws InterruptedException {
        long start = System.currentTimeMillis();

        ProducerThread producer = new ProducerThread(map);
        producer.start();
        ConsumerThread consumer = new ConsumerThread(map);
        consumer.start();

        producer.join();
        consumer.join();

        long elapse = System.currentTimeMillis() - start;
        System.out.println(map.getClass().toString() + " costs (millis) : " + elapse);
        // 上面只是单线程读，单线程写，并不能很好的体现出多线程环境下的效率
    }

    public static void main(String[] args) throws InterruptedException {

        // 有三种方法进行map的线程安全操作：

        // 1）使用Collections.synchronizedMap来同步HashMap
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        produceAndConsume(map);

        // 2) 使用ConcurrentHashMap
        map = new ConcurrentHashMap<Integer, Integer>();
        produceAndConsume(map);

        // 3) HashTable也是线程安全的，单现在已经废弃了
        // If you need the synchronization (provided by Hashtable), you can use Collections.synchronizedMap() to decorate
        // a normal HashMap or use ConcurrentHashMap which allows concurrent reading and modifying without requiring locking.
        // But you'll be not be able to replace a Hashtable by a HashMap if you have a third tier library needing a HashMap or a Dictionary (super type of the HashMap) like the one in
        map = new Hashtable<Integer, Integer>();
        produceAndConsume(map);

        // 试一下非线程安全的HashMap(仅用作对比)
        map = new HashMap<Integer, Integer>();
        produceAndConsume(map);
    }

    static class ConsumerThread extends Thread {
        private Map<Integer, Integer> map;

        ConsumerThread(Map<Integer, Integer> map ) { this.map = map; }

        @Override
        public void run() {
            for (int i=0; i<2000000; ++i) {
                if (map.get(i) != null)
                    map.remove(i);
            }
        }
    }

    static class ProducerThread extends Thread {
        private Map<Integer, Integer> map;

        ProducerThread(Map<Integer, Integer> map) { this.map = map; }

        @Override
        public void run() {
            for (int i=0; i<2000000; ++i) {
                map.put(i, i);
            }
        }
    }
}


/**
 * (三者的异同)
 * 查看源码可知：HashTable（已废弃）直接使用sychronized修饰方法体，只能有一个线程进行操作；Collections.synchronizedMap()只是外面修饰了一层同步，还是使用synchronized进行线程安全处理
 *
 ConcurrentHashMap代码中可以看出，它引入了一个“分段锁”的概念，具体可以理解为把一个大的Map拆分成N个小的HashTable，根据key.hashCode()来决定把key放到哪个HashTable中。
 在ConcurrentHashMap中，就是把Map分成了N个Segment，put和get的时候，都是现根据key.hashCode()算出放到哪个Segment中：
 */