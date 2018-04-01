package com.tseong.learning.text;

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

        PS Scavenge    -- PS Scavenge，全称是ParallelScavenge，是个并行的copying算法的收集器
        PS MarkSweep   -- 负责执行full GC（收集整个GC堆，包括young gen、old gen、perm gen）的是PS MarkSweep, 但整个收集器并不是并行的，而在骨子里是跟Serial Old是同一份代码，是串行收集的。
                            其算法是经典的LISP2算法，是一种mark-compact GC（不要被MarkSweep的名字骗了）

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
    9，和ParNew 收集器类似，是一个新生代收集器。
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


/*

        链接：https://www.zhihu.com/question/48780091/answer/113063216


        不奇怪，一切现象都是有原因的。先来了解些背景信息。在题主所使用的JDK 6 update 27的HotSpot VM里，-XX:+UseParallelGC会启用题主所说的配置（这也是HotSpot VM在Server Class Machine上的默认配置）。
        其中，负责执行minor GC（只收集young gen）的是PS Scavenge，全称是ParallelScavenge，是个并行的copying算法的收集器；而负责执行full GC（收集整个GC堆，包括young gen、old gen、perm gen）的是PS MarkSweep
        ——但整个收集器并不是并行的，而在骨子里是跟Serial Old是同一份代码，是串行收集的。其算法是经典的LISP2算法，是一种mark-compact GC（不要被MarkSweep的名字骗了）。
        （注意这个跟使用-XX:+UseParallelOldGC所指定的并不是同一个收集器，那个是PS Compact，是个并行的全堆收集器）
        ParallelScavenge这个GC套装的full GC有个很特别的实现细节，那就是：当触发full GC的时候，实际上会先使用PS Scavenge执行一次young GC收集young gen，然后紧接着去用PS MarkSweep执行一次真正的full GC收集全堆。
        所以说题主看到的现象就是很正常的一次ParallelScavenge的full GC而已。要控制这个行为，可以使用VM参数：
        product(bool, ScavengeBeforeFullGC, true,                                 \
        "Scavenge youngest generation before each full GC, "              \
        "used with UseParallelGC")
        指定 -XX:-ScavengeBeforeFullGC 就可以不在执行full GC的时候先执行一次PS Scavenge。

        jdk6/jdk6/hotspot: 7561dfbeeee5 src/share/vm/gc_implementation/parallelScavenge/psMarkSweep.cpp

        oid PSMarkSweep::invoke(bool maximum_heap_compaction) {
        // ...

        if (ScavengeBeforeFullGC) {
        PSScavenge::invoke_no_policy();
        }

        // ...
        }
        ================================题主说：
        这次FullGC为什么会被触发？回收前Old Gen的使用率是194M/910M，Survivor只占用了300多K，Eden则是0，此时Old Gen空间很富余，从YoungGen晋升的对象也只有300多K，PermGen也很富余……按照我的理解，似乎此时不应该触发FullGC啊？

        正因为上面说的，ParallelScavenge这套GC在触发full GC时实际上会先做一个young GC（PS Scavenge），再执行真正的full GC（PS MarkSweep），所以题主在看数据的时候就被弄晕了：题主实际看到的是在“真正的full GC”的数据，
        而这是在刚刚做完那个young GC后的，所以自然，此时edgen是空的，而survivor space里的对象都是活的。要看这次full GC为何触发，必须去看这个因为full GC而触发的young GC之前的状态才行。================================
        另外，做完full GC后old gen的使用量上升也是非常正常的行为。HotSpot的full GC实现中，默认young gen里所有活的对象都要晋升到old gen，实在晋升不了才会留在young gen。假如做full GC的时候，old gen里的对象几乎没有死掉的，
        而young gen又要晋升活对象上来，那么full GC结束后old gen的使用量自然就上升了。

*/


/*

常见GC方式：

Eden S0 S1      Tenured         Permenent

1. 绝大部分新生成的对象都放在Eden区，当Eden区将满，JVM会因申请不到内存，而触发Young GC ,进行Eden区+有对象的Survivor区(设为S0区)垃圾回收，把存活的对象用复制算法拷贝到一个空的Survivor(S1)中，此时Eden区被清空，另外一个Survivor S0也为空。
下次触发Young GC回收Eden+S0，将存活对象拷贝到S1中。新生代垃圾回收简单、粗暴、高效。

2. Old区(每次Young GC都会使Survivor区存活对象值+1，直到阈值)。 3.Old区也会进行垃圾收集(Young GC),发生一次 Major GC 至少伴随一次Young GC，一般比Young GC慢十倍以上。

3. JVM在Old区申请不到内存，会进行Full GC。Old区使用一般采用Concurrent-Mark–Sweep策略回收内存。
总结：Java垃圾回收器是一种“自适应的、分代的、停止—复制、标记-清扫”式的垃圾回收器。

缺点：

GC过程中会出现STW(Stop-The-World)，若Old区对象太多，STW耗费大量时间。
CMS收集器对CPU资源很敏感。
CMS收集器无法处理浮动垃圾，可能出现“Concurrent Mode Failure”失败而导致另一次Full GC的产生。
CMS导致内存碎片问题。

在G1中，堆被划分成 许多个连续的区域(region)。每个区域大小相等，在1M~32M之间。JVM最多支持2000个区域，可推算G1能支持的最大内存为2000*32M=62.5G。区域(region)的大小在JVM初始化的时候决定，也可以用-XX:G1HeapReginSize设置。
在G1中没有物理上的Yong(Eden/Survivor)/Old Generation，它们是逻辑的，使用一些非连续的区域(Region)组成的。
G1的新生代收集跟ParNew类似，当新生代占用达到一定比例的时候，开始出发收集。


G1虽然保留了CMS关于代的概念，但是代已经不是物理上连续区域，而是一个逻辑的概念。在标记过程中，每个区域的对象活性都被计算，在回收时候，就可以根据用户设置的停顿时间，选择活性较低的区域收集，这样既能保证垃圾回收，又能保证停顿时间，
而且也不会降低太多的吞吐量。
Remark阶段新算法的运用，以及收集过程中的压缩，都弥补了CMS不足。引用Oracle官网的一句话：“G1 is planned as the long term replacement for the Concurrent Mark-Sweep Collector (CMS)”。

 */
