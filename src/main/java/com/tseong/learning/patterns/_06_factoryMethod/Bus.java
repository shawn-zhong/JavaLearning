package com.tseong.learning.patterns._06_factoryMethod;

public class Bus implements Vehicle {
    @Override
    public void gotoWork() {
        System.out.println("坐公交车上班");
    }
}
