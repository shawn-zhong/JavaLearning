package com.tseong.learning.patterns.templateMethod;

import com.tseong.learning.patterns.states.PayState;

public abstract class PayProcessor {

    PayState currentState;

    public void process() {

        // while
        currentState.Handle(this);
    }

    public void setState(PayState state) {
        currentState = state;
    }

    public abstract boolean checkPayParameters();

    public boolean checkPayEnvironment() {

        // check database etc.
        return true;
    }

    public boolean checkUserPayable() {
        // if exists class

        // if already has a class

        // if exists orders

        return true;
    }

    public void createAndPersistOrders() {

    }

    public void Biz() {

    }


}
