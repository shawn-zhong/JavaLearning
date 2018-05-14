package com.tseong.learning.patterns._27_businessDelegate;

public class EJBService implements BusinessService {

    @Override
    public void doProcessing() {
        System.out.println("Processin task by invoking EJB Servide");
    }
}
