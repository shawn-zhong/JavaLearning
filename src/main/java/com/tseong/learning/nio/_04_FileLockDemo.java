package com.tseong.learning.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

public class _04_FileLockDemo {

    // 在java nio中提供了文件锁的功能，这样当一个线程将文件锁定之后，其它线程是无法操作此文件的
    // 要进行文件的锁定操作，则要使用FileLock类来完成，此类的对象需要依靠FileChannel进行实例化操作

    // 这个程序是将a.txt文件锁定15秒

    public static void main(String[] args) throws Exception {
        //String path = FileLock.class.getClassLoader().getResource("a.txt").getPath();
        String path = "/Users/Shawn/Documents/workspace/JavaLearning/target/newfile.txt";
        File file = new File(path);

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel channel = raf.getChannel();
        java.nio.channels.FileLock lock = channel.tryLock(0, Integer.MAX_VALUE, false);

        if (lock != null) {
            System.out.println("locking file for 30 secs ： " + Thread.currentThread().getId());

            // 锁定30秒
            TimeUnit.SECONDS.sleep(30);

            // 释放锁
            lock.release();
        }

        // close
        channel.close();
        raf.close();

        System.out.println("locking file done");
    }
}
