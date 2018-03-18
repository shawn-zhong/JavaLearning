package com.tseong.learning.basic.container.nonconcurrent;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class _2_LinkedListDemo {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        LinkedList<String> linkedList = (LinkedList)list;
        System.out.println("First element of LinkedList is : " + linkedList.getFirst());
        System.out.println("Last element of LinkedList is : " + linkedList.getLast());
        System.out.println(linkedList);

        String removedStr = linkedList.removeFirst();
        System.out.println(removedStr + " has been removed");
        System.out.println(linkedList);

        removedStr = linkedList.removeLast();
        System.out.println(removedStr + " has been removed");
        System.out.println(linkedList);

        linkedList.addFirst("1");
        linkedList.addLast("5");
        System.out.println(linkedList);

        System.out.println();
        
        Collections.sort(linkedList, Collections.reverseOrder());
        System.out.println(linkedList);
    }
}


/*
*
* ArrayList和LinkedList的相同点：
    1)    都是接口List<E>的实现类；
    2)    都可以通过iterator()方法返回Iterator<E>迭代器对象，并可以通过该对象遍历容器中的元素；
    3)    如果多个Iterator<E>实例同时引用同一个集合对象，那么当有一个正在遍历，而另外一个修改(add/remove)了集合对象中的 元素，对于第一个迭代器再进行迭代时将会引发ConcurrentModificationException异常的发生。
  ArrayList和LinkedList的不同点：
    1)    对ArrayList和LinkedList而言，在列表末尾增加一个元素所花的开销都是固定的。对ArrayList而言，主要是在内部数组中增加一 项，指向所添加的元素，偶尔可能会导致对数组重新进行分配；而对LinkedList而言，这个开销是统一的，分配一个内部Entry对象。
    2)    在ArrayList的中间插入或删除一个元素意味着这个列表中剩余的元素都会被移动；而在LinkedList的中间插入或删除一个元素的开销是固定的。
    3)    LinkedList不支持高效的随机元素访问。
    4)    ArrayList的空间浪费主要体现在在list列表的结尾预留一定的容量空间，而LinkedList的空间花费则体现在它的每一个元素都需要消耗相当的空间
    在C++的标准库中，ArrayList和LinkedList之间的使用差异以及各自的优缺点也同样反映于vector和list。
*
* */
