package com.tseong.learning.patterns.Demo.state;

import com.tseong.learning.patterns.Demo.ClockInRecord;
import com.tseong.learning.patterns.Demo.state.base.BetClass;
import com.tseong.learning.patterns.Demo.state.base.BetClassState;

public class StateOnGoing implements BetClassState {

    @Override
    public void startPromisedClass(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("无法启动一个已经开跑的跑班");
    }

    @Override
    public void clockIn(BetClass theRunClass, ClockInRecord clockIn) throws Exception {
        // 为跑班增加打卡信息
        // ...
    }

    @Override
    public void settlePromisedClass(BetClass theRunClass) throws Exception {
        // 实现对跑班的具体结算动作:
        // ...

        // 将跑班状态至为已结算
        theRunClass.setState(new StateSettleSuccess());
    }

    @Override
    public void generateClassDiploma(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("无法为正在进行中的跑班生成毕业证书");
    }
}
