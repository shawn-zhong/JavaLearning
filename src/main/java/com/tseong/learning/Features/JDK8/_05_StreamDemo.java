package com.tseong.learning.Features.JDK8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _05_StreamDemo {

    public static void main(String[] args) {

        _05_StreamDemo instance = new _05_StreamDemo();

        instance.commonOperations01();
        instance.commonOperationsDemo();

        List<String> value = instance.createdDemoList();
        instance.serialStreamDemoForComparison(value);
        instance.parallelStreamDemoForComparison(value);
    }

    public void commonOperations01() {
        //List<Integer> nums1 = Arrays.asList(new Integer[] {1000, 2, 3, 5, 12, 3, 23, 11});
        List<Integer> nums = Arrays.asList(1000, 2, 3, null, 5, 12, 3, null, 23, 11);
        int sum = nums.stream().filter(Objects::nonNull).distinct().mapToInt(num->num*2).sorted().skip(0).limit(4).peek(System.out::println).sum();
        System.out.println("sum = " + sum);

        System.out.println("end");

        class IntObj {
            int a = new Random().nextInt(100);
            int b = new Random().nextInt(100);
        }

        List<IntObj> myObjects = new ArrayList<>(10);
        myObjects.add(new IntObj());
        myObjects.add(new IntObj());
        myObjects.add(new IntObj());

        List<Integer> abc = myObjects.stream().map(t->t.a).collect(Collectors.toList());    // 注意使用的是map
        System.out.println(abc);        // [18, 70, 88]
        abc.set(0, 1);

        for (IntObj obj : myObjects) {
            System.out.println("original val : " + obj.a);  // 没有变，说明map是有新建内存的
        }

        String stringRet = myObjects.stream().map(x->String.valueOf(x.b)).collect(Collectors.joining(", "));
        System.out.println(stringRet);  // 22, 83, 99


        // mapToInt方法返回值是TStream类型，TStream类包含了一些处理基础数据的方法，可以让我们更方便。我们使用mapToT的原因，不仅仅是方便，
        // 还在于性能。我们知道，因为泛型的原因，可以有List<Integer>但是不能有List<int>，这里的IntStream就相当于是List<int>,int 所占内存比Integer小。
        IntSummaryStatistics intSummaryStatistics = myObjects.stream().mapToInt(t->t.a).summaryStatistics();
        System.out.println("Max num = " + intSummaryStatistics.getMax());


        //List<Integer> abc = nums.stream().map(num->num*num).collect(Collectors.toList());

    }

    public void commonOperationsDemo() {
        List<String> list = new ArrayList<>();
        list.add("d2");
        list.add("a2");
        list.add("b1");
        list.add("a1");
        list.add("b3");
        list.add("b2");
        list.add("d1");

        // filter
        list.stream()
                .filter(s->s.startsWith("a"))
                .forEach(System.out::println);

        System.out.println("----------------");

        // sort
        list.stream()
                .sorted(String::compareTo)
                .filter(s->s.startsWith("b"))
                .forEach(System.out::println);

        // 也可以用Collections.sort进行
        Collections.sort(list, String::compareTo);

        System.out.println("----------------");

        // 中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象，下面的示例展示了将字符串转换为大写字符串。
        // 你也可以通过map来讲对象转换成其他类型，map返回的Stream类型是根据你map传递进去的函数的返回值决定的
        list.stream()
                .map(String::toUpperCase)
                .sorted((a,b)->b.compareTo(a))  // .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("----------------");
        boolean anyStartsWithA = list.stream().anyMatch(s->s.startsWith("a"));  // true
        boolean allStartsWithA = list.stream().allMatch(s->s.startsWith("a"));  // false
        boolean noneStartWithZ = list.stream().noneMatch(s->s.startsWith("z")); // true
        System.out.println(anyStartsWithA + " " + allStartsWithA + " " + noneStartWithZ);

        // count
        long startWithB = list.stream().filter(s->s.startsWith("b")).count();

        // reduce
        Optional<String> reduced = list.stream().sorted().reduce((s1, s2)->s1+"#"+s2); // a1#a2#b1#b2#b3#d1#d2
        reduced.ifPresent(System.out::println);

        System.out.println("----------------");
    }

    public List<String> createdDemoList() {
        int max = 10000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        return values;
    }

    public void serialStreamDemoForComparison(List<String> values) {
        long mark1 = System.currentTimeMillis();
        Stream<String> a = values.stream().sorted();
        long mark2 = System.currentTimeMillis();

        System.out.println(" serialStream costs: " + (mark2-mark1) + " value:" + a.count() );

    }

    public void parallelStreamDemoForComparison(List<String> values) {
        long mark1 = System.currentTimeMillis();
        Stream<String> a = values.parallelStream().sorted();
        long mark2 = System.currentTimeMillis();

        System.out.println(" parallelStream costs: " + (mark2 - mark2) + " value:" + a.count());
    }
}


//        // http://www.jb51.net/article/48304.htm