package com.tseong.learning.thread.jvmTools.jstack;

import java.util.concurrent.TimeUnit;

public class ThreadState {

    public static void main(String ... args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();

        // 使用两个Blocked线程，一个获取锁成功， 另一个被阻塞
        new Thread(new Blocked(), "BLockedThread-1").start();
        new Thread(new Blocked(), "BLockedThread-2").start();
    }

    // 该线程不断的进行睡眠
    static class TimeWaiting implements  Runnable {

        @Override
        public void run() {
            while (true) {
               SleepUtils.second(100);
            }
        }
    }

    // 该线程在waiting.class实例上等待
    static class Waiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在Blocked.class加锁后，不会释放该锁
    static class Blocked implements Runnable {

        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }
}

class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {

        }
    }
}


/*

调试方法：
1. 控制台上运行jps得出进程id
2. 控制台上运行jstack 进程id

 */

/* 输出

~/AutoScripts Shawn >> jstack 4157
2018-03-03 17:05:22
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.92-b14 mixed mode):

"Attach Listener" #14 daemon prio=9 os_prio=31 tid=0x00007faf4a863800 nid=0x3507 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"DestroyJavaVM" #13 prio=5 os_prio=31 tid=0x00007faf4b055800 nid=0x1003 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"BLockedThread-2" #12 prio=5 os_prio=31 tid=0x00007faf4b054800 nid=0x5203 waiting for monitor entry [0x00007000013cf000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at com.tseong.learning.thread.lock.ThreadState$Blocked.run(ThreadState.java:53)
	- waiting to lock <0x00000007956e8568> (a java.lang.Class for com.tseong.learning.thread.lock.ThreadState$Blocked)
	at java.lang.Thread.run(Thread.java:745)

"BLockedThread-1" #11 prio=5 os_prio=31 tid=0x00007faf4b054000 nid=0x5003 waiting on condition [0x00007000012cc000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at com.tseong.learning.thread.lock.SleepUtils.second(ThreadState.java:63)
	at com.tseong.learning.thread.lock.ThreadState$Blocked.run(ThreadState.java:53)
	- locked <0x00000007956e8568> (a java.lang.Class for com.tseong.learning.thread.lock.ThreadState$Blocked)
	at java.lang.Thread.run(Thread.java:745)

"WaitingThread" #10 prio=5 os_prio=31 tid=0x00007faf4b053000 nid=0x4e03 in Object.wait() [0x00007000011c9000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000007956e3da8> (a java.lang.Class for com.tseong.learning.thread.lock.ThreadState$Waiting)
	at java.lang.Object.wait(Object.java:502)
	at com.tseong.learning.thread.lock.ThreadState$Waiting.run(ThreadState.java:37)
	- locked <0x00000007956e3da8> (a java.lang.Class for com.tseong.learning.thread.lock.ThreadState$Waiting)
	at java.lang.Thread.run(Thread.java:745)

"TimeWaitingThread" #9 prio=5 os_prio=31 tid=0x00007faf4b034800 nid=0x4c03 waiting on condition [0x00007000010c6000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at com.tseong.learning.thread.lock.SleepUtils.second(ThreadState.java:63)
	at com.tseong.learning.thread.lock.ThreadState$TimeWaiting.run(ThreadState.java:24)
	at java.lang.Thread.run(Thread.java:745)

"Service Thread" #8 daemon prio=9 os_prio=31 tid=0x00007faf4c034000 nid=0x4803 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread2" #7 daemon prio=9 os_prio=31 tid=0x00007faf4b02b800 nid=0x4603 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #6 daemon prio=9 os_prio=31 tid=0x00007faf4b009000 nid=0x4403 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #5 daemon prio=9 os_prio=31 tid=0x00007faf4b02a000 nid=0x4203 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 tid=0x00007faf4b042000 nid=0x320f runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007faf4b80f000 nid=0x3003 in Object.wait() [0x000070000092e000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000795588ee0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
	- locked <0x0000000795588ee0> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007faf4a83c800 nid=0x2e03 in Object.wait() [0x000070000082b000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x0000000795586b50> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x0000000795586b50> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=31 tid=0x00007faf4a837800 nid=0x2c03 runnable

"GC task thread#0 (ParallelGC)" os_prio=31 tid=0x00007faf4b00c800 nid=0x2403 runnable

"GC task thread#1 (ParallelGC)" os_prio=31 tid=0x00007faf4b00d800 nid=0x2603 runnable

"GC task thread#2 (ParallelGC)" os_prio=31 tid=0x00007faf4b00e000 nid=0x2803 runnable

"GC task thread#3 (ParallelGC)" os_prio=31 tid=0x00007faf4b00e800 nid=0x2a03 runnable

"VM Periodic Task Thread" os_prio=31 tid=0x00007faf4a80f000 nid=0x4a03 waiting on condition

JNI global references: 6

~/AutoScripts Shawn >>

 */