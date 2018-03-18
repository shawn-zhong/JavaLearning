package com.tseong.learning.basic.container.concurrent.CopyOnWrite;

import java.util.concurrent.CopyOnWriteArrayList;

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b", "c"});

        // 写1
        Thread thread = new Thread(()->{
            int count = 10;
            while(true) {
                list.add(count++ + "");
            }
        });
        thread.setDaemon(true);
        thread.start();

        // 写2
        Thread thread2 = new Thread(()->{
            int count = -10;
            while(true) {
                list.add(count-- + "");
            }
        });
        thread2.setDaemon(true);
        thread2.start();

        Thread.sleep(3);

        for (String s : list) {
            System.out.println(list.hashCode() + " " + s);
        }

        /*
        我们可以看到，在set方法中，我们首先是获得了当前数组的一个拷贝获得一个新的数组，然后在这个新的数组上完成我们想要的操作。
        当操作完成之后，再把原有数组的引用指向新的数组。并且在此过程中，我们只拥有一个事实不可变对象，即容器中的array。这样一来就很巧妙地体现了CopyOnWrite思想。
        */
    }
}
