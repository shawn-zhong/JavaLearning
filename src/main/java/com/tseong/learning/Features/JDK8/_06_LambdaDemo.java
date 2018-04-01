package com.tseong.learning.Features.JDK8;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class _06_LambdaDemo {

    public static void main(String args[]) {
        _06_LambdaDemo instance = new _06_LambdaDemo();
        instance.lambdaOfPhases();


    }

    public void lambdaOfPhases() {

        List<String> names = Arrays.asList("Chen", "Cao", "Zeng", "Meng", "Zhong");

        Collections.sort(names, String::compareToIgnoreCase);

        // phase1 : 实现接口的形式
        Comparator<String> cmp1 = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        };

        // phase2 :
        Comparator<String> cmp2 = (String a, String b) -> {
            return b.compareTo(a);
        };

        // phase3 : 省略参数类型
        Comparator<String> cmp3 = (a, b) -> {
            return b.compareTo(a);
        };

        // pahse4 : 省略返回值写法
        Comparator<String> cmp4 = (a, b) -> b.compareTo(a);

        // pahse5 : 直接引用现存方法
        Comparator<String> cmp5 = String::compareTo;

        names.stream().sorted(cmp5).forEach(System.out::println);
    }

    public void lambdaDemo() {
        MyConverter<String, Integer> converter = Integer::valueOf;
        System.out.println(converter.convert("1024"));

        MyConverter<Integer, String> converter2 = String::valueOf;
        System.out.println(converter2.convert(2048));
    }
}

@FunctionalInterface
interface MyConverter<F, T>{
    T convert(F from);
}