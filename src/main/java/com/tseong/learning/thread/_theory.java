package com.tseong.learning.thread;

public class _theory {

    /*

    与程序员密切相关的happens-before规则如下:
    1. 程序顺序原则: 一个线程中的每个操作，happens-before与该线程中的任意后续操作
    2. 监视器锁原则: 对一个锁的解锁，happens-before于随后对这个锁的加锁
    3. volatile变量规则： 对一个volatile域的写，happens－before于任意后续对这个volatile域的读
    4. 传递性: 如果A happens-before B，且B happens-before C, 那么A happens-before C

    <JSR-133:Java Memory Model and Thread Specification> 定义了如下happens-before规则
    1. 程序顺序规则
    2. 监视器锁规则
    3. volatile变量规则
    4. 传递性
    5. start()规则：如果线程A执行操作ThreadB.start(), 那么A此案成的ThreadB.start()操作happens-before于线程B中的任意操作
    6. join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回
     */
}
