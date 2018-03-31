package com.tseong.learning.advance._09_pattern.factoryMethod;

public class BikeFactory implements IVehicleFactory {
    @Override
    public Vehicle getVehicle() {
        return new Bike();
    }
}
