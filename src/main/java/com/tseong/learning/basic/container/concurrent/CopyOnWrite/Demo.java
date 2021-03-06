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


/*

和单词描述的一样，他的实现就是写时复制， 在往集合中添加数据的时候，先拷贝存储的数组，然后添加元素到拷贝好的数组中，然后用现在的数组去替换成员变量的数组（就是get等读取操作读取的数组）。这个机制和读写锁是一样的，
但是比读写锁有改进的地方，那就是读取的时候可以写入的 ，这样省去了读写之间的竞争，看了这个过程，你也发现了问题，同时写入的时候怎么办呢，当然果断还是加锁。

copyonwrite的机制虽然是线程安全的，但是在add操作的时候不停的拷贝是一件很费时的操作，所以使用到这个集合的时候尽量不要出现频繁的添加操作，而且在迭代的时候数据也是不及时的，数据量少还好说，数据太多的时候，
实时性可能就差距很大了。在多读取，少添加的时候，他的效果还是不错的（数据量大无所谓，只要你不添加，他都是好用的）。

 */