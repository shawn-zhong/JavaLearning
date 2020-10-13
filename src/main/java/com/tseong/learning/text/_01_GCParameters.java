package com.tseong.learning.text;

import com.sun.codemodel.internal.JAnnotationValue;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class _01_GCParameters {


    public static void main(String[] args) {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }

        /*

        默认（没有设置）输出结果：(-XX:UseParallelGC)

        PS Scavenge    -- PS Scavenge，全称是ParallelScavenge，是个并行的copying算法的收集器
        PS MarkSweep   -- 负责执行full GC（收集整个GC堆，包括young gen、old gen、perm gen）的是PS MarkSweep, 但整个收集器并不是并行的，而在骨子里是跟Serial Old是同一份代码，是串行收集的。
                            其算法是经典的LISP2算法，是一种mark-compact GC（不要被MarkSweep的名字骗了）

        抛开上面令人迷惑的命令方式，直接说明：
        在JDK8中，默认使用下面GC算法：
        Young Generation : ParallelScavenge (某种 copying 算法)
        Old Generatoin :   SerialOld (某种 mark-compact 算法).


        -XX:+UseConcMarkSweepGC (使用CMS收集器)
        Young Generation : ParNew
        Old Generation : ConcurrentMarkSweep


        -XX:+UseParallelOldGC
        Young Generation : Parallel Scavenge
        Old Generation : ParOld

         */
    }


    /*

    JAVA内存区域划分：
    1. 程序计数器（字节码的行号指示器）
    2. stack (栈)：创建盏帧存储线程的局部变量表、操作数栈、动态链接、方法出口等信息；会抛出 StackOverFlowError 或 OutOfMemoryError
    3. Native Method Stack (本地方法栈)：服务于native方法 （Hotspot直接把statck和Native Method Stack合二为一）
    4. 堆（Heap）：（堆上可能有freelist，表明哪些内存可用）
        - 新生代（Eden, Survivor(S0, S1) 供三个，默认Eden占80%，S0, S1各占10%，即默认-XX:SurvivorRatio=8）
          - Eden区容纳不下对象时，通过分配担保机制直接进入老年代
          - 大对象直接进入老年代，但默认设置为0 （-XX：PretenureSizeThreshhold=0），一定会现在新生代尝试
          - 长期存活的对象进入老年代（-XX:MaxTenuringThreashhod）, 默认15次
        - 老年代 （默认老年代占 2/3， 即 -XX:newRatio=2）
    5. 方法区（Method Area, 即perm区）（JDK8取消了MethodArea而用MetaSpace代替，类的元数据放入native memory，字符串池和类的静态变量放入java堆中）: 存储类信息、常量、静态变量，即时编译器编译后的代码等数据 （OOM）
    6. 直接内存（有人叫堆外内存）：（）（OOM）(可通过 -XX:MaxDirectMemorySize指定如果不指定默认等于-Xmx) （NIO可以直接分配直接内存并自动释放、unsafe类可以手动分配直接内存手动释放）
        shawn：直接内存不会主动触发GC，而是等老年代满了之后进行FULLGC随便清除掉（bytebuffer虚引用），但是unsafe难说
        FULL GC发生条件：1, system.gc 2. 快OOM的时候（老年代、metaspace，）3.晋升失败（大对象担保、老年代碎片太多，）

    对象的内存布局：
        对象头（2或3个字（数组），一个字32/64bit） + 实例数据 + 对齐填充（凑成8字节的倍数）


    程序计数器、栈、本地方法 3个区域随线程而生而灭

    如何判断对象已死：引用计数法、可达性分析法（GC roots：栈中引用的对象、方法区中静态属性引用的对象、方法区中常量引用的对象、JNI（即Native方法）引用对的对象）
    引用分成4中：强引用（就是最普通的引用）、软引用（OOM前进行回收）、弱引用（下次GC时进行回收）、虚引用（shawn：回收后可以在队列查到一条记录）
    类的finalize方法只能执行一次，但在finalize方法中自己还能救活自己一次；


    GC的基本算法：
    1. 复制算法copying(没有碎片、实现简单效率高、但内存利用率只有50%)
    2. 标志-清除算法（Mark-Sweep）：不足：标志和清除的效率都不高，会产生内存碎片
    3。标志-整理算法（Mark-Compact）：

    SerialNew（新生代 copying） : 单线程，而且需要stopTheWorld，新生代采取复制算法
    ParNew（新生代算法，copying）：CMS的默认新生代算法（另一个可用新生代算法为SerialNew），可以通过 -XX:ParallelGCThreads进行线程数量控制
    Parallel Scavenge(新生代算法，copying)：可自适应调节策略、可设置最大停顿时间，可直接设置吞吐量大小；
    SerialOld（老年代 mark-compact），不仅默认与 parallel scavenge配合工作，还作为CMS的后备预案(因为CMS可以和用户程序并发，这时可能内存还不够用，所以换用SerialOld)
    CMS（老年代，mark-sweep算法）：优点：并发，停顿时间少；缺点：占cpu、浮动垃圾、内存碎片
      - 初识标志（CMS initial mark）：标志Root内直接关联到的对象；stop the world
      - 并发标志（CMS concurrent mark）：可以与用户用户线程并发执行
      - 重新标志（CMS remark）：修正并发标志期间因用户程序继续运作而导致标志产生变动的那一部分对象的标志记录
      - 并发清除（CMS concurrent sweep）
    G1（老年代-新生代一起）：mark-compact，copying可预测的停顿时间模型（优先列表，优先收回价值最大的region）；将整个java堆划分为多个大小相等的独立区域（Region），虽然还保留有新生代和老年代的概念，但不是物理隔离了，而是他们都是一部分Region的集合


    CMS参数：
      -XX：CMSInitialtingOccupancyFraction=68 (当老年代使用了xx%空间后就会激活CMS)
      -XX：UseCMSCompactAtFullCollection (默认开)：FullGC时compact


     */




    /*

    JAVA 参数配置（Oracle）：

    LIB_JARS=`echo /opt/app/lib/*.jar | tr ' ' ':'`
    JAVA_OPTS=" -Ddubbo.protocol.port=$DUBBO_PROTOCOL_PORT
                -Dserver:port=$SERVER_PORT
                -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE
                -Dlogname=$LOG_NAME
                -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "


    # 为了避免Perm区满引起的full gc，建议开启CMS回收Perm区选项：+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled

    JAVA_MEM_OPTS="
                -server
                -Xmx2048m
                -Xms512m
                -XX:PermSize=128m （JDK8无permsize参数）
                -Xss256k
                -XX:+DisableExplicitGC
                -XX:+UseConcMarkSweepGC
                -XX:+CMSParallelRemarkEnabled （为了减少第二次暂停的时间，开启并行remark）
                -XX:+UseCMSCompactAtFullCollection （可以通过配置适当的CMSFullGCsBeforeCompaction来调整性能）
                -XX:CMSInitiatingOccupancyFraction=70 （老年代70%的时候开始进行CMS收集）
                -XX:+UseCMSInitiatingOccupancyOnly （只是用设定的回收阈值(上面指定的70%),如果不指定,JVM仅在第一次使用设定值,后续则自动调整）
                -XX:LargePageSizeInBytes=128m （https://blog.csdn.net/xihuanyuye/article/details/83930703）
                -XX:+UseFastAccessorMethods
                -Xloggc:/apps/logs/gc/$LOG_NAME.log -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps "

    JAVA_JMX="  -Dcom.sun.management.jmxremote=true
                -Dcom.sun.management.jmxremote.rmi.port=1000
                -Dcom.sun.management.jmxremote.port=1000
                -Dcom.sun.management.jmxremote.ssl=false
                -Dcom.sun.management.jmxremote.authenticate=false
                -Dcom.sun.management.jmxremote.local.only=false
                -Djava.rmi.server.hostname=192.168.1.186 "

    JAVA_OOM_OPTS="
                -XX:+HeapDumpOnOutOfMemoryError
                -XX:HeapDumpPath=/apps/logs/heapdump/$APP_NAME "

    nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_OOM_OPTS $JAVA_JMX -classpath $LIB_JARS $MAIN_FUNCTION > ${LOG_NAME}.log 2>&1 &



    JAVA 参数配置（OpenJ9）：
    JAVA_OPTS="
                -Dlogname=$APP_NAME
                -Ddubbo.protocol.port=$DUBBO_PROTOCOL_PORT
                -Dserver:port=$SERVER_PORT
                -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE
                -Dlogname=$LOG_NAME
                -Djava.awt.headless=true
                -Djava.net.preferIPv4Stack=true "

    # set memory options ( app memory : 400m ~ 85% of container memory ); ignore -XX:InitialRAMPercentage=; ignore -Xmx2g -Xms400m
    JAVA_MEM_OPTS="
                -Xss256k
                -Xms400m
                -XX:+UseContainerSupport
                -XX:MaxRAMPercentage=85
                -XX:+IgnoreUnrecognizedVMOptions
                -XX:+DisableExplicitGC
                -XX:+UseConcMarkSweepGC
                -XX:+CMSParallelRemarkEnabled
                -XX:+UseCMSCompactAtFullCollection
                -XX:+UseCMSInitiatingOccupancyOnly
                -XX:CMSInitiatingOccupancyFraction=70
                -XX:LargePageSizeInBytes=128m
                -XX:+UseFastAccessorMethods
                -Xloggc:/apps/logs/gc/$LOG_NAME.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGC "

    # enable openj9 features
    JAVA_CONTAINER_OPTS="
                -XX:+IdleTuningCompactOnIdle
                -XX:+IdleTuningGcOnIdle
                -Xshareclasses
                -Xquickstart "

    # set OOM heapdump path
    JAVA_OOM_OPTS=" -XX:HeapDumpPath=/apps/logs/heapdump/$APP_NAME "

    # enable JMX (when needed)
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.rmi.port=1000 -Dcom.sun.management.jmxremote.port=1000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false -Djava.rmi.server.hostname=192.168.1.186 "

    echo -e `date` "Starting the service $APP_NAME...\c" >> shell.log
    echo -e "nohup java -server $JAVA_OPTS $JAVA_MEM_OPTS  $JAVA_CONTAINER_OPTS  $JAVA_OOM_OPTS -classpath $LIB_JARS $MAIN_FUNCTION > ${LOG_NAME}.log 2>&1 &" >> shell.log



     */








    /*


    什么时候触发FullGC：

    触发MinorGC(Young GC)
    虚拟机在进行minorGC之前会判断老年代最大的可用连续空间是否大于新生代的所有对象总空间

    1、如果大于的话，直接执行minorGC
    2、如果小于，判断是否开启HandlerPromotionFailure，没有开启直接FullGC
    3、如果开启了HanlerPromotionFailure, JVM会判断老年代的最大连续内存空间是否大于历次晋升的大小，如果小于直接执行FullGC
    4、如果大于的话，执行minorGC

    触发FullGC （shawn：快OOM的时候+YGC安全判断）
    老年代空间不足
         如果创建一个大对象，Eden区域当中放不下这个大对象，会直接保存在老年代当中，如果老年代空间也不足，就会触发Full GC。为了避免这种情况，最好就是不要创建太大的对象。
    持久代空间不足
        如果有持久代空间的话，系统当中需要加载的类，调用的方法很多，同时持久代当中没有足够的空间，就出触发一次Full GC
    YGC出现promotion failure
        promotion failure发生在Young GC, 如果Survivor区当中存活对象的年龄达到了设定值，会就将Survivor区当中的对象拷贝到老年代，如果老年代的空间不足，就会发生promotion failure， 接下去就会发生Full GC.
    统计YGC发生时晋升到老年代的平均总大小大于老年代的空闲空间
          在发生YGC是会判断，是否安全，这里的安全指的是，当前老年代空间可以容纳YGC晋升的对象的平均大小，如果不安全，就不会执行YGC,转而执行Full GC。
    显示调用System.gc

     */


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
}



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
*
*
*
* java -jar -server -Xms512m -Xfuture -XX:+UnlockCommercialFeatures -XX:+FlightRecorder \
        -Dcom.sun.management.jmxremote.port=1090 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
        pcrf-1.0-SNAPSHOT.jar


JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC
-XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods
-XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Xloggc:/apps/logs/gc/$LOG_NAME.log
-XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGC"

/*

容器里2.5G环境下，如果没有设定 -Xmx -Xmx，Max Memory is : 618.6875 Mb Init Memory is : 40.0 Mb Committed Memory is: 120.69140625 Mb Used Memory is 76.60406494140625 Mb
说明 xmx是 感知内存的 1/4
*
* */