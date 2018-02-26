package com.tseong.learning.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class _02_FastSortDemo {

    public static void main(String[] args) {

        List numbers = Arrays.asList(34, 23, 12, 33, 5, 26, 18, 25, 8, 13);

        printArray(numbers, 0, numbers.size()-1);

        fastSort(numbers, 0, numbers.size()-1);

        printArray(numbers, 0, numbers.size()-1);
    }

    // 快速排序的思想是挖坑，以及找出一个合适的值填坑，最后只剩一个位置的时候，填入val
    public static void fastSort(List<Integer> list, int begin, int end) {

        if (begin >= end)           // 有可能进入 begin > end, 判断必须这样写
            return;

        int val = list.get(begin);  // 先把位置0挖出一个坑
        int i = begin;
        int j = end;

        while(i<j) {                // 如果i和j相碰撞则停止

            for (; j > i; j--) {                    // 从右往左寻找一个小于val的值，以便填取i位置的坑，然后j位置变为一个坑
                if (list.get(j) < list.get(i)) {
                    list.set(i, list.get(j));
                    printArray(list, begin, end);
                    break;
                }
            }

            for (; i < j; i++) {                    // 从左往右寻找一个大于val的值，以便填取上面j位置的坑，然后i位置变为一个坑
                if (list.get(i) > val) {
                    list.set(j, list.get(i));
                    printArray(list, begin, end);
                    break;
                }
            }
        }

        list.set(i, val);   // 此时i位置和j位置指向同一个坑，填入val值。所以val左边都是小于val的值，val右边都是大于val的值
        printArray(list, begin, end);

        fastSort(list, begin, i-1);      // 递归处理左边
        fastSort(list, i+1, end);       // 递归处理右边
    }

    static void printArray(List<Integer> numbers, int begin, int end) {
        System.out.print(">>> ");

        for (int i=begin; i<=end; i++)
            System.out.print(numbers.get(i) + " ");

        System.out.println("");
    }
}
