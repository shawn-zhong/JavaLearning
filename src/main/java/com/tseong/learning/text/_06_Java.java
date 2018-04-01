package com.tseong.learning.text;

public class _06_Java {

    /*

    调试JVM的方法:
    - 应用程序打开JMX接口，然后使用JConsole或JVisualVM
    - 使用飞行记录器（jcmd jmc）：-XX:+UnlockCommercialFeatures -XX:+FlightRecorder，使用jcmd打印处dump信息，使用jmc.exe分析
    - jps命令打印出进程状态信息（通过RMI协议查询开启了RMI服务的远程虚拟机进程状态）
        jps [options] [hostid]
    - jstat：虚拟机同级信息监视工具：（看JVM垃圾回收）
        jstat -gcutil [进程号]

        jstat -gcutil 25444 1000 5  (每隔1000毫秒查询一次，查询5次)
        S0     S1     E      O      P     YGC     YGCT    FGC    FGCT     GCT

        S0      S1      E      O      P         YGC     YGCT       FGC   FGCT     GCT
        58.34   0.00    71.44  23.88  72.08     85498   220.502    14    1.182   221.683
        0.00    33.34   35.76  24.32  72.08     85499   220.503    14    1.182   221.685
        25.00   0.00    40.51  24.33  72.08     85500   220.505    14    1.182   221.687
        0.00    50.00   36.37  24.33  72.08     85501   220.507    14    1.182   221.688
        0.00    0.00    0.00   24.35  72.08     85502   220.509    14    1.182   221.691

        YGC - 表示Minor GC的次数，YGCT － 表示Minor GC的总耗时
        FGC - 表示Full GC的次数， FGCT - 表示Full GC的总耗时


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


    * JDK1.8新特性
    1. HashMap中的红黑树： HashMap中链的长度大于8时采用红黑树的结构存储
    2. ConcurrentHashMap
        － JDK1.7时采用segement，默认16个桶
        － JDK1.8采用了CAS算法和红黑树
    3. JDK1.8没有永久代，取而代之的是MetaSpace元空间，用的是物理内存
    4. Lambda表达式
    5. 四大函数式接口
    6. Stream
    7. Fork/Join框架
    8. Optional类

    ＊ JDK1.7新特性
    1. 对集合类的语言支持                      // List<String> list = ["item"];
    2. 自动资源管理                           // 把资源申请放在try代码块中，自动关闭。 要实现Closable接口
    3. 改进的通用实例创建类型推断（Diamond）     // Map<String, List<String>> anagrams = new HashMap<>()
    4. 数字字面量下划线支持                     // 很长的数字可读性不好，在Java 7中可以使用下划线分隔长int以及long了，如： int one_million = 1_000_000;
    5. switch中使用string
    6. 二进制字面量                           // byte aByte = (byte)0b001;  short aShort = (short)0b010;
    7. 简化可变参数方法调用

    http://blog.csdn.net/wlanye/article/details/51954855

    ＊ JDK1.6新特性
    1. Desktop类和SystemTray类
    2. 使用JAXB2来实现对象域XML之间的映射
    3. StAX
    4. 使用Compiler API
    5. 轻量级Http Server API
    6. 插入式注解处理API (Pluggable Annotation Processing API)
    7. 用Console开发控制台测序
    8. 对脚本语言的支持
    9. Common Annotation


    Java类的声明周期及其初始化时机 (注意类的初始化和实例化的区别)：
    1. 加载 Loading
    2. 连接 Linking
        2.1 验证 Verification
        2.2 准备 Preparation
        2.3 解析 Resolution
    3. 初始化 Initialization
    4. 使用 using
    5. 卸载 Unloading

    JVM有且只有五种情况必须立即对类进行初始化：
    1. new 设置／获取／调用 static 字段，方法的时候
    2. 对类进行反射调用时 （Java.lang.reflect）
    3. 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化
    4. 当虚拟机启动时，用户需要指定要执行的类（main），初始化这个类
    5. 1.7动态语言


    JAVA基础知识：
    － OO特性：封装，继承，多态，抽象
    － JAVA修饰符：public，private，protected
    － JRE包括：Java虚拟机，applte插件
    － JDK包括：JRE，编译器，JavaDoc，调试器
    － JAVA支持8种基本数据类型：
        － boolean：true／false
        － char：2个字节，unicode
        － byte：1个字节，－128～127
        － short：2个字节，－2^15~2^15-1
        － int：4个字节，-2^31~2^31-1
        － long：8个字节，－2^63~2^63-1    => long a = 1L;
        － float: 4个字节，               => float f = 1.0f
        － double：8个字节
    - 浮点数比较大小：
        － Math.abs(a-b) = 0 or Math.abs(a-b) < 0.00000001
    - Java浮点数：1bit符号位 8bit指数位 23bit尾数
    - 定点数：BigDecimal
    - JAVA不支持继承多个类，但可以实现多个接口
    - 抽象类和接口的区别：
        － 只能实现一个抽象类；可以实现多个接口
        － 可以不实现多有的抽象方法
        － 接口中所有变量为final，所有函数为public；抽象方法可以不一样


    内存分配与回收策略：
    1） 对象优先在Eden分配，当Eden区没有足够空间进行分配时，虚拟机将发起一次MinorGC
    2） 大对象直接进入老年代
    3） 长期存活的对象将进入老年代（15次MinorGC存活）
    4） 动态对象年龄判定


    JAVA Stream流水线解决方案: https://www.cnblogs.com/CarpenterLee/archive/2017/03/28/6637118.html

    Stream上的所有操作分为两类：中间操作和结束操作，中间操作只是一种标记，只有结束操作才会触发实际计算。中间操作又可以分为无状态的(Stateless)和有状态的(Stateful)，
    无状态中间操作是指元素的处理不受前面元素的影响，而有状态的中间操作必须等到所有元素处理之后才直到最终结果，比如排序是有状态操作，在读取所有元素之前并不能确定排序结果；
    结束操作又可以分为短路操作和非短路操作，短路操作是指不用处理全部元素就可以返回结果，比如找到第一个满足条件的元素。之所以要进行如此细致的划分，


    我们大致能够想到，应该采用某种方式记录用户每一步大操作，当用户调用结束操作时将之前记录的操作叠加到一起在一次迭代中全部执行掉。沿着这个思路，有几个问题需要解决；
    1. 用户的操作如何记录？
    2. 操作如何叠加？
    3. 叠加之后的操作如何执行？
    4. 执行后的结果（如果有在哪里）

    1. 用户的操作如何记录？
        图中通过Collection.Stram()方法得到Head也就是stage0, 紧接着调用一系列的中间操作，不断产生新的Stream。这些Stream对象以双向链表的形式组织在一起，构成整个流水线。由于每个Stage都记录
        了前一个Stage和本次的操作以及回调函数，依靠这种结构就能建立骑对数据源的所有操作。这就是Stream记录操作的方式。（Stream由某种数据结构产生，每次调用中间操作都会产生一个新的Stream）
    2. 操作如何叠加？
        通过Sink接口的协议协调Stage之间的调用关系。每个Stage都会将自己的操作封装到一个Sink里，前一个Stage只需要调用后一个Stage的accept（）方法即可，并不需要直到其内部是如何处理的。当然对于
        有状态的操作，Sink的begin（）和end（）方法也是必须实现的。比如Sorted（）是一个有状态的中间操作，其对应的Sink.begin（）方法可能创建一个盛放结果的容器，而accept（）方法负责将元素添加到
        该容器，最后end（）负责对容器进行排序。对于短路操作
     */

}
