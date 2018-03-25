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


    事务的四种隔离级别
    1. Read Uncommitted
    2. Read Committed
    3. Repeatable Read （事务B提交了，只有当事务A也提交了，A才能看到更改的内容，但还是有幻读的问题－A感知不到新插入的数据）
    4. Serializable


    MyBatis:
    一级缓存：作用域：同一个SqlSession中执行相同的SQL语句
    二级缓存：作用域：多个sqlSession共享

    MyBatis延迟加载（默认关）：先查主表需要的信息，里面包含的比如Association，需要用到数据再进行DB查询

     */

}
