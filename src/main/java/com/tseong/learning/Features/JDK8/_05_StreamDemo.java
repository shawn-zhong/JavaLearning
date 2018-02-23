package com.tseong.learning.Features.JDK8;

import java.util.*;
import java.util.stream.Stream;

public class _05_StreamDemo {

    public static void main(String[] args) {

        _05_StreamDemo instance = new _05_StreamDemo();
        instance.commonOperationsDemo();

        List<String> value = instance.createdDemoList();
        instance.serialStreamDemoForComparison(value);
        instance.parallelStreamDemoForComparison(value);
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


//
//
//        // http://www.jb51.net/article/48304.htm