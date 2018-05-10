package com.tseong.learning.patterns._06_factoryMethod;

public class Main {

    public static void main(String[] args) {
        IVehicleFactory factory = null;

        factory = new BikeFactory();
        Vehicle vehicle = factory.getVehicle();
        vehicle.gotoWork();

        factory = new BusFactory();
        vehicle = factory.getVehicle();
        vehicle.gotoWork();
    }
}
