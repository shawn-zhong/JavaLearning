package com.tseong.learning.patterns._06_factoryMethod;

public class Bike implements Vehicle {
    @Override
    public void gotoWork() {
        System.out.println("骑自行车上班!");
    }
}
