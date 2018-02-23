package com.tseong.learning.container.nonconcurrent;

import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class _4_TreeSet {

    public static void main(String[] args) {

        TreeSet<MyItem> parts = new TreeSet<>();
        parts.add(new MyItem("Toster", 2));
        parts.add(new MyItem("Widget", 21));
        parts.add(new MyItem("Modem", 15));

        System.out.println("default treeset: " + parts);
        System.out.println("descending treeset: " + parts.descendingSet());

         SortedSet<MyItem> sortedSet = new TreeSet<>(Comparator.comparing(MyItem::getDescription)); // 和下面注释的一样
       // SortedSet<MyItem> sortedSet = new TreeSet<MyItem>((a,b)->{
       //     return a.getDescription().compareTo(b.getDescription());
       // }); // 可以在新建treeset时指定compare方法，这样item就不用实现Comparable方法了
        sortedSet.addAll(parts);                        // 新建内存存放
        System.out.println("by name:" + sortedSet);

        parts.add(new MyItem("Web", 4));       // 此处添加不会影响sortedSet
        System.out.println("by name, added:" + sortedSet);

    }

    static class MyItem implements Comparable<MyItem> {

        String partString;
        int partNumber;

        public MyItem(String str, int val) {
            partString = str;
            partNumber = val;
        }

        @Override
        public int compareTo(MyItem o) {
            return partNumber - o.partNumber;
        }

        public String getDescription() {
            return partString;
        }


        @Override
        public String toString() {
            return String.format("%s-%d", partString, partNumber);
        }
    }

}


/*


/*
*  TreeSet：该集合为有序集合，数据在插入集合后便按照自定义的排序规则将插入的对象进行排序，该集合主要是通过两种方式排序插入对象的，
*  一种是要求 集合元素类必须是Comparable<T>的实现类，这样在插入对象的时候，集合可以根据compareTo方法的返回值来确定新插入对象 的位置，
*  另外一种方式是通过在构造该集合对象的时候，传入Comparator<T>的实现类作为参数，之后所有插入的对象都是通过 Comparator<T>的compare方法
*  的返回值来决定新对象的位置。该集合是通过RBTree(红黑树)来实现排序的，这一点和 C++标准库中的set和map是一致的。由于对象插入集合之后是有序的，
*  因此该集合的插入效率要低于HashSet的插入效率。TreeSet和 HashSet相比主要的差异来自于对象的排序规则，以上HashSet的示例代码均适用于TreeSet，
*  下面只是列出对象比较的代码：
*


//HashSet是基于Hash算法实现的,其性能通常优于TreeSet,我们通常都应该使用HashSet,在我们需要排序的功能时,我门才使用TreeSet;

*/