package com.tseong.learning.patterns.Demo.state.base;

import com.tseong.learning.patterns.Demo.ClockInRecord;

public interface BetClassState {

    void startPromisedClass(BetClass theRunClass) throws Exception;

    void clockIn(BetClass theRunClass, ClockInRecord clockIn) throws Exception;

    void settlePromisedClass(BetClass theRunClass) throws Exception;

    void generateClassDiploma(BetClass theRunClass) throws Exception;
}
