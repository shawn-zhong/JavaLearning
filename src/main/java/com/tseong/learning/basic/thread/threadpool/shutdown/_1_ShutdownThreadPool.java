package com.tseong.learning.basic.thread.threadpool.shutdown;

import com.tseong.learning.basic.thread.threadpool.ExecutorService._1_FixedThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/z69183787/article/details/105770619
 * http://blog.sina.com.cn/s/blog_7d1968e20102x1x4.html
 *
 * @author: Shawn ZHONG @date: 2020-10-08 06:28
 */
public class _1_ShutdownThreadPool {

    ExecutorService executorService = Executors.newFixedThreadPool(5);


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        _1_ShutdownThreadPool instance = new _1_ShutdownThreadPool();
        instance.executeDemo();



    }

    void executeDemo() throws InterruptedException {

        // 调用execute方法没有返回值 (Runnable)
        for (int i=0; i<100; i++) {
            final int count = i;
            executorService.execute(() -> {
                System.out.println("thread name: " + Thread.currentThread().getName() + " count:" + count);
            });

            Thread.sleep(1000);
        }

        //executorService.shutdown();

        Thread shutdownThread = new Thread() {
            @Override
            public void run() {
                System.out.println("received shutdown signal !!!");
                executorService.shutdown();

                try {
                    // http://blog.sina.com.cn/s/blog_7d1968e20102x1x4.html
                    if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

}


/**
 * 使用spring的线程池，可以设置是否优雅退出，并设置超时时间。没有设置时，相当于shutdownnow，有设置时，相当于shutdown
*
* */