package com.tseong.learning.text;

public class _06_Java {

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


     */

}
