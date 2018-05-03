package com.tseong.learning.patterns.Demo.state;

import com.tseong.learning.patterns.Demo.ClockInRecord;
import com.tseong.learning.patterns.Demo.state.base.BetClass;
import com.tseong.learning.patterns.Demo.state.base.RunClassState;

public class StateCreatePending implements RunClassState {

    @Override
    public void startPromisedClass(BetClass theRunClass) throws Exception {
        // 这里实现"开启"跑班的具体操作
        // ...

        // 如果符合开跑要求，则将跑班状态至为"进行中"
        theRunClass.setState(new StateOnGoing());
    }

    @Override
    public void clockIn(BetClass theRunClass, ClockInRecord clockIn) throws Exception {
        assert false; // & log warn
    }

    @Override
    public void settlePromisedClass(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("无法结算一个'创建中'的跑班");
    }

    @Override
    public void generateClassDiploma(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("无法为一个'创建中'的跑班生成毕业证书");
    }
}
