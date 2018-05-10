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
    - jinfo: 实时地查看和调整虚拟机各项参数。
    - jmap : 用于生成堆转储快照 （dump文件）， 并使用jhat进行分析
    - jstack : Java堆栈跟踪工具



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
    7. Optional类

    ＊ JDK1.7新特性
    1. 对集合类的语言支持                      // List<String> list = ["item"];
    2. 自动资源管理                           // 把资源申请放在try代码块中，自动关闭。 要实现Closable接口
    3. 改进的通用实例创建类型推断（Diamond）     // Map<String, List<String>> anagrams = new HashMap<>()
    4. 数字字面量下划线支持                     // 很长的数字可读性不好，在Java 7中可以使用下划线分隔长int以及long了，如： int one_million = 1_000_000;
    5. switch中使用string
    6. 二进制字面量                           // byte aByte = (byte)0b001;  short aShort = (short)0b010;
    7. 简化可变参数方法调用
    8. Fork/Join框架

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
    4. 当虚拟机启动时，用户需要指定要执行的类（_01_Api），初始化这个类
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
    结束操作又可以分为短路操作和非短路操作，短路操作是指不用处理全部元素就可以返回结果，比如找到第一个满足条件的元素。之所以要进行如此细致的划分，之所以要进行如此精细的
    化划分，是因为底层对每一种情况的处理方式butong
    中间操作：
        - 无状态：unordered(), filter(), map(), mapToInt(), mapToLong(), mapToDouble(), flatMap(), flatMapToInt(), flatMapToLong(), flatMapToDouble(), peek()
        - 有状态: distinct(), sorted(), limit(), skip()
    结束操作：
        - 非短路操作：forEach(), forEachOrdered(), toArray(), reduce(), collect(), max(), min(), count()
        - 短路操作：anyMatch(), allMatch(), noneMatch(), findFirst(), findAny()


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
        该容器，最后end（）负责对容器进行排序。对于短路操作Sink.cancellationRequested()也是必须实现的，比如Stream.findFirst()是短路操作，只要找到一个元素，cancellationRequested()就应该返回true，
        以便调用者尽快结束查找。Sink的四个接口方法常常互相协作，共同完成计算任务。实际上Stream API内部实现的本质，就是如何重载Sink的这四个接口方法
        有了Sink对操作的包装，Stage之间的调用问题就解决了，执行时只需要从流水线的head开始对数据源依次调用每个Stage对应的Sink.{begin(), accept(). cancellationRequested(). end()}方法就可以了。
        一种可能的Sink.accept()方法流程是这样的：
        void accept(U u) {
            1. 使用当前Sink包装的回调函数处理u
            2. 将处理结果传递给流水线下游的sink
        }

        举例 Sorted （ 有状态的中间操作 ）方法Sink的四个接口的协同工作：
        1. 首先begin()方法告诉Sink参与排序的元素个数，方便确定中间结果容器的大小
        2. 之后通过accept()方法将元素添加到中间结果当中，最终执行时调用者会不断调用该方法，直到便利所有元素
        3. 最后end（）方法告诉Sink所有元素遍历完毕，启动排序步骤。排序完成后将结果传递给下游的Sink
        4. 如果下游的Sink是短路操作，将结果传递给下游时不断询问下游cancellationRequested()是否可以结束处理

    3. 叠加之后的操作如何执行
        Sink完美封装了Stream每一步操作，并给出了[处理->转发]的模式来叠加操作。这一连串的齿轮已经咬合，就差最后一步拨动齿轮启动执行。是什么启动这一连串的操作呢？也许你已经想到了启动的原始动力就是
        结束操作(Terminal Operation)，一旦调用某个结束操作，就会触发整个流水线的执行。
        结束操作之后不能再有别的操作，所以结束操作不会创建新的流水线阶段(Stage)，直观的说就是流水线的链表不会在往后延伸了。结束操作会创建一个包装了自己操作的Sink，这也是流水线中最后一个Sink，
        这个Sink只需要处理数据而不需要将结果传递给下游的Sink（因为没有下游）。对于Sink的[处理->转发]模型，结束操作的Sink就是调用链的出口。
        为什么要产生一个新对象而不是返回一个Sink字段？这是因为使用opWrapSink()可以将当前操作与下游Sink（上文中的downstream参数）结合成新Sink。试想只要从流水线的最后一个Stage开始，不断调用上一个Stage的opWrapSink()方法直到最开始
        （不包括stage0，因为stage0代表数据源，不包含操作），就可以得到一个代表了流水线上所有操作的Sink，用代码表示就是这样：
        现在流水线上从开始到结束的所有的操作都被包装到了一个Sink里，执行这个Sink就相当于执行整个流水线

    4. 执行后的结果在哪里
        返回类型    对应的结束操作
        boolean	    anyMatch()      allMatch()      noneMatch()
        Optional	findFirst()     findAny()
        归约结果	    reduce()        collect()
        数组	        toArray()

        1. 对于表中返回boolean或者Optional的操作（Optional是存放一个值的容器）, 由于值返回一个值，只需要在对应的Sink中记录这个值，等到执行结束时返回就可以了
        2. 对于规约操作，最终结果放在用户调用时指定的容器中（容器类型通过收集器指定）。collect(), reduce(), max(), min()都是规约操作，虽然max()和min()也是
            返回一个Optional，但事实上底层是通过调用reduce()方法实现的
        3. 对于返回是数组的情况，毫无疑问的结果会放在数组当中。这么说当然是对的，但在最终返回数组之前，结果其实是存储在一种叫做Node的数据结构中的，Node是一种多叉树结构，元素存储在树的叶子当中，
        并且一个叶子节点可以存放多个元素，这样做是为了并行执行方便。




    Lambda的底层实现：https://www.cnblogs.com/WJ5888/p/4667086.html

    Lambda的使用：

    @FunctionalInterface
        interface Print<T> {
        public void print(T x);
    }

    public class Lambda {
        public static void PrintString(String s, Print<String> print) {
            print.print(s);
        }
        public static void _01_Api(String[] args) {
            PrintString("test", (x) -> System.out.println(x));
        }
    }

    通过代码编织，增加一个方法和一个类文件，最终的Lambda表达式等价于以下形式
    @FunctionalInterface
        interface Print<T> {
        public void print(T x);
    }

    public class Lambda {
        public static void PrintString(String s, Print<String> print) {
            print.print(s);
        }
        private static void lambda$0(String x) {
            System.out.println(x);
        }
        final class $Lambda$1 implements Print{
            @Override
            public void print(Object x) {
                lambda$0((String)x);
            }
        }
        public static void _01_Api(String[] args) {
            PrintString("test", new Lambda().new $Lambda$1());
        }
    }


     */

}
