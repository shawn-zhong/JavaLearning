package com.tseong.learning.basic.container.nonconcurrent;

import java.util.*;

public class _1_ArrayListDemo {

    public static void main(String[] args) {

        _1_ArrayListDemo instance = new _1_ArrayListDemo();
        instance.iteratorDemo();
        instance.setAndGetDemo();
        instance.removeAndSizeDemo();
        instance.subListDemo();
        instance.copyToArrayDemo();
        instance.fillAndReplaceDemo();
        instance.swapReverseSortSearch();
    }

    public void iteratorDemo() {

        System.out.println("Executing iteratorDemo");

        List<String> list = new ArrayList<>(3);
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");

        System.out.println(list);

        // 1 iterator
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("iterator: " + element);
        }

        // 2 for each
        for (String element : list) {
            System.out.println("foreach: " + element);
        }
    }

    public void setAndGetDemo() {

        System.out.println("Executing iteratorDemo");

        ArrayList<String> list = new ArrayList<>(3);
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");

        System.out.println("before set: " + list);

        list.set(0, "first");   // change the element at index 0

        System.out.println("after set: " + list);

        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public void removeAndSizeDemo() {

        List<String> list = new ArrayList<>(4);
        System.out.println("Initial size of list is : " + list.size());
        list.add("C");
        list.add("A");
        list.add("E");
        list.add("B");
        list.add(1, "A2");

        System.out.println("Size after addition: " + list.size());
        System.out.println("Contents of list: " + list);    // Contents of list: [C, A2, A, E, B]

        list.remove("F");   // 会返回false但不会抛出异常
        list.remove(2);  // Contents of list: [C, A2, E, B]

        System.out.println("Size after deletions: " + list.size());
        System.out.println("Contents of list: " + list);

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println("Iterator : " + iterator.next());
            iterator.remove(); // Notes:remove() must be called after next()
        }

        System.out.println("Size after iteration deletions: " + list.size());   // 0
        System.out.println("Contents of list: " + list);    // Contents of list: []

    }

    public void subListDemo() {
        ArrayList<String> list = new ArrayList<String>(5);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        List<String> subList = list.subList(1, 3);
        System.out.println("sublist content : " + subList); // sublist content : [2, 3]


        String obj = subList.remove(0); // 会对原来的list有影响
        System.out.println("removed object : " + obj);

        System.out.println("list content after removal: " + list);
        System.out.println("sublist content after removal: " + subList);

    }

    public void copyToArrayDemo() {
        ArrayList<String> list = new ArrayList<>(4);
        list.add("1A");
        list.add("2B");
        list.add("3C");
        list.add("4C");

        System.out.println("list : " + list);

        Object[] objArray = list.toArray();
        for (Object obj : objArray) {
            System.out.println("foreach of Object[] : " + obj);
        }

        objArray[0] = "1AA";
        System.out.println("original list after Object[] change : " + list);    // 原来的list不会受影响

        // get object array with generic method
        String[] strArray = list.toArray(new String[0]);    // 填为String[0]就好，会自动处理
        for (String str : strArray) {
            System.out.println(str);
        }

        list.set(0, "1AA");         // strArray不会受影响，说明是新分配的内存
        System.out.println(list);
        System.out.println("toArrayList : ");
        for (String str : strArray) {
            System.out.println(str);
        }
        // 结论：由list转换为Array，Array使用的是新的内存，不会受影响

        String[] arrStr = {"a1", "a2", "a3"};
        List<String> arrLst = Arrays.asList(arrStr);
        arrLst.set(0, "A1");
        System.out.println("asList : ");
        for (String str : arrStr) {
            System.out.println(str);
        }
        // 结论：由Arrays将Array转变为List时，并没有分配新内存，原来的数据会受印象

    }

    public void fillAndReplaceDemo() {
        ArrayList<String> arrayList = new ArrayList<>(5);
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        arrayList.add("A");

        System.out.println("orginal arraylist : " + arrayList);

        Collections.replaceAll(arrayList, "A", "1");            // 把arrayList的所有A换成1

        System.out.println("orginal arraylist after replacement: " + arrayList);

        Collections.fill(arrayList, "newElement");

        System.out.println("orginal arraylist after filling: " + arrayList);    // 把arrayList的所有元素替换成newElement

    }

    public void swapReverseSortSearch() {
        List<String> colors = new ArrayList<>();
        colors.add("read");
        colors.add("green");
        colors.add("blue");

        System.out.println("before swap : " + colors);  // [read, green, blue]
        System.out.println(colors);
        Collections.swap(colors, 0, 2 );          // 将元素0和元素2互换
        System.out.println("after swap : " + colors);   // [blue, green, read]

        Collections.reverse(colors);
        System.out.println("after reverse : " + colors);  // 将元素反转 [read, green, blue]

        Collections.sort(colors);
        System.out.println("after sort :" + colors);       // 排序 [blue, green, read]

        Collections.sort(colors, Collections.reverseOrder());
        System.out.println("after sort by reverseOrder :" + colors);

        int index = Collections.binarySearch(colors, "green");
        System.out.println("Element found at : " + index);

        System.out.println("min : " + Collections.min(colors));
        System.out.println("max : " + Collections.max(colors));
    }

    public void SynchronizedArrayList() {
        ArrayList arrayList = new ArrayList();
        List list = Collections.synchronizedList(arrayList);

        // list之后的并发操作将不再需要synchronized关键字来进行同步了
    }
}



/*

 ArrayList：该集合的底层是通过动态数组来实现的，集合构造的时候可以指定一个初始容量，当插入的元素过多导致已有的容量不能容纳新元素是，
 其底 层数组的容量将自动增长原有容量的1.5 倍，这样会带来一定的空间浪费，但是为了避免经常扩张而带来的性能开销，只能是用空间换取时间了。
 如果在容器的中间添加或者删除一个元素都将会导致后面的 元素向后或向前移动一个位置，如果元素数量较多且该操作比较频繁，将会导致系统的性能降低，
 然而对于容器中元素的随机访问性能较好，以下为 ArrayList的常用示例代码

 */

/*
1.    Java集合类库中最重要的两个接口Collection<E>和Map<K,V>，其中Collection接口又再次划分为 List和Set两大子接口，List中可以包含重复的元素，Set中则不可以。以下列举出一些常用的集合实现类，他们均分别继承自这两个接口：
        1)    ArrayList: 一种可以动态增长和缩减的索引序列(动态数组，类似于C++中的vector)；
        2)    LinkedList: 一种可以在任何位置进行高效的插入和删除操作的有序序列(类似于C++中list)；
        3)    ArrayDeque: 一种用循环数组实现的双端队列(类似于C++中的deque)；
        4)    HastSet：一种没有重复元素的无序集合(C++的标准库中并未提供hashset集合，但是Windows的VC和Linux平台下的gcc均各自提供了hashset容器)；
        5)    TreeSet: 一种有序集(类似于C++中的set)；
        6)    EnumSet: 一种包含枚举类型值的集；
        7)    LinkedHashSet: 一种可以记住元素插入次序的集，在其内部由LinkedList负责维护插入的次序，HashSet来维护Hash；
        8)    HashMap：一种存储键值对关联的数据结构(C++的标准库中并未提供hashmap集合，但是Windows的VC和Linux平台下的gcc均各自提供了hashmap容器)；
        9)    TreeMap：一种键值有序排列的映射表(类似于C++中的map)；
        10)   EnumMap：一种键值属于枚举类型的映射表；
        11)   LinkedHashMap：一种可以记住键值项插入次序的映射表；
*/


// http://www.cnblogs.com/stephen-liu74/archive/2011/08/26/2155082.html