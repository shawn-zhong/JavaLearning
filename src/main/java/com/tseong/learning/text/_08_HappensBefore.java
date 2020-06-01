package com.tseong.learning.text;

public class _08_HappensBefore {

    /*

    另外，Java内存模型具备一些先天的“有序性”，即不需要通过任何手段就能够得到保证的有序性，这个通常也称为 happens-before 原则。如果
    两个操作的执行次序无法从happens-before原则推导出来，那么它们就不能保证它们的有序性，虚拟机可以随意地对它们进行重排序。

    - 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；->
                    这个规则是用来保证程序在单线程中执行结果的正确性，但无法保证程序在多线程中执行的正确性。
    - 锁定规则：一个unLock操作先行发生于后面对同一个锁的lock操作； -> 也就是说无论在单线程中还是多线程中，同一个锁如果出于被锁定的状态，那么必须先对锁进行了释放操作，后面才能继续进行 lock 操作。
    - volatile 变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作 -> 直观地解释就是，如果一个线程先去写一个变量，然后一个线程去进行读取，那么写入操作肯定会先行发生于读操作。
    - 传递规则：如果操作 A 先行发生于操作 B，而操作 B 又先行发生于操作 C，则可以得出操作 A 先行发生于操作 C -> 就是体现 happens-before 原则具备传递性

    - 线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作
    - 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
    - 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
    - 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始

    as-if-serial 语义：不管怎么重排序，（单线程）程序的执行结果不能被改变

    两个操作之间存在happens-before关系，并不意味着Java平台的具体实现必须要按照happens-before关系指定的顺序来执行；如果重排序之后的执行结果，
    与按happens-before关系来执行的结果一致，那么这种重排序并不非法（也就是说，JMM允许这种重排序）


    -------------

    - 使用Lmbench3可以测量上下文切换带来到消耗， 用vmstat可以测量上下文切换的次数

    减少上下文切换的方法：
    1. 无锁并发编程 - 比如Hash分段
    2. CAS
    3. 使用最少线程
    4. 协程 － 在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换
    5. （自己增加的：偏向锁的使用）

    避免死锁常见方法：
    1. 避免一个线程同时获取多个锁
    2. 尽量保证每个锁只占用一个资源
    3. 尝试使用定时锁如lock.tryLock(timeout)
    4. 数据库的加锁和解锁须在一个DB连接里

    Java中的每一个对象都可以作为锁
    － 对于普通同步方法，锁是当前实例对象
    － 对于静态同步方法，锁是当前类的class对象
    － 对于同步方法块，锁是Synchonized括号里的对象
    － 代码块同步是使用monitorenter和monitoerexit指令实现的，而方法同步是使用另外一种方式实现的
    － synchronized用的锁是存在Java对象头里面的

    锁一共有四种状态，从低到高是:
    - 无锁状态
    - 偏向锁状态     -> 当一个线程访问同步块并获取锁时，会在对相投和帧栈中到锁记录里存储锁偏向的线程ID，以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁
    - 轻量级锁状态   -> CAS自旋加锁解锁。缺陷：如果饥饿，自旋会耗时
    - 重量级锁状态   -> 不消耗cpu，缺陷：线程阻塞，响应时间慢

    偏向锁默认激活；关闭延迟 -XX:BiasedLockingStartupDelay=0 , 关闭偏向锁: -XX:-UseBiasedLocking=false

    处理器实现原子操作：1.使用总线锁 2.使用缓存锁

    CAS的问题：
    1. ABA问题 -> 解决方法: 使用版本号 － AtomicStampReference
    2. 循环时间长开销大
    3. 只能保证一个共享变量的锁定／解锁 -> 解决方法：AtomicReference

    线程之间通信的方法： 共享内存，消息传递，同步容器

    JAVA语言规范鼓励但不强求JVM对64位的long型变量和double型变量的写操作具有原子性

    Volatile变量有两个特性：保证可见性（读总是能看到对这个volatile变量对最后一次写）；保证原子性（只针对读写，像＋＋符合操作不具有原子性）；（还有重排序的问题）

    Volatile可以防止重排序，具体表现 （storestore storeLoad LoadLoad LoadStore）：
    1. 写volatile语句的上一条语句不能重排过界
    2. 读volatile语句的下一条语句不能重排过界
    3. 第一条语句是volatile写，第二条语句是volatile读，不能重排序

    写final域的重排序规则禁止把final域的写重排序到构造函数之外

    ----------------

    Java线程里，优先级的范围从1～10（最高），但线程优先级不能作为程序正确性的依赖，因为OS可以完全不理会JAVA线程设定的优先级

    注意线程的interrupt操作，
    － 如果有抛出InterruptedException异常，抛出之前，IsInterrupted标志位会被清除，isInterrupted（）返回false
    － 如果没有抛出异常（比如做循环），则isInterrupted会返回true


    使用 javap -v xx.class 来查看实现细节


    notify() 或 notifyAll() 方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或notifyAll()的线程释放锁之后，等待线程才有机会从wait（）返回


    Object的监视器方法（object.wait, object.notify...）与Condition接口的不同：
    - condition支持 awaitUninterruptedly
    - awaitUntil

    ReentrantLock的实现：
    － CAS操作state
    － 同步队列(AbstractQueuedSynchronizer) － 实现锁竞争
    － 等待队列 - 实现condition

    AQS（AbstractQueuedSynchronizer）是一个同步框架，它提供通用机制来原子性管理同步状态、阻塞和唤醒线程，以及维护被阻塞线程的队列，基于AQS实现的同步器包括：
    ReentrantLock, Semaphore, ReentrantReadWriteLock, CountDownLatch, FutureTaks

    － HashMap在并发执行put操作时会引起死循环，(要么使用synchronized修饰，或者直接使用同步队列)

    Unsafe只提供了3种CAS方法：compareAndSwapObject，compareAndSwapInt 和 compareAndSwapLong， 看AtomicBoolean源码的实现，发现它是把Boolean转换成整形，
    再使用compareAndSwapInt进行CAS， 所以原子更新char float 和 double变量也可以用蕾丝的思路实现


    线程池的处理流程
    1. 如果当前运行的线程少于corePoolSize，则创建新线程来执行任务（注意，执行这一步需要获取全局锁）
    2. 如果运行的线程等于或多余corePoolSize， 则将任务加入BlockingQueue
    3. 如果无法将任务加入BlockingQueue（队列已满），则创建新的线程来处理任务（注意，执行这一步需要获取全局锁）
    4. 如果创建新线程将使当前总线程数超出maximumPoolSize，任务将被拒绝，并调用RejectedExecutionHandler.rejectedExecution()方法。自带四种策略：抛出异常，丢弃，丢弃最旧，返回给caller线程执行

    线程池使用原则：
    － CPU密集型任务应配置尽可能小的线程池，比如 N(cpu)＋1 个线程的线程池
    － IO密集型任务由于并不是一直在执行任务，则应配置尽可能多的线程，如2*N（cpu）－ 另一说，比如监听一个端口使用一个线程就好了
    － 混合型任务，可以尝试拆分

     */
}
