package com.tseong.learning.container.concurrent.ConcurrentNavigableMap;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapDemo {

    // java.util.concurrent.ConcurrentNavigableMap 是一个支持并发访问的 java.util.NavigableMap，
    // 它还能让它的子 map 具备并发访问的能力。所谓的 "子 map" 指的是诸如 headMap()，subMap()，tailMap() 之类的方法返回的 map。

    public static void main(String[] args) {
        ConcurrentNavigableMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("4", "four");
        map.put("1", "one");
        map.put("0", "zero");
        map.put("2", "two");
        map.put("3", "three");

        // 返回小于3的子map
        ConcurrentNavigableMap<String, String> headMap = map.headMap("3");
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            System.out.println("headMap: " + entry.getKey() + " " + entry.getValue());
        }

        // 返回大于等于3的子map
        ConcurrentNavigableMap<String, String> tailMap = map.tailMap("3");
        for (Map.Entry<String, String> entry : tailMap.entrySet()) {
            System.out.println("tailMap: " + entry.getKey() + " " + entry.getValue());
        }

        // 返回 fromkey <= n < toKey 的子map
        ConcurrentNavigableMap<String, String> subMap = map.subMap("1", "4");
        for (Map.Entry<String, String> entry : subMap.entrySet()) {
            System.out.println("subMap: " + entry.getKey() + " " + entry.getValue());
        }

        NavigableSet<String> set1 = map.descendingKeySet();
        for (String item : set1) {
            System.out.println("descendingKeyset: " + item);
        }

        ConcurrentNavigableMap<String, String> descendingMap = map.descendingMap();
        for (Map.Entry<String, String> entry : descendingMap.entrySet()) {
            System.out.println("descendingMap: " + entry.getKey() + " " + entry.getValue());
        }
    }

}
