package com.tseong.learning.container.concurrent.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class _3_Iteration {

    private Map<String, Integer> _map;

    public static void main(String[] args) {
        _3_Iteration instance = new _3_Iteration();
        instance.initMap();
        instance.toIterate01();
        instance.toIterate02();
    }

    public void initMap() {
        _map = new HashMap<>(20);

        for(int i=0; i<2000000; i++)
            _map.put(String.valueOf(i), i);
    }

    public void toIterate01() {

        long start = System.currentTimeMillis();
        long result = 0;

        for (Map.Entry<String, Integer> entry : _map.entrySet()) {
            //System.out.println("toIterate01 : " + "key:value " + entry.getKey() + ":" + entry.getValue());
            result += entry.getValue();
        }

        long elapse = System.currentTimeMillis() - start;
        System.out.println("foreach costs (Millis) : " + elapse + " result: " + result);
    }

    // 使用iterator的优点：可以使用iterator.remove. 如果在for-each中使用，结果是不可预测的
    public void toIterate02() {
        long start = System.currentTimeMillis();

        Iterator<Map.Entry<String, Integer>> iterator = _map.entrySet().iterator();
        long result = 0;

        while ( iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            //System.out.println("toIterate02 : " + "key:value " + entry.getKey() + ":" + entry.getValue());
            result += entry.getValue();
        }

        long elapse = System.currentTimeMillis() - start;
        System.out.println("iterator costs (Millis) : " + elapse + " result: " + result);
    }

}
