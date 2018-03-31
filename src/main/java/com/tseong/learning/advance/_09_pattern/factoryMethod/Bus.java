package com.tseong.learning.advance._09_pattern.factoryMethod;

public class Bus implements Vehicle {
    @Override
    public void gotoWork() {
        System.out.println("坐公交车上班");
    }
}
