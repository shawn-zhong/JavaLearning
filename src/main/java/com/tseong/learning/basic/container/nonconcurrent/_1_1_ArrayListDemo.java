package com.tseong.learning.basic.container.nonconcurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class _1_1_ArrayListDemo {

    public static void main(String[] args) {

        _1_1_ArrayListDemo app = new _1_1_ArrayListDemo();
        app.bootstrap();
    }

    public void bootstrap(){

        // Exception in thread "Thread-1" java.util.ConcurrentModificationException
        /*
        List<String> lst = new ArrayList<>(100);
        Collections.fill(lst, "A");

        new Thread(new ProducerThread(lst)).start();
        new Thread(new IteratorThread(lst)).start();*/

        // Exception in thread "Thread-1" java.util.ConcurrentModificationException
        List<String> lst = new ArrayList<>(100);
        lst.add("DD");
        lst.add("EE");
        Collections.fill(lst, "A");
        List<String> syncLst = Collections.synchronizedList(lst);
        new Thread(new ProducerThread(lst)).start();
        new Thread(new IteratorThread(lst)).start();

        // 结论：无论是否是synchronized，都不能在遍历过程中添加元素，否则会跑出ConcurrentModificationException
    }


    class IteratorThread implements Runnable {

        public IteratorThread(List<String> list) {
            this.lst = list;
        }

        List<String> lst;

        @Override
        public void run() {
           Iterator<String> iterator = lst.iterator();
           while(iterator.hasNext()) {
               String element = iterator.next();
               System.out.println(element);
               try {
                   Thread.sleep(10);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    class ProducerThread implements Runnable {

        public ProducerThread(List<String> list) {
            this.lst = list;
        }

        List<String> lst;

        @Override
        public void run() {

            for (int i=0; i<100; i++) {
                lst.add("B");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
