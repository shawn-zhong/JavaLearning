package com.tseong.learning.text;

public class _05_MySQL {

    /*

    查看MySQL的执行速度
    mySql > show variables like '%pro%';
    mySql > set profiling = 1;
    mySql > select * from xx;
    mySql > show profiles;

    查看执行计划:
    mySql > explain select * from ...

    查看有哪些线程在运行，不仅可以查看当前所有的连接数，还可以查看当前的连接状态帮助识别出有问题的查询语句等
    mysql > show full processlist


    数据库连接池到底应该设置多大:
    下面公式由PostgreSQL提供： 连接数 ＝ （核心数 * 2）+ 有效磁盘数

    常用数据库连接池: C3P0 或 druid

    事务并发带来的问题：
    脏读： 一个事务读取了另一个事务未提交的数据
    不可重复读： 重点是修改。同样条件下两次读取结果不同。也就是说，被读取的数据可以被其它事务修改
    幻读：重点在于新增或删除。同样条件下两次读出来的记录数不一样

    事务的四种隔离级别
    1. Read Uncommitted
    2. Read Committed
    3. Repeatable Read （事务B提交了，只有当事务A也提交了，A才能看到更改的内容，但还是有幻读的问题－A感知不到新插入的数据）
    4. Serializable


    MyBatis:
    一级缓存：作用域：同一个SqlSession中执行相同的SQL语句
    二级缓存：作用域：多个sqlSession共享

    MyBatis延迟加载（默认关）：先查主表需要的信息，里面包含的比如Association，需要用到数据再进行DB查询


    － 存储过程和函数：
    存储过程和函数的区别在于函数必须有返回值，而存储过程没有。存储过程的参数可以使用IN，OUT，INOUT类型，而函数的参数只能是IN类型

    MySQL的优化主要涉及SQL语句及索引的优化，数据表结构的优化，系统配置的优化，硬件的优化
    SQL语句的优化：
    1）优化Insert语句，一次插入多值
    2）应尽量避免在where子句中使用!=或<>操作符，否则引擎将放弃使用索引而进行全表扫描
    3) 应尽量避免在where子句中对字段进行null值判断。否则引擎将放弃使用索引而进行全表扫描
    4）优化嵌套查询，子查询可以被更有效率的连接（join）替代
    5）很多时候用exists代替是一个好的选择

    其它注意：
    1）使用较小的数据类型解决问题
    2）使用简单的数据类型（Mysql处理int比varchar容易）
    3）尽可能的使用not null定义字段
    4）尽量避免使用text类型。非用不可时最好考虑分表

    唯一索引是什么？
    索引列的值必须唯一，但允许由空值。主键是一种唯一索引，但主键不允许有空值，所以不能说唯一索引就是主键。



    Innodb 的锁机制：
    source: https://www.cnblogs.com/janehoo/p/5603983.html

    Innodb 的锁分两类：lock和latch
        - latch主要是保证并发线程操作临界资源的正确性，要求时间非常短，所以没有死锁检测机制。latch包括mutex（互斥量）和rwlock（读写锁）
        - lock是面向事务，操作（表、页（Innodb没有页锁）、行）等对象，用来管理共享资源的并发访问，是有死锁检测机制的

    行锁：innodb实现了多粒度锁：表锁，行锁，


     */

}
