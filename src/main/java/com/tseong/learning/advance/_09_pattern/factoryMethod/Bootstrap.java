package com.tseong.learning.advance._09_pattern.factoryMethod;

public class Bootstrap {

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
