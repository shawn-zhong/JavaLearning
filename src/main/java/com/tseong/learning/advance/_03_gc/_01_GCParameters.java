package com.tseong.learning.advance._03_gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class _01_GCParameters {


    public static void main(String[] args) {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }

        /* 输出结果：
        PS Scavenge
        PS MarkSweep

         */
    }

    /*

    1 标记 - 清除算法
    2 复制算法
    3 标记 - 整理算法

    ----

    1. Serial（ -XX:+UseSerialGC ）. 新生代收集器算法。单线程收集器。采用复制算法避免内存碎片但是要预留一块空内存

    2、SerialOld（-XX:+UseSerialGC）
    SerialOld是Serial收集器的老年代收集器版本，它同样是一个单线程收集器，这个收集器目前主要用于Client模式下使用。如果在Server模式下，
    它主要还有两大用途：一个是在JDK1.5及之前的版本中与Parallel Scavenge收集器搭配使用，
    另外一个就是作为CMS收集器的后备预案，如果CMS出现Concurrent Mode Failure，则SerialOld将作为后备收集器。
    使用算法：标记 - 整理算法

    3、ParNew（-XX:+UseParNewGC）
    ParNew其实就是Serial收集器的多线程版本。除了Serial收集器外，只有它能与CMS收集器配合工作。
    使用算法：复制算法
    ParNew是许多运行在Server模式下的JVM首选的新生代收集器。但是在单CPU的情况下，它的效率远远低于Serial收集器，所以一定要注意使用场景。

    4. ParallelScavenge（-XX:+UseParallelGC）
    ParallelScavenge又被称为吞吐量优先收集器，和ParNew 收集器类似，是一个新生代收集器。
    使用算法：复制算法
    ParallelScavenge收集器的目标是达到一个可控件的吞吐量，所谓吞吐量就是CPU用于运行用户代码的时间与CPU总消耗时间的比值，
    即吞吐量 = 运行用户代码时间 / （运行用户代码时间 + 垃圾收集时间）。如果虚拟机总共运行了100分钟，其中垃圾收集花了1分钟，那么吞吐量就是99% 。

    5、ParallelOld（-XX:+UseParallelOldGC）
    ParallelOld是并行收集器，和SerialOld一样，ParallelOld是一个老年代收集器，是老年代吞吐量优先的一个收集器。这个收集器在JDK1.6之后才开始提供的，
    在此之前，ParallelScavenge只能选择SerialOld来作为其老年代的收集器，这严重拖累了ParallelScavenge整体的速度。而ParallelOld的出现后，“吞吐量优先”收集器才名副其实！
    使用算法：标记 - 整理算法
    在注重吞吐量与CPU数量大于1的情况下，都可以优先考虑ParallelScavenge + ParalleloOld收集器。

    6、CMS （-XX:+UseConcMarkSweepGC）
    CMS是一个老年代收集器，全称 Concurrent Low Pause Collector，是JDK1.4后期开始引用的新GC收集器，在JDK1.5、1.6中得到了进一步的改进。
    它是对于响应时间的重要性需求大于吞吐量要求的收集器。对于要求服务器响应速度高的情况下，使用CMS非常合适。
    CMS的一大特点，就是用两次短暂的暂停来代替串行或并行标记整理算法时候的长暂停。
    使用算法：标记 - 清理
    CMS的缺点：1、内存碎片 2、需要更多的CPU资源 3、需要更大的堆空间

    7、GarbageFirst（G1）
    这是一个新的垃圾回收器，既可以回收新生代也可以回收老年代，SunHotSpot1.6u14以上EarlyAccess版本加入了这个回收器，
    Sun公司预期SunHotSpot1.7发布正式版本。通过重新划分内存区域，整合优化CMS，同时注重吞吐量和响应时间。杯具的是Oracle收购这个收集器之后将其用于商用收费版收集器。
    因此目前暂时没有发现哪个公司使用它，这个放在之后再去研究吧。

    */

    /*

    Young Generation        Old Generation          JVM Options

    Serial                  Serial                  -XX:+UseSerialGC
    Parallel Scavenge       Serial                  -XX:+UseParallelGC -XX:-UseParallelOldGC (?)
    Parallel New            Serial                  -XX:+UseParNewGC
    Serial                  Parallel Old            N/A
    Parallel Scavenge       Parallel Old            -XX:+UseParallelGC -XX:+UseParallelOldGC
    Parallel New            Parallel Old            N/A
    Serial                  CMS                     -XX:-UseParNewGC -XX:+UseConcMarkSweepGC
    Parallel Scavenge       CMS                     N/A
    Parallel New            CMS                     -XX:+UseParNewGC -XX+UseConcMarkSweepGC
    G1                                              -XX:+UseG1GC

    PS: UseConcMarkSweepGC is "ParNew" + "CMS" + "Serial Old". "CMS" is used most of the time to collect
    the tenured generation. "Serial Old" is used when a concurrent mode failure occurs.

     */
}


//https://zhuanlan.zhihu.com/p/25539690


/*

/ Shawn >> java -XX:+PrintFlagsFinal -version | grep :
     intx CICompilerCount                          := 3                                   {product}
    uintx InitialHeapSize                          := 134217728                           {product}
    uintx MaxHeapSize                              := 2147483648                          {product}
    uintx MaxNewSize                               := 715653120                           {product}
    uintx MinHeapDeltaBytes                        := 524288                              {product}
    uintx NewSize                                  := 44564480                            {product}
    uintx OldSize                                  := 89653248                            {product}
     bool PrintFlagsFinal                          := true                                {product}
     bool UseCompressedClassPointers               := true                                {lp64_product}
     bool UseCompressedOops                        := true                                {lp64_product}
     bool UseParallelGC                            := true                                {product}


/ Shawn >> java -XX:+PrintCommandLineFlags -version
-XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
java version "1.8.0_92"
Java(TM) SE Runtime Environment (build 1.8.0_92-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.92-b14, mixed mode)

 */