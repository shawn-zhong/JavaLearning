package com.tseong.learning.advance._07_unsafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo2 {

    public static void main(String[] args) throws InterruptedException {


        ThreadLockSupport threadA = new ThreadLockSupport();
        threadA.start();
        Thread.sleep(2000);
        threadA.interrupt();    // -> Locksupport会返回，但不会抛出异常

        threadA.join();
        Thread.sleep(1000);
        System.out.println();

        ThreadCondition threadB = new ThreadCondition();
        threadB.start();
        threadB.interrupt();    // －> Object.wait会抛出异常
        threadB.interrupt();

    }

    public static class ThreadLockSupport extends Thread  {

        @Override
        public void run() {
            int count = 0;

            long start = System.currentTimeMillis();
            long end = 0;

            System.out.println("ThreadLockSupport: before 10 second count = " + count);


            while ((end - start) <= 10000) {
                count++;
                end = System.currentTimeMillis();
            }

            System.out.println("ThreadLockSupport: after 10 second count = " + count);

            LockSupport.park(); // 和object.wait相比：1.不需要锁，2.碰到interrupt时不会抛异常, 只会返回 3.wait搭配锁使用并会释放锁

            end = System.currentTimeMillis();
            System.out.println("ThreadLockSupport: over here, costs : " + (end-start)/1000);
        };


    }

    public static class ThreadCondition extends Thread {

        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        @Override
        public void run() {
            int count = 0;

            long start = System.currentTimeMillis();
            long end = 0;

            System.out.println("ThreadCondition: Thread-B: before 10 second count = " + count);

            while ((end - start) <= 10000) {
                count++;
                end = System.currentTimeMillis();
            }

            System.out.println("ThreadCondition: after 10 second count = " + count);

            lock.lock();

            try {
                condition.await();  //
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            end = System.currentTimeMillis();
            System.out.println("ThreadCondition: over here, costs : " + (end-start)/1000);
        }
    }
}


/*

/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/bin/java -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/lib/tools.jar:/Users/Shawn/Documents/workspace/JavaLearning/target/classes:/Users/Shawn/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/Shawn/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/Shawn/.m2/repository/org/apache/thrift/libthrift/0.11.0/libthrift-0.11.0.jar:/Users/Shawn/.m2/repository/org/apache/httpcomponents/httpclient/4.4.1/httpclient-4.4.1.jar:/Users/Shawn/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/Users/Shawn/.m2/repository/commons-codec/commons-codec/1.9/commons-codec-1.9.jar:/Users/Shawn/.m2/repository/org/apache/httpcomponents/httpcore/4.4.1/httpcore-4.4.1.jar:/Users/Shawn/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/Shawn/.m2/repository/org/springframework/spring-context/5.0.4.RELEASE/spring-context-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/org/springframework/spring-aop/5.0.4.RELEASE/spring-aop-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/org/springframework/spring-beans/5.0.4.RELEASE/spring-beans-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/org/springframework/spring-core/5.0.4.RELEASE/spring-core-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/org/springframework/spring-jcl/5.0.4.RELEASE/spring-jcl-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/org/springframework/spring-expression/5.0.4.RELEASE/spring-expression-5.0.4.RELEASE.jar:/Users/Shawn/.m2/repository/io/nettyDemos/nettyDemos-all/4.1.21.Final/nettyDemos-all-4.1.21.Final.jar com.tseong.learning.advance._07_unsafe.LockSupportDemo2
ThreadLockSupport: before 10 second count = 0
ThreadLockSupport: after 10 second count = 146225027
ThreadLockSupport: over here, costs : 10

ThreadCondition: Thread-B: before 10 second count = 0
ThreadCondition: after 10 second count = 159336061
java.lang.InterruptedException
ThreadCondition: over here, costs : 10
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2034)
	at com.tseong.learning.advance._07_unsafe.LockSupportDemo2$ThreadCondition.run(LockSupportDemo2.java:80)

Process finished with exit code 0


 */