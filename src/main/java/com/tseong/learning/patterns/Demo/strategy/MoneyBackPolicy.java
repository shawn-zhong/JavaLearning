package com.tseong.learning.patterns.Demo.strategy;

import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.PayPreOrder;
import com.tseong.learning.patterns.Demo.SettleModel;

import java.util.List;

// 策略接口
public interface MoneyBackPolicy {

    // 策略算法接口：处理班级情况，返回生成的返款订单
    List<PayPreOrder> chargeAndGenerateOrders(SettleModel settleModel);
}
