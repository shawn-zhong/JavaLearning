package com.tseong.learning.patterns._04_state;

import com.tseong.learning.patterns.ClockInRecord;
import com.tseong.learning.patterns._04_state.base.BetClass;
import com.tseong.learning.patterns._04_state.base.BetClassState;

public class StateSettleSuccess implements BetClassState {

    @Override
    public void startPromisedClass(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("无法启动一个已经结帐的跑班");
    }

    @Override
    public void clockIn(BetClass theRunClass, ClockInRecord clockIn) throws Exception {
        // 实现代码：对已结帐的跑班，如何处理用户上传的打卡
    }

    @Override
    public void settlePromisedClass(BetClass theRunClass) throws Exception {
        assert false;
        throw new Exception("该跑班已经结算过类");
    }

    @Override
    public void generateClassDiploma(BetClass theRunClass) throws Exception {
        // 在这里实现 "生成跑班毕业证"
        // ***
    }
}
