package com.tseong.learning.patterns._06_factoryMethod;

public class BusFactory implements IVehicleFactory {
    @Override
    public Vehicle getVehicle() {
        return new Bus();
    }
}
