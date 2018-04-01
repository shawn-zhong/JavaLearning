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

     */
}
