package com.tseong.learning.basic.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = new MyCallable();

        // 这里的FutureTask<V>类分别实现了Runnable和Future<V>接口
        // 他将后续的操作中分别扮演不同的角色
        FutureTask<Integer> sleepTask = new FutureTask<>(callable);

        // 此时的FutureTask扮演着Runnable的角色
        Thread t = new Thread(sleepTask);
        t.start();

        // 此时的FutureTask扮演着Future<V>的角色

        // 该方法将阻塞直到线程执行完毕正常返回，如果是异常中断, 则被异常捕捉逻辑获取
        Integer ret = sleepTask.get(); // 可以设置超时
        System.out.println("The return value of FutureTask is " + ret);

        // 关于callable的其它用法可以看线程池部分
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws InterruptedException {
            Thread.sleep(1000*2);
            return 101;
        }
    }
}
