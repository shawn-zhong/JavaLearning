package com.tseong.learning.basic.thread.lockSupport;

import java.util.concurrent.locks.LockSupport;

public class _2_locksupport {

    public static void main(String[] args) throws Exception {
        Thread threadA = new Thread(()->{
            int sum = 0;
            for (int i=0; i<10; i++) {
                sum += i;
            }

            try {
                Thread.sleep(2000); // 加了这句也没有关系，unpark可以在park之前调用
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LockSupport.park();
            System.out.println(sum);
        });

        threadA.start();

        // 睡眠1s，保证线程A已经完成计算并阻塞在wait方法
        Thread.sleep(1000);
        LockSupport.unpark(threadA);    // 注意要传入句柄
    }
}

// https://www.cnblogs.com/qingquanzi/p/8228422.html