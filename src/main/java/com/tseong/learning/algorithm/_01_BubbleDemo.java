package com.tseong.learning.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class _01_BubbleDemo {

    public static void main(String[] args) {

        /*
        Integer[] source = {1,2,62,12, 3, 9, 19};
        bubbleSort(source);
        */

        List<Integer> array = Arrays.asList(43,12,654,23,65,3,1);
        bubbleSort(array);


        List<Integer> newArray = new ArrayList<>(15);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            try {
                String text = scanner.next();
                if (text.equals("end"))
                    break;

                Integer number = Integer.parseInt(text);
                newArray.add(number);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bubbleSortNative(newArray);
    }

    public static void bubbleSort(Integer[] source) {
        for (int i=0; i<source.length-1; i++) {
            for (int j=0; j<source.length-i-1; j++) {
                if (source[j] > source[j+1]) {          // 把最大值从左往右边推
                    int tmp = source[j+1];
                    source[j+1] = source[j];
                    source[j] = tmp;
                }
            }
        }

        for (int value : source) {
            System.out.print(value + " ");
        }

        System.out.println();
    }

    public static void bubbleSortNative(List<Integer> source) {
        for (int i=0; i<source.size()-1; i++) {
            for (int j=0; j<source.size()-i-1; j++) {
                if (source.get(j) > source.get(j+1)) {
                    int tmp = source.get(j+1);
                    source.set(j+1, source.get(j));
                    source.set(j, tmp);
                }
            }
        }

        for (int value : source) {
            System.out.print(value + " ");
        }

        System.out.println();
    }

    public static void bubbleSort(List<Integer> source) {

        Integer[] array = source.toArray(new Integer[0]);   // 注意用法. 新建了内存, source其实没有变
        bubbleSort(array);

        for (int value : source) {
            System.out.print(value + " ");
        }

        System.out.println();
    }
}
