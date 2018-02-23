package com.tseong.learning.container.concurrent.map;


public class demo {
    public static void main(String[] args) {
        int a,b;
        b=10;
        int c = (a=b)*a;
        System.out.println(a+" " + b + " " +c);

        java.util.concurrent.ArrayBlockingQueue<Integer> ca = new java.util.concurrent.ArrayBlockingQueue<>(10);
        //java.util.concurrent.DelayQueue<Integer> ca2;

        java.util.concurrent.LinkedBlockingDeque<Integer> cg;
        java.util.concurrent.LinkedBlockingQueue<Integer> ch;
        java.util.concurrent.PriorityBlockingQueue<Integer> cj;
        java.util.concurrent.SynchronousQueue<Integer> ck;

        java.util.concurrent.ConcurrentLinkedQueue<Integer> cb = new java.util.concurrent.ConcurrentLinkedQueue<>();
        java.util.concurrent.ConcurrentHashMap<Integer, Integer> cc = new java.util.concurrent.ConcurrentHashMap<>();
        java.util.concurrent.ConcurrentLinkedDeque<Integer> cd = new java.util.concurrent.ConcurrentLinkedDeque<>();
        // interface : java.util.concurrent.ConcurrentMap<Integer, Integer>
        // interface : java.util.concurrent.ConcurrentNavigableMap
        java.util.concurrent.ConcurrentSkipListMap<Integer, Integer> ce;
        java.util.concurrent.ConcurrentSkipListSet<Integer> cf;

        java.util.concurrent.LinkedTransferQueue<Integer> ci;


        java.util.concurrent.CopyOnWriteArrayList<Integer> cl;
        // interface : java.util.concurrent.ConcurrentNavigableMap
        // interface : java.util.concurrent.TransferQueue



    }
}
