package com.tseong.learning.basic.thread.lockSupport;

public class _0_readme {


    /**
     *
     *
     LockSupport比Object的wait/notify有两大优势：
     ① LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
     ② unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。

     补充：LockSupport对于interrupt的响应，只是返回，不会抛异常
     *
     */
}
