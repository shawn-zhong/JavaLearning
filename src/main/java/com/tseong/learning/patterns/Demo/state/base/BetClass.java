package com.tseong.learning.patterns.Demo.state.base;

import com.tseong.learning.patterns.Demo.ClockInRecord;

public class BetClass {

    RunClassState currentState;

    public void getBetClassFromDB() {
        // fetch entry from DB
        BetClass betClass = null;

        //
    }

    public void setState(RunClassState state) {
        currentState = state;
    }

    public void startPromisedClass() throws Exception {
        currentState.startPromisedClass(this);
    }

    void clockIn(BetClass theRunClass, ClockInRecord clockIn) throws Exception {
        currentState.clockIn(this, clockIn);
    }

    public void settlePromisedClass() throws Exception {
        currentState.settlePromisedClass(this);
    }

    public void generateClassDiploma() throws Exception {
        currentState.generateClassDiploma(this);
    }

}
