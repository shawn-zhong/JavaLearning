package com.tseong.learning.basic.container.nonconcurrent;

import java.util.*;

public class _3_HashsetDemo {

    public static void main(String[] args) {

        _3_HashsetDemo instance = new _3_HashsetDemo();
        instance.addAndIteratorDemo();
        instance.removeAndClearAndContainsDemo();
        instance.toArrayListDemo();
        instance.listToSetDemo();
        instance.minMaxDemo();
    }

    public void addAndIteratorDemo() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("B");
        hashSet.add("A");
        hashSet.add("D");
        hashSet.add("E");
        hashSet.add("C");
        hashSet.add("F");
        hashSet.add("ABC");    // ***
        hashSet.add("ABcC");
        hashSet.add("F");

        System.out.println(hashSet);    // set是无序的，当你添加元素进去时，是经过3L所说的hash算法在集合内部进行排序保存的
        Iterator<String> iterator = hashSet.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());    // 打印结果： [A, B, ABC, C, D, E, F, ABcC]
        }
    }

    public void removeAndClearAndContainsDemo() {
        HashSet<Integer> hashset = new HashSet<>();
        hashset.add(new Integer("1"));
        hashset.add(new Integer("2"));
        hashset.add(new Integer("3"));

        System.out.println(hashset.contains(3));    // true
        System.out.println(hashset);                // [1, 2, 3]

        boolean isRemoved = hashset.remove(2);
        System.out.println(hashset.contains(2));    // false
        System.out.println(isRemoved);
        hashset.clear();
        System.out.println(hashset);                // []
        System.out.println(hashset.isEmpty());      // true

    }

    public void toArrayListDemo() {
        HashSet<String> hashset = new HashSet();
        hashset.add("B");
        hashset.add("A");
        hashset.add("C");

        Object[] objects = hashset.toArray();
        String[] strings = hashset.toArray(new String[0]);
        List<String> strList = new ArrayList<>(hashset);

        for (int i=0; i<2; i++) {
            for (Object obj : objects) {
                System.out.println("Object = " + obj);
            }

            for (String str : strings) {
                System.out.println("String Object : " + str);
            }

            for (String strlst : strList) {
                System.out.println("ArrayList : " + strlst);
            }

            hashset.add("D");   // 往hastset添加元素，上面三种数组的打印不会受影响，说明是另外新建出来的
        }

    }

    public void listToSetDemo() {
        Integer[] numbers = {7, 7, 8, 9, 10, 2, 3};

        List<Integer> list = Arrays.asList(numbers);    // 没有另外新建内存，修改numbers时list会受影响
        Set<Integer> set = new HashSet<>(list);         // 新建内存，修改numbers时set不会受影响
        System.out.println("list : " + list);           // list : [7, 7, 8, 9, 10, 2, 3]
        System.out.println("set : " + set);             // set : [2, 3, 7, 8, 9, 10]

        numbers[0] = 12;
        System.out.println("list : " + list);           // list : [12, 7, 8, 9, 10, 2, 3]
        System.out.println("set : " + set);             // set : [2, 3, 7, 8, 9, 10]
    }

    public void minMaxDemo() {
        HashSet<Long> hashSet = new HashSet<Long>();
        hashSet.add(new Long("9"));
        hashSet.add(new Long("4"));
        hashSet.add(new Long("2"));
        hashSet.add(new Long("2"));
        hashSet.add(new Long("3"));
        System.out.println(hashSet);
        Object objMin = Collections.min(hashSet);
        System.out.println("Minimum Element of Java HashSet is : " + objMin);
        Object objMax = Collections.max(hashSet);
        System.out.println("Maximum Element of Java Hashset is : " + objMax);
    }

    public void synchronizedSet() {
        HashSet hashset = new HashSet();
        Set set = Collections.synchronizedSet(hashset);
        //set之后的并发操作将不再需要synchronized关键字进行同步了
    }
}



/*
HashSet:    散列表为每个对象计算出一个整数(通过Object缺省的或类重载的hashCode域方法)，称为散列码。在Java中，散列表是使用链表的数组来实现 的，
        每个链表被称为一个哈希桶。如果想在哈希表中查找一个对象，则需要先计算对象的hashCode，之后对表内的桶数取余数，得到的结果就是哈希桶的索 引，
        此时如果该桶为空桶，就可以直接将该对象插入到该桶中，如果已经有对象存在了，则需要逐个进行比较，一旦发现该与该对象相等的对象(equals() 返回true)，
        就会放弃本次插入操作，否则将该对象插入到该桶的末端。HashSet在构造的时候提供两个参数，一个是initialCapacity
        指定了桶的数量(实际的桶数为2的initialCapacity次幂)，另一个是loadFactortian(0.0--1.0,推荐值为 0.75)，当桶的填充百分比达到该值后，
                        因可能在构造HashSet的时候给出一个合理的参数。以下为HashSet的常用示例代码：
*/

/*
HashSet:    此类实现 Set 接口，由哈希表（实际上是一个 HashMap 实例）支持。它不保证集合的迭代顺序；特别是它不保证该顺序恒久不变。此类允许使用 null 元素。
        此类为基本操作提供了稳定性能，这些基本操作包括 add、remove、contains 和 size，假定哈希函数将这些元素正确地分布在桶中。对此集合进行迭代所需的时间与
        HashSet 实例的大小（元素的数量）和底层 HashMap 实例（桶的数量）的“容量”的和成比例。因此，如果迭代性能很重要，则不要将初始容量设置得太高（或将加载因子设置得太低）。
*/