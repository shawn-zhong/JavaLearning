package com.tseong.learning.basic.thread.lockSupport;

public class _1_object_wait {

    public static void main(String[] args) throws Exception {
        final Object obj = new Object();

        Thread threadA = new Thread(()->{

            // some work here
            int sum = 0;
            for (int i=0; i<10; i++) {
                sum += i;
            }

            try {

                //Thread.sleep(2000); // 加了这句将永远等不到notify，因为notify在wait之前调用了

                synchronized (obj) {
                    obj.wait();         // wait和notify方法都必须写在同步代码段内，而且wait要在notify之前调用才有用
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("sum = " + sum);
        });

        threadA.start();

        // 睡眠1s，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        synchronized (obj) {
            obj.notify();
        }
    }
}
