package com.tseong.learning.text;

public class _07_Java {

    /*

    JAva线程池不建议用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式更加明了，而且避免了资源耗尽的风险
    通过Executors返回的线程池有以下弊端：
    1）FixedThreadPool 和 SingleThreadPool：允许请求的队列长度为Integer.MAX_VALUE, 可能OOM
    2) CachedThreadPool 和 ScheduledThreadPool : 允许创建线程的数量为Integer.MAX_VALUE, 可能会创建大量的线程，OOM

    实践中用ScheduledThreadPool代替Timer, 因为Timer有以下缺陷：
    1） 一个异常发生，Timer都会停止
    2） 修改Local时间，Timer会归零


    Java容器包括两类 （需要再完善）：
    1） <Interface> Collection ：
        a) <Interface> Set      :   Hashset, LinkedHashset
        b) <Interface> List     :   ArrayList, LinkedList, Vector(已废弃)
        c) <Interface> Queue    :   ConcurrentLinkedQueue, LinkedBlockingQueue, PriorityQueue, PriorityBlockingQueue

    2) <Interface> Map  :   HashTable(已废弃) LinkedHashMap, HashMap
        a) <Interface> SortedMap : TreeMap


     */

}
