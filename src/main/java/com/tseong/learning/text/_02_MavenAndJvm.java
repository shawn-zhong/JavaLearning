package com.tseong.learning.text;

public class _02_MavenAndJvm {

    /*

    Maven 常用命令：
    mvn clean   : 清理项目生成的临时文件，一般是模块下的target目录
    mvn compile : mvn compile后，maven就会自动帮我们下载依赖jar，为代码生成字节码文件等。我们把这个过程称为 编译。
    mvn package : 项目打包工具，会在target目录生成jar或war等文件
    mvn test    : 测试命令
    mvn install : 将打包的jar/war文件复制到本地仓库中，供其它模块使用
    mvn deploy  : 将打包发布到远程参考，供其它人员进行下载依赖（一般为公司私服）

    JVM 参数
    -Xmx3550    : 设置JVM最大内存
    -Xms3550    : 设置JVM最小内存
    -Xmn2g      : 设置年轻代内存
    -XX:MaxPermSize=16m
    -XX:NewRatio    :   年轻代与年老代的比值
    -Xss128k    : 设置每个线程的堆栈大小


    将文件checkout到本地目录
    1 svn checkout path（path是服务器上的目录）
    2 例如：svn checkout svn://192.168.1.1/pro/domain
    3 简写：svn co

    往版本库中添加新的文件
    1 svn add file
    2 例如：svn add test.php(添加test.php)
    3 svn add *.php(添加当前目录下所有的php文件)

    将改动的文件提交到版本库
    1 svn commit -m “LogMessage“ [-N] [--no-unlock] PATH　　　　　　　　(如果选择了保持锁，就使用–no-unlock开关)
    2 例如：svn commit -m “add test file for my test“ test.php
    3 简写：svn ci

    加锁/解锁
    1 svn lock -m “LockMessage“ [--force] PATH
    2 例如：svn lock -m “lock test file“ test.php
    3 svn unlock PATH

    https://www.cnblogs.com/luckythan/p/4478706.html


     */
}
