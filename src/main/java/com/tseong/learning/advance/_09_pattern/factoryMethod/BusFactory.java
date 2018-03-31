package com.tseong.learning.advance._09_pattern.factoryMethod;

public class BusFactory implements IVehicleFactory {
    @Override
    public Vehicle getVehicle() {
        return new Bus();
    }
}
