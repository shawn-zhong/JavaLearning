package com.tseong.learning.patterns._04_state.base;

import com.tseong.learning.patterns.ClockInRecord;
import com.tseong.learning.patterns._04_state.StateCreatePending;

public class BetClass {

    BetClassState currentState;

    public static BetClass initBetClassFromDB(int classId) {
        // fetch entry from DB
        // ...

        BetClass theClass = new BetClass();
        theClass.setState(new StateCreatePending());
        return theClass;
    }

    public void setState(BetClassState state) {
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
