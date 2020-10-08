package com.tseong.learning.basic.thread.threadlocal;

/**
 * @author: Shawn ZHONG @date: 2020-10-08 09:37
 */
public class _01_ThreadLocalDemo {

    public static void main(String[] args) {

        ValueContainer valueContainer = new ValueContainer();

        LocalThread thread1 = new LocalThread(valueContainer, "thread-1");
        LocalThread thread2 = new LocalThread(valueContainer, "thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class LocalThread extends Thread{

    ValueContainer valueContainer;
    String threadName;

    public LocalThread(ValueContainer valueContainer, String name) {
        this.valueContainer = valueContainer;
        this.threadName = name;
    }

    @Override
    public void run() {
        valueContainer.localVal.set("i am thread " + threadName);

        while(true) {
            System.out.println(threadName + ": " + valueContainer.localVal.get());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ValueContainer {


    // 用static沒有關係，因為Thread类已经包含了非静态的一个map，即使 ThreadLocal 的this相同，但不同thread的map里面的内容不相同
    public static  ThreadLocal<String> localVal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return  "local init value";
        }
    };

}


/**

 使用ThreadLocal没有remove。
 导致问题：1内存溢出，ThreadLocal依赖没有释放，无法GC。
 2线程池的某个线程会被反复使用,ThreadLocal的生命周期不等于一次

 */