package com.tseong.learning.patterns._06_factoryMethod;

public class BikeFactory implements IVehicleFactory {
    @Override
    public Vehicle getVehicle() {
        return new Bike();
    }
}
