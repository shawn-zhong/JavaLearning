package com.tseong.learning.advance._09_pattern.factoryMethod;

public class Bike implements Vehicle {
    @Override
    public void gotoWork() {
        System.out.println("骑自行车上班!");
    }
}
