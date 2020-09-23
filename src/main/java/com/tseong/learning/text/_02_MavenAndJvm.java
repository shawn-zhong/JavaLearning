package com.tseong.learning.text;

public class _02_MavenAndJvm {

    /*

    Maven 常用命令：
    mvn clean   : 清理项目生成的临时文件，一般是模块下的target目录
    mvn compile : mvn compile后，maven就会自动帮我们下载依赖jar，为代码生成字节码文件等。我们把这个过程称为 编译。
    mvn test    : 测试命令
    mvn package : 项目打包工具，会在target目录生成jar或war等文件
    mvn install : 将打包的jar/war文件复制到本地仓库中，供其它模块使用
    mvn deploy  : 将打包发布到远程参考，供其它人员进行下载依赖（一般为公司私服）


    Maven scope :
    compile(默认)：表示为当前依赖参与项目的编译、测试和运行阶段，属于强依赖。打包之时，会达到包里去
    test ： 该依赖仅仅参与测试相关的内容，包括测试用例的编译和执行，比如定性的Junit
    runtime ： 依赖仅参与运行周期中的使用。一般这种类库都是接口与实现相分离的类库，比如JDBC类库，在编译之时仅依赖相关的接口，在具体的运行之时，才需要具体的mysql、oracle等等数据的驱动程序。
                此类的驱动都是为runtime的类库
    provided ： 该依赖在打包过程中，不需要打进去，这个由运行的环境来提供，比如tomcat或者基础类库等等，事实上，该依赖可以参与编译、测试和运行等周期，与compile等同。区别在于打包阶段进行了exclude操作
    system ： 使用上与provided相同，不同之处在于该依赖不从maven仓库中提取，而是从本地文件系统中提取，其会参照systemPath的属性进行提取依赖
    import ： 这个是maven2.0.9版本后出的属性，import只能在dependencyManagement的中使用，能解决maven单继承问题，import依赖关系实际上并不参与限制依赖关系的传递性


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


